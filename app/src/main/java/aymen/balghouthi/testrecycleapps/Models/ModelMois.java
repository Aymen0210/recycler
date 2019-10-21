package aymen.balghouthi.testrecycleapps.Models;

public class ModelMois {
    String Mois;
    int Day;

    public ModelMois(String mois, int day) {
        Mois = mois;
        Day = day;
    }

    public String getMois() {
        return Mois;
    }

    public void setMois(String mois) {
        Mois = mois;
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
    }

    @Override
    public String toString() {
        return ""+getMois()+" "+getDay();
    }
}
