package BingoProjekt;

public class BingoMängija implements Comparable<BingoMängija>{
    private String nimi;
    private int tulemus;
    private String kuupäev;

    public BingoMängija(String nimi, int tulemus, String kuupäev) {
        this.nimi = nimi;
        this.tulemus = tulemus;
        this.kuupäev = kuupäev;
    }

    public String getNimi() {
        return nimi;
    }

    public int getTulemus() {
        return tulemus;
    }

    public String getKuupäev() {
        return kuupäev;
    }

    //Võrdleb mängijate tulemusi
    @Override
    public int compareTo(BingoMängija võrreldav) {
        return Integer.compare(getTulemus(), võrreldav.getTulemus());
    }

    @Override
    public String toString() {
        return nimi + "  /  " + kuupäev + "  /  " + tulemus + " palli";
    }
}
