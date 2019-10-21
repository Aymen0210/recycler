package aymen.balghouthi.testrecycleapps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.kodmap.library.kmrecyclerviewstickyheader.KmHeaderItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import aymen.balghouthi.testrecycleapps.Adapter.AdapterRecycleViewMois;
import aymen.balghouthi.testrecycleapps.Adapter.AdapterRecycleViewUser;
import aymen.balghouthi.testrecycleapps.Models.FirstLastPosMois;
import aymen.balghouthi.testrecycleapps.Models.ModelItemwithHeader;
import aymen.balghouthi.testrecycleapps.Models.ModelMois;
import aymen.balghouthi.testrecycleapps.Models.Datum;
import aymen.balghouthi.testrecycleapps.Models.ItemType;
import aymen.balghouthi.testrecycleapps.Models.Mois;
import aymen.balghouthi.testrecycleapps.Models.User;
import aymen.balghouthi.testrecycleapps.utils.UserCalls;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements UserCalls.Callbacks {
    @BindView(R.id.graphDate)
    GraphView graph;
    @BindView(R.id.recycle)
    RecyclerView recyclerView;
    @BindView(R.id.view_Pager)
    RecyclerView recycle_view_mois;
    List<Datum> mListDatum;
    List<Mois> listMois;

    List<FirstLastPosMois> listMoisItem;
    AdapterRecycleViewUser mAdapterRecycleViewUser;
    AdapterRecycleViewMois mAdapterRecycleViewMois;
    User user;
    private LinearLayoutManager layoutManager;
    private KmHeaderItemDecoration kmHeaderItemDecoration;
    private ModelMois mModelMois;
    public FirstLastPosMois moisNow;
    public int firstVisiblePosition = 1, firstVisiblePositionMois = 1;
    public String[] ch;
    public List<ModelItemwithHeader> modelList;
    public int sourceScrolling = 0;
    RecyclerView.OnScrollListener scrollListenerRecycleView;
    RecyclerView.OnScrollListener scrollListenerRecycleViewMois;
    LinearLayoutManager llManager;
    final RecyclerView.OnScrollListener[] scrollListeners = new RecyclerView.OnScrollListener[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        executeHttpRequestWithRetrofit();
        modelList = new ArrayList<>();
        listMoisItem = new ArrayList<>();
        moisNow = new FirstLastPosMois();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

        inisialiseMois();
        //set page margin between pages for viewpager
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int pageMargin = ((metrics.widthPixels / 4) * 2);

        scrollListeners[0] = new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView0, int newState) {
                super.onScrollStateChanged(recyclerView0, newState);
                if (newState == recyclerView0.SCROLL_STATE_IDLE) {
                    firstVisiblePositionMois = getCurrentItemMois();
                    recyclerView.removeOnScrollListener(scrollListeners[1]);
                    String ch0 = mAdapterRecycleViewMois.getRadioItem(firstVisiblePositionMois).getMoisName();
                    int pos0 = getfirstItemWithMois(ch0, listMoisItem);

                    recycle_view_mois.scrollToPosition(pos0);
//                    Log.i("Position0:==>", " " + firstVisiblePositionMois);
//                    if (!checkFirstitemRecyclewithViewPager(firstVisiblePositionMois)) {
//                        int f = viewPagerChangePosition(firstVisiblePositionMois);
//                        // Log.i("checkFirstitemRecyclewithViewPager:==>", " ok " + f);
//                        if (f > 0) {
//                            recyclerView.scrollToPosition(f);
//                        }
//                    }
                    recyclerView.addOnScrollListener(scrollListeners[1]);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView0, int dx, int dy) {
                super.onScrolled(recyclerView0, dx, dy);

            }
        };
        scrollListeners[1] = new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == recyclerView.SCROLL_STATE_IDLE) {
                    recyclerView.removeOnScrollListener(scrollListeners[1]);
                    firstVisiblePosition = getCurrentItemUser();
                    Log.i("Position1:==>: ", " " + firstVisiblePosition);
                    ch = mAdapterRecycleViewUser.getItemFromRcycle(firstVisiblePosition).getCreatedAt().split("-");
                    int pos = getfirstItemWithMois(ch[1], listMoisItem);

                    recycle_view_mois.scrollToPosition(pos);
                    recycle_view_mois.addOnScrollListener(scrollListeners[0]);
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        };
        recycle_view_mois.addOnScrollListener(scrollListeners[0]);
        recyclerView.addOnScrollListener(scrollListeners[1]);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void updateUIWhenStartingHTTPRequest() {
        // Log.i("AymenTest", "setText Downloading");
    }

    private void updateUIWhenStopingHTTPRequest(String response) {
        //  Log.i("AymenTest1", "=" + response);
    }
    // ------------------------------
    //  HTTP REQUEST (Retrofit Way)
    // ------------------------------

    // 4 - Execute HTTP request and update UI
    private void executeHttpRequestWithRetrofit() {
        this.updateUIWhenStartingHTTPRequest();
        UserCalls.fetchUserFollowing(this);
    }

    // 2 - Override callback methods

    @Override
    public void onResponse(@Nullable User users) {
        // 2.1 - When getting response, we update UI
        if (users != null) {
            user = users;
            mListDatum = users.getData();
            this.updateUI(user, mModelMois);
            initData(user);
            String[] ch = mAdapterRecycleViewUser.getItemFromRcycle(1).getCreatedAt().split("-");
            if (!ch.equals("")) {
                //  moisNow = Integer.parseInt(ch[1]);
                //   view_pager_mois.setCurrentItem(moisNow - 1);
            }
        }
    }


    @Override
    public void onFailure() {
        // 2.2 - When getting error, we update UI
        this.updateUIWhenStopingHTTPRequest("An error happened !");
    }

    // 3 - Update UI showing only name of users
    private void updateUIWithListOfUsers(User user) {
        StringBuilder stringBuilder = new StringBuilder();

        updateUIWhenStopingHTTPRequest(user.getData().toString());
    }

    private void updateUI(User user, ModelMois mModelMois) {
        mAdapterRecycleViewUser = new AdapterRecycleViewUser();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(mAdapterRecycleViewUser);
        kmHeaderItemDecoration = new KmHeaderItemDecoration(mAdapterRecycleViewUser);
        recyclerView.setLayoutManager(layoutManager);
        SnapHelper snapHelper1 = new LinearSnapHelper();
        snapHelper1.attachToRecyclerView(recyclerView);
    }

    public void inisialiseMois() {
        listMois = new ArrayList<>();
        listMois.add(new Mois("Janvier", 1));
        listMois.add(new Mois("Février", 2));
        listMois.add(new Mois("Mars", 3));
        listMois.add(new Mois("Avril", 4));
        listMois.add(new Mois("Mai", 5));
        listMois.add(new Mois("Juin", 6));
        listMois.add(new Mois("Juillet", 7));
        listMois.add(new Mois("Aout", 8));
        listMois.add(new Mois("Septembre", 9));
        listMois.add(new Mois("Octobre", 10));
        listMois.add(new Mois("Novembre", 11));
        listMois.add(new Mois("Décembre", 12));

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mAdapterRecycleViewMois = new AdapterRecycleViewMois(listMoisItem);
        recycle_view_mois.setAdapter(mAdapterRecycleViewMois);

        recycle_view_mois.setLayoutManager(layoutManager1);
        mAdapterRecycleViewMois.notifyDataSetChanged();
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recycle_view_mois);
        //recycle_view_mois.scrollToPosition(moisNow.getPosMois());
        Log.i("DataNow", "  " + moisNow.getMoisName());
    }

    public String getDateString(String ch) {
        switch (ch) {
            case "01":
                return "Janvier";
            case "02":
                return "Février";
            case "03":
                return "Mars";
            case "04":
                return "Avril";
            case "05":
                return "Mai";
            case "06":
                return "Juin";
            case "07":
                return "Juillet";
            case "08":
                return "Aout";
            case "09":
                return "Septembre";
            case "10":
                return "Octobre";
            case "11":
                return "Novembre";
            case "12":
                return "Décembre";
            default:
                return "";
        }
    }

    public void initData(User mUser) {
        ModelMois mModelMois;
        Datum mdatum;
        if (mUser.getData().size() > 0) {
            for (int i = 0; i < mUser.getData().size(); i++) {
                String[] separated = mUser.getData().get(i).getCreatedAt().split("-");
                mModelMois = new ModelMois(getDateString(separated[1]), Integer.parseInt(separated[2].substring(0, 2)));
                mdatum = new Datum(mUser.getData().get(i), mModelMois);

                for (Integer j = 1; j <= 1; j++) {
                    ModelItemwithHeader headerModel = new ModelItemwithHeader(mdatum, mModelMois, ItemType.Header);
                    ModelItemwithHeader headerModel1 = new ModelItemwithHeader(mdatum, mModelMois, ItemType.Post);
                    modelList.add(headerModel);
                    modelList.add(headerModel1);
                }
            }
            Collections.sort(modelList);
            mAdapterRecycleViewUser.submitList(modelList);
            retriveMois(modelList);
            moisNow = listMoisItem.get(0);
        }
    }

    public void retriveMois(List<ModelItemwithHeader> modelLists) {

        FirstLastPosMois flPosMois = new FirstLastPosMois();
        int itemMoisNow = 0;

        for (int i = 0; i < modelLists.size(); i++) {
            String[] separated = modelLists.get(i).getMdatum().getCreatedAt().split("-");
            if (i == itemMoisNow) {
                flPosMois.setMoisName(getDateString(separated[1]));
                flPosMois.setFirstMois(i);
                flPosMois.setPosMois(Integer.parseInt(separated[1]));
                flPosMois.setAnne(Integer.parseInt(separated[0]));
                Log.i("Datan", " " + flPosMois.getMoisName() + " " + flPosMois.getFirstMois() + " " + i);
            } else {
                if (!flPosMois.getMoisName().equals(getDateString(separated[1]))) {
                    flPosMois.setLastMois(i - 1);
                    itemMoisNow = i;
                    listMoisItem.add(flPosMois);
                    Log.i("Datan1", " " + flPosMois.getMoisName() + " " + getDateString(separated[1]) + " " + flPosMois.getLastMois() + " i " + i + " itemmoisnow " + itemMoisNow);
                    Log.i("Datan2 ",flPosMois.toString());
                    flPosMois.setMoisName(getDateString(separated[1]));
                    flPosMois.setFirstMois(i);
                    flPosMois.setPosMois(Integer.parseInt(separated[1]));
                    flPosMois.setAnne(Integer.parseInt(separated[0]));
                }
            }
        }
    }

    public int viewPagerChangePosition(int fvb) {
        int pos = 1;
        if (modelList.size() > 0) {
            if (fvb > 0 && fvb < 13) {
                for (int i = 0; i < modelList.size(); i++) {
                    if (modelList.get(i).getModelMois().getMois().equals(mAdapterRecycleViewMois.getRadioItem(fvb).getMoisName()) && i < modelList.size()) {
                        pos = i;
                        i = modelList.size();
                    }
                }
            }
        }
        return pos;

    }

    public Boolean checkFirstitemRecyclewithViewPager(int fvb) {
        int firstItem = 1;
        int lastItem = 1;
        Boolean etat = false;
        if (modelList.size() > 0) {
            if (fvb > 0 && fvb < 13) {
                for (int i = 0; i < modelList.size(); i++) {
                    Log.i("Data", "  " + modelList.get(i).getModelMois().getMois() + " == " + mAdapterRecycleViewMois.getRadioItem(fvb).getMoisName() + " " + fvb);
                    if (modelList.get(i).getModelMois().getMois().equals(mAdapterRecycleViewMois.getRadioItem(fvb).getMoisName())) {
                        Log.i("DataD", "  " + modelList.get(i).getModelMois().getMois() + " == " + mAdapterRecycleViewMois.getRadioItem(fvb).getMoisName() + " " + fvb);
                        firstItem = i;
                        for (int j = i + 1; j < modelList.size(); j++) {
                            if (!modelList.get(j).getModelMois().getMois().equals(mAdapterRecycleViewMois.getRadioItem(fvb).getMoisName())) {
                                lastItem = j - 1;
                                j = modelList.size();
                                i = modelList.size();
                            }
                        }
                    }
                }
                if (firstItem > 0 && lastItem == 0) lastItem = modelList.size();
                if (firstVisiblePosition <= lastItem && firstVisiblePosition >= firstItem) {
                    etat = true;
                }
            }
        }
        return etat;
    }

    private int getCurrentItemMois() {
        return ((LinearLayoutManager) Objects.requireNonNull(recycle_view_mois.getLayoutManager()))
                .findFirstVisibleItemPosition();
    }

    private int getCurrentItemUser() {
        return ((LinearLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager()))
                .findFirstVisibleItemPosition();
    }

    public int getfirstItemWithMois(String MoisName, List<FirstLastPosMois> fl) {

        int scrollto = 1;
        if (fl.size() > 0) {
            for (int i = 0; i < fl.size(); i++) {
                if (fl.get(i).getMoisName().equals(MoisName)) {
                    scrollto = fl.get(i).getFirstMois();
                    i = fl.size();
                }
            }
        }
        return scrollto;
    }
}
