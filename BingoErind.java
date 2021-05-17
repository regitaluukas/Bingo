package BingoProjekt;

public class BingoErind extends RuntimeException{
    private String sõnum;

    public BingoErind(String message) {
        super(message);
        sõnum = message;
    }

    public String getSõnum() {
        return sõnum;
    }
}
