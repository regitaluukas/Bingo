package BingoProjekt;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BingoFail {
    private String failinimi;

    public BingoFail(String failinimi) {
        this.failinimi = failinimi;
    }

    public String getFailinimi() {
        return failinimi;
    }

    public void kirjutaFaili(String failinimi, BingoMängija mängija){
        try (BufferedWriter välja = new BufferedWriter(new FileWriter(failinimi,true))){
            välja.write(mängija.getNimi() + ";" + mängija.getTulemus() + ";" + mängija.getKuupäev());
            välja.newLine();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public String[] loeFailist(String failinimi) {
        List<String> read = new ArrayList<>();
        List<BingoMängija> tulemused = new ArrayList<>();
        String[] tulemusteMassiiv = new String[5];
        try (BufferedReader lugeja = new BufferedReader(new InputStreamReader(new FileInputStream(failinimi)))) {
            String rida = lugeja.readLine();
            while (rida != null){
                read.add(rida);
                rida = lugeja.readLine();
            }
            for (String üksrida : read) {
                String[] mängija = üksrida.split(";");
                BingoMängija uusMängija = new BingoMängija(mängija[0], Integer.parseInt(mängija[1]), mängija[2]);
                tulemused.add(uusMängija);
            }
            Collections.sort(tulemused);
            for (int i = 0; i < tulemused.size() && i < 5; i++) {
                tulemusteMassiiv[i] = tulemused.get(i).toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tulemusteMassiiv;
    }
}
