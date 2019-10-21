package aymen.balghouthi.testrecycleapps.Models;

public class Mois {
    private String nameMois;
    private int posMois;
    private Boolean selectedMois;

    public Mois(String nameMois, int posMois, Boolean selectedMois) {
        this.nameMois = nameMois;
        this.posMois = posMois;
        this.selectedMois = selectedMois;
    }

    public Mois(String nameMois, int posMois) {
        this.nameMois = nameMois;
        this.posMois = posMois;
        this.selectedMois=false;
    }

    public Mois() {
    }

    public String getNameMois() {
        return nameMois;
    }

    public void setNameMois(String nameMois) {
        this.nameMois = nameMois;
    }

    public int getPosMois() {
        return posMois;
    }

    public void setPosMois(int posMois) {
        this.posMois = posMois;
    }

    public Boolean getSelectedMois() {
        return selectedMois;
    }

    public void setSelectedMois(Boolean selectedMois) {
        this.selectedMois = selectedMois;
    }

    @Override
    public String toString() {
        return "Mois{" +
                "nameMois='" + nameMois + '\'' +
                ", posMois=" + posMois +
                ", selectedMois=" + selectedMois +
                '}';
    }
}
