package aymen.balghouthi.testrecycleapps.Models;

public class FirstLastPosMois {
    String MoisName;
    int FirstMois;
    int LastMois;
    int PosMois;
    int anne;


    public FirstLastPosMois(String moisName, int firstMois, int posMois, int lastMois, int manne) {
        MoisName = moisName;
        FirstMois = firstMois;
        LastMois = lastMois;
        PosMois = posMois;
        anne = manne;
    }

    public FirstLastPosMois(String moisName, int firstMois, int posMois) {
        MoisName = moisName;
        FirstMois = firstMois;
        PosMois = posMois;
    }

    public FirstLastPosMois(int posMois) {
        PosMois = posMois;
    }

    public FirstLastPosMois() {
    }

    public String getMoisName() {
        return MoisName;
    }

    public void setMoisName(String moisName) {
        MoisName = moisName;
    }

    public int getFirstMois() {
        return FirstMois;
    }

    public void setFirstMois(int dirstMois) {
        FirstMois = dirstMois;
    }

    public int getLastMois() {
        return LastMois;
    }

    public void setLastMois(int lastMois) {
        LastMois = lastMois;
    }

    public int getPosMois() {
        return PosMois;
    }

    public void setPosMois(int posMois) {
        PosMois = posMois;
    }

    public int getAnne() {
        return anne;
    }

    public void setAnne(int anne) {
        this.anne = anne;
    }

    @Override
    public String toString() {
        return "FirstLastPosMois{" +
                "MoisName='" + MoisName + '\'' +
                ", FirstMois=" + FirstMois +
                ", LastMois=" + LastMois +
                ", PosMois=" + PosMois +
                ", anne=" + anne +
                '}';
    }
}
