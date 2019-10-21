package aymen.balghouthi.testrecycleapps.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import aymen.balghouthi.testrecycleapps.Models.FirstLastPosMois;
import aymen.balghouthi.testrecycleapps.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewHolderRecycleViewMois extends RecyclerView.ViewHolder implements View.OnClickListener {


    @BindView(R.id.title_mois)
    TextView title_mois;
    @BindView(R.id.title_annee)
    TextView title_annee;

    public ViewHolderRecycleViewMois(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithMoisItem(FirstLastPosMois mMoisItem) {

        title_mois.setText("" + mMoisItem.getMoisName());
        title_annee.setText("" + mMoisItem.getAnne());
    }

    @Override
    public void onClick(View view) {

    }
}
