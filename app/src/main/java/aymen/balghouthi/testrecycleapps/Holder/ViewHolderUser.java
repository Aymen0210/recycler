package aymen.balghouthi.testrecycleapps.Holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import aymen.balghouthi.testrecycleapps.Models.Datum;
import aymen.balghouthi.testrecycleapps.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewHolderUser extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.receiver)
    TextView receiver;
    @BindView(R.id.created_at)
    TextView created_at;
    @BindView(R.id.amount)
    TextView amount;

    public ViewHolderUser(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onClick(View view) {

    }
    public void updateWithUser(Datum mDatum) {


        this.receiver.setText(mDatum.getReceiver());
        this.amount.setText(""+mDatum.getAmount());
        this.created_at.setText(""+mDatum.getCreatedAt());
    }


}
