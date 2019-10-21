package aymen.balghouthi.testrecycleapps.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

import aymen.balghouthi.testrecycleapps.Models.Mois;
import aymen.balghouthi.testrecycleapps.R;

public class AdapterMois extends PagerAdapter {
    private List<Mois> mMois;
    private LayoutInflater layoutInflater;
    private Context context;
    TextView title;

    public AdapterMois(List<Mois> models, Context context) {
        this.mMois = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mMois.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item, container, false);
        title=view.findViewById(R.id.title);
        title.setText("" + mMois.get(position).getNameMois());
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);

    }

    public String getMoisName() {
        return title.getText().toString();
    }
}
