package BingoProjekt;
import java.util.Collections;
import java.util.List;

public class BingoNumbrid {
    private List<Integer> LoosiNumbrid;

    public BingoNumbrid(List<Integer> LoosiNumbrid) {
        for (int i = 1; i <= 75; i++) {
            LoosiNumbrid.add(i);
        }
        Collections.shuffle(LoosiNumbrid);
        this.LoosiNumbrid = LoosiNumbrid;
    }

    public List<Integer> getLoosiNumbrid() {
        return LoosiNumbrid;
    }

}//Järjest juhuslikud numbrid
