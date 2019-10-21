package aymen.balghouthi.testrecycleapps.Models;

public class ModelItemwithHeader implements Comparable<ModelItemwithHeader>{
    Datum mdatum;
    ModelMois mModelMois;
    Integer type;

    public ModelItemwithHeader(Datum datum, ModelMois modelMois,Integer type) {
        mdatum = datum;
        mModelMois = modelMois;
        this.type=type;
    }

    public ModelItemwithHeader() {
    }

    public Datum getMdatum() {
        return mdatum;
    }

    public void setMdatum(Datum mdatum) {
        this.mdatum = mdatum;
    }

    public ModelMois getModelMois() {
        return mModelMois;
    }

    public void setModelMois(ModelMois modelMois) {
        mModelMois = modelMois;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public int compareTo(ModelItemwithHeader modelItemwithHeader) {
        return getMdatum().getCreatedAt().compareTo(modelItemwithHeader.getMdatum().getCreatedAt());
    }
}
