package aymen.balghouthi.testrecycleapps.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import aymen.balghouthi.testrecycleapps.Models.FirstLastPosMois;
import aymen.balghouthi.testrecycleapps.R;
import butterknife.BindView;

public class AdapterRecycleViewMois extends RecyclerView.Adapter<ViewHolderRecycleViewMois> {
    private List<FirstLastPosMois> listMoisItem;

    public AdapterRecycleViewMois(List<FirstLastPosMois> listMois) {
        this.listMoisItem = listMois;
    }

    @NonNull
    @Override
    public ViewHolderRecycleViewMois onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item, parent, false);

        return new ViewHolderRecycleViewMois(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRecycleViewMois holder, int position) {
        holder.updateWithMoisItem(this.listMoisItem.get(position));


    }

    @Override
    public int getItemCount() {
        return listMoisItem.size();
    }

    public FirstLastPosMois getRadioItem(int position) {
        return this.listMoisItem.get(position);
    }
}
