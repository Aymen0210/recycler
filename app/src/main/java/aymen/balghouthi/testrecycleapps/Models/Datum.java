
package aymen.balghouthi.testrecycleapps.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Comparable<Datum>{

    @SerializedName("receiver")
    @Expose
    private String receiver;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("amount")
    @Expose
    private Integer amount;

    public Datum(Datum datum, ModelMois mModelMois) {
        this.receiver=datum.getReceiver();
        this.amount=datum.getAmount();
        this.createdAt=datum.getCreatedAt();
    }

    public Datum(String receiver, String createdAt, Integer amount) {
        this.receiver = receiver;
        this.createdAt = createdAt;
        this.amount = amount;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public int compareTo(Datum datum) {
        return getCreatedAt().compareTo(datum.createdAt);
    }
}
