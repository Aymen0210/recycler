package aymen.balghouthi.testrecycleapps.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.kodmap.library.kmrecyclerviewstickyheader.KmStickyListener;

import aymen.balghouthi.testrecycleapps.Models.Datum;
import aymen.balghouthi.testrecycleapps.Models.ItemType;
import aymen.balghouthi.testrecycleapps.Models.ModelItemwithHeader;
import aymen.balghouthi.testrecycleapps.Models.ModelMois;
import aymen.balghouthi.testrecycleapps.R;

public class AdapterRecycleViewUser extends ListAdapter<ModelItemwithHeader, RecyclerView.ViewHolder> implements KmStickyListener {


    public AdapterRecycleViewUser() {
        super(ModelDiffUtilCallback);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView;
        if (viewType == ItemType.Header ) {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_header, viewGroup, false);
            return new HeaderViewHolder(itemView);
        } else if (viewType == ItemType.Post){
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item_user, viewGroup, false);
            return new PostViewHolder(itemView);
        }else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ItemType.Header) {
            ((HeaderViewHolder) holder).bind(getItem(position).getModelMois());
        } else {
            ((PostViewHolder) holder).bind(getItem(position).getMdatum());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }

    @Override
    public Integer getHeaderPositionForItem(Integer itemPosition) {
        Integer headerPosition = 0;
        for (Integer i = itemPosition; i > 0; i--) {
            if (isHeader(i)) {
                headerPosition = i;
                return headerPosition;
            }
        }
        return headerPosition;
    }

    @Override
    public Integer getHeaderLayout(Integer headerPosition) {
        return R.layout.item_header;
    }

    @Override
    public void bindHeaderData(View header, Integer headerPosition) {
        TextView tv = header.findViewById(R.id.title_header);
        tv.setText(getItem(headerPosition).getModelMois().toString());
    }

    @Override
    public Boolean isHeader(Integer itemPosition) {
        return getItem(itemPosition).getType().equals(ItemType.Header);
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_header);
        }

        public void bind(ModelMois mModelmois) {
            title.setText(mModelmois.toString());
        }
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView title,created_at,amount;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.receiver);
            created_at=itemView.findViewById(R.id.created_at);
            amount=itemView.findViewById(R.id.amount);
        }

        public void bind(Datum model) {
            title.setText(""+model.getReceiver());
            created_at.setText(""+model.getCreatedAt().substring(11,19));
            amount.setText(""+model.getAmount());
        }
    }

    public static final DiffUtil.ItemCallback<ModelItemwithHeader> ModelDiffUtilCallback =
            new DiffUtil.ItemCallback<ModelItemwithHeader>() {
                @Override
                public boolean areItemsTheSame(@NonNull ModelItemwithHeader model, @NonNull ModelItemwithHeader t1) {
                    return model.getModelMois().toString().equals(t1.getModelMois().toString());
                }

                @Override
                public boolean areContentsTheSame(@NonNull ModelItemwithHeader model, @NonNull ModelItemwithHeader t1) {
                    return model.equals(t1);
                }
            };

    public Datum getItemFromRcycle(int position) {
      //  Log.i("PositionAdapter : "," "+position+1+" "+getItem(position).getMdatum().getReceiver());
        return getItem(position).getMdatum();
    }
}
