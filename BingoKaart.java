package BingoProjekt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BingoKaart {
    public BingoKaart() {
    }
//Kas võib teha tühja konstruktorit, et saada kätte teisi selle klassi meetodeid?
    public int[][] Kaart() {
        List<Integer> B = new ArrayList<>(15);
        for (int i = 1; i <= 15; i++){
            B.add(i);
        }
        List<Integer> I = new ArrayList<>(15);
        for (int i = 16; i <= 30; i++){
            I.add(i);
        }
        List<Integer> N = new ArrayList<>(15);
        for (int i = 31; i <= 45; i++){
            N.add(i);
        }
        List<Integer> G = new ArrayList<>(15);
        for (int i = 46; i <= 60; i++){
            G.add(i);
        }
        List<Integer> O = new ArrayList<>(15);
        for (int i = 61; i <= 75; i++){
            O.add(i);
        }
        Collections.shuffle(B);
        Collections.shuffle(I);
        Collections.shuffle(N);
        Collections.shuffle(G);
        Collections.shuffle(O);
        int[][] bingolaud = new int[5][5];
        for (int i = 0; i < 5; i++){
            //i++ ülevalt ära

            //int suvaline = (int)(Math.random()*(15)+;
            //if (!IntStream.of(bingolaud[0]).anyMatch(x -> x == suvaline)){
            //      bingolaud[0][i] = suvaline;
            //      i++;}
            bingolaud[0][i] = B.get(i);
        }
        for (int i = 0; i < 5; i++){
            bingolaud[1][i] = I.get(i);
        }
        for (int i = 0; i < 5; i++){
            bingolaud[2][i] = N.get(i);
        }
        for (int i = 0; i < 5; i++){
            bingolaud[3][i] = G.get(i);
        }
        for (int i = 0; i < 5; i++){
            bingolaud[4][i] = O.get(i);
        }
        return bingolaud;
    }
}//Bingolaua konstrueerimine
