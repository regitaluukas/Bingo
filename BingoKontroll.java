package BingoProjekt;

import java.util.List;

public class BingoKontroll {

    public BingoKontroll() {
    }

    public boolean bingoKontroll(int[][] bingoseis) {
        if (bingoseis[0][0] == 1 && bingoseis[1][1] == 1 && bingoseis[2][2] == 1 && bingoseis[3][3] == 1 && bingoseis[4][4] == 1) {
                return true;
            }
        else if (bingoseis[0][4] == 1 && bingoseis[1][3] == 1 && bingoseis[2][2] == 1 && bingoseis[3][1] == 1 && bingoseis[4][0] == 1) {
                return true;
            }
        else if (bingoseis[0][0] == 1 && bingoseis[1][0] == 1 && bingoseis[2][0] == 1 && bingoseis[3][0] == 1 && bingoseis[4][0] == 1) {
            return true;
        }
        else if (bingoseis[0][1] == 1 && bingoseis[1][1] == 1 && bingoseis[2][1] == 1 && bingoseis[3][1] == 1 && bingoseis[4][1] == 1) {
            return true;
        }
        else if (bingoseis[0][2] == 1 && bingoseis[1][2] == 1 && bingoseis[2][2] == 1 && bingoseis[3][2] == 1 && bingoseis[4][2] == 1) {
            return true;
        }
        else if (bingoseis[0][3] == 1 && bingoseis[1][3] == 1 && bingoseis[2][3] == 1 && bingoseis[3][3] == 1 && bingoseis[4][3] == 1) {
            return true;
        }
        else if (bingoseis[0][4] == 1 && bingoseis[1][4] == 1 && bingoseis[2][4] == 1 && bingoseis[3][4] == 1 && bingoseis[4][4] == 1) {
            return true;
        }
        else if (bingoseis[0][0] == 1 && bingoseis[0][1] == 1 && bingoseis[0][2] == 1 && bingoseis[0][3] == 1 && bingoseis[0][4] == 1) {
            return true;
        }
        else if (bingoseis[1][0] == 1 && bingoseis[1][1] == 1 && bingoseis[1][2] == 1 && bingoseis[1][3] == 1 && bingoseis[1][4] == 1) {
            return true;
        }
        else if (bingoseis[2][0] == 1 && bingoseis[2][1] == 1 && bingoseis[2][2] == 1 && bingoseis[2][3] == 1 && bingoseis[2][4] == 1) {
            return true;
        }
        else if (bingoseis[3][0] == 1 && bingoseis[3][1] == 1 && bingoseis[3][2] == 1 && bingoseis[3][3] == 1 && bingoseis[3][4] == 1) {
            return true;
        }
        else return bingoseis[4][0] == 1 && bingoseis[4][1] == 1 && bingoseis[4][2] == 1 && bingoseis[4][3] == 1 && bingoseis[4][4] == 1;
    } //Diagonaali, horisontaali, vertikaali kontroll


    public boolean numbriKontroll(int[][] bingoseis, List<Integer> eelmisedNumbrid, int[][] bingokaart) {
        for (int i = 0; i < bingoseis.length; i++) {
            for (int j = 0; j < bingoseis[i].length; j++) {
                if (bingoseis[i][j] == 1){
                    if (!eelmisedNumbrid.contains(bingokaart[i][j])){
                        return false;
                    }
                }
            }
        }
        return true;
    }//Õigete numbrite valimise kontroll
}