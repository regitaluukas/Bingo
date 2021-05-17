package BingoProjekt;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.geometry.Insets;
import javafx.scene.effect.Bloom;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.VLineTo;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Path;
import java.text.SimpleDateFormat;
import java.util.*;


public class Bingo extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Vajaminevad objektid
        BingoKaart Bingokaart = new BingoKaart();
        int[][] bingokaart = Bingokaart.Kaart();
        BingoSeis bingoseis = new BingoSeis(new int[5][5]);
        List<Integer> numbrid = new ArrayList<>(75);
        BingoNumbrid LoosiNumbrid = new BingoNumbrid(numbrid);
        List<Integer> kasutatudNumbrid = new ArrayList<>(75);


        //pealkirja Vbox (esilehele)
        Label B = new Label("B");
        B.setFont(Font.font("Bahnschrift Light", FontWeight.EXTRA_BOLD, 40));
        B.setTextFill(Color.RED);
        Label I = new Label("I");
        I.setFont(Font.font("Bahnschrift Light", FontWeight.EXTRA_BOLD, 40));
        I.setTextFill(Color.ORANGE);
        Label N = new Label("N");
        N.setFont(Font.font("Bahnschrift Light", FontWeight.EXTRA_BOLD, 40));
        N.setTextFill(Color.BLUE);
        Label G = new Label("G");
        G.setFont(Font.font("Bahnschrift Light", FontWeight.EXTRA_BOLD, 40));
        G.setTextFill(Color.GREEN);
        Label O = new Label("O");
        O.setFont(Font.font("Bahnschrift Light", FontWeight.EXTRA_BOLD, 40));
        O.setTextFill(Color.PURPLE);
        HBox pealkiri = new HBox();
        pealkiri.getChildren().addAll(B,I,N,G,O);
        HBox pealkiriHb = new HBox();
        pealkiriHb.getChildren().add(pealkiri);
        pealkiriHb.setAlignment(Pos.CENTER);
        //Juhend (esilehele)
        Label juhend = new Label("Bingo mängus saab mängija 25-ruudulise bingolaua, kus on juhuslikult valitud numbrid.\n" +
                "Mängu alustamiseks vajuta nupule start, misjärel hakkavad ekraanile tulema iga 5sek tagant\n" +
                "loositud numbrid, iga kord kui mängija leiab oma bingolaualt sama numbri, \n" +
                "mis ekraanil näidatud peab ta selle ära märkima peale vajutades. Eesmärgiks on saada \n" +
                "bingo ehk ära märkida kõik numbrid kas ühel real, veerul või diagonaalil. Head mängimist!");
        juhend.setStyle("-fx-background-color: rgba(121, 191, 216, 0.2);");
        juhend.setFont(Font.font("Banschrift Light", FontWeight.findByWeight(40), 14));
        juhend.setTextFill(new Color(0.1,0.5,0.4,1));
        //Nime sisestamine (esilehele)
        Label nimi = new Label("Sisesta oma nimi:");
        nimi.setFont(Font.font("Bahnschrift Light",FontWeight.BOLD,14));
        nimi.setTextFill(new Color(0.1,0.5,0.4,1));
        TextField mängijaNimi = new TextField();
        mängijaNimi.setPrefWidth(150);
        mängijaNimi.setMaxWidth(200);
        Label jätkamiseksVajutaEnter = new Label("Jätkamiseks vajuta ENTER");
        jätkamiseksVajutaEnter.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD,14));
        jätkamiseksVajutaEnter.setTextFill(new Color(0.1,0.5,0.4,1));

        //ANIMATSIOON (jätkamiseksVajutaEnter - sildile)
        FadeTransition ft = new FadeTransition(Duration.seconds(2), jätkamiseksVajutaEnter);
        ft.setFromValue(1.0);
        ft.setToValue(0.2);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();

        //Esileht
        BorderPane algus = new BorderPane();
        VBox vb = new VBox();
        vb.getChildren().addAll(nimi, mängijaNimi,jätkamiseksVajutaEnter);
        vb.setSpacing(10);
        vb.setAlignment(Pos.CENTER);

        algus.setTop(pealkiriHb);
        algus.setCenter(juhend);
        algus.setBottom(vb);
        algus.setMargin(vb,new Insets(20));

        BackgroundImage myBI= new BackgroundImage(new Image("https://img.freepik.com/free-vector/white-abstract-background_23-2148810113.jpg?size=626&ext=jpg"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        algus.setBackground(new Background(myBI));

        Scene stseen1 = new Scene(algus, 625, 300, Color.SNOW);
        primaryStage.setTitle("BINGO");
        primaryStage.setMaxHeight(800);
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(600);
        primaryStage.setScene(stseen1);
        primaryStage.show();

        //Mängu aken
        Stage teine = new Stage();
        teine.setTitle("BINGO");
        teine.setMinWidth(200);
        teine.setMinHeight(100);
        BorderPane bingo = new BorderPane();
        GridPane ruudustik = new GridPane();


        //Bingokaardi numbrite nupud
        Button B1 = new Button(String.valueOf(bingokaart[0][0]));
        B1.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        B1.setMinHeight(60);
        B1.setMaxHeight(60);
        B1.setMinWidth(60);
        B1.setMaxWidth(60);
        B1.setStyle("-fx-background-color: #e3e3e3");
        B1.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[0][0] == 0){
                hetkeseis[0][0] = 1;
                bingoseis.setBingoseis(hetkeseis);
                B1.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[0][0] = 0;
                bingoseis.setBingoseis(hetkeseis);
                B1.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button B2 = new Button(String.valueOf(bingokaart[0][1]));
        B2.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        B2.setMinHeight(60);
        B2.setMaxHeight(60);
        B2.setMinWidth(60);
        B2.setMaxWidth(60);
        B2.setStyle("-fx-background-color: #e3e3e3");
        B2.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[0][1] == 0){
                hetkeseis[0][1] = 1;
                bingoseis.setBingoseis(hetkeseis);
                B2.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[0][1] = 0;
                bingoseis.setBingoseis(hetkeseis);
                B2.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button B3 = new Button(String.valueOf(bingokaart[0][2]));
        B3.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        B3.setMinHeight(60);
        B3.setMaxHeight(60);
        B3.setMinWidth(60);
        B3.setMaxWidth(60);
        B3.setStyle("-fx-background-color: #e3e3e3");
        B3.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[0][2] == 0){
                hetkeseis[0][2] = 1;
                bingoseis.setBingoseis(hetkeseis);
                B3.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[0][2] = 0;
                bingoseis.setBingoseis(hetkeseis);
                B3.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button B4 = new Button(String.valueOf(bingokaart[0][3]));
        B4.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        B4.setMinHeight(60);
        B4.setMaxHeight(60);
        B4.setMinWidth(60);
        B4.setMaxWidth(60);
        B4.setStyle("-fx-background-color: #e3e3e3");
        B4.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[0][3] == 0){
                hetkeseis[0][3] = 1;
                bingoseis.setBingoseis(hetkeseis);
                B4.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[0][3] = 0;
                bingoseis.setBingoseis(hetkeseis);
                B4.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button B5 = new Button(String.valueOf(bingokaart[0][4]));
        B5.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        B5.setMinHeight(60);
        B5.setMaxHeight(60);
        B5.setMinWidth(60);
        B5.setMaxWidth(60);
        B5.setStyle("-fx-background-color: #e3e3e3");
        B5.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[0][4] == 0){
                hetkeseis[0][4] = 1;
                bingoseis.setBingoseis(hetkeseis);
                B5.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[0][4] = 0;
                bingoseis.setBingoseis(hetkeseis);
                B5.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button I1 = new Button(String.valueOf(bingokaart[1][0]));
        I1.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        I1.setMinHeight(60);
        I1.setMaxHeight(60);
        I1.setMinWidth(60);
        I1.setMaxWidth(60);
        I1.setStyle("-fx-background-color: #e3e3e3");
        I1.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[1][0] == 0){
                hetkeseis[1][0] = 1;
                bingoseis.setBingoseis(hetkeseis);
                I1.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[1][0] = 0;
                bingoseis.setBingoseis(hetkeseis);
                I1.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button I2 = new Button(String.valueOf(bingokaart[1][1]));
        I2.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        I2.setMinHeight(60);
        I2.setMaxHeight(60);
        I2.setMinWidth(60);
        I2.setMaxWidth(60);
        I2.setStyle("-fx-background-color: #e3e3e3");
        I2.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[1][1] == 0){
                hetkeseis[1][1] = 1;
                bingoseis.setBingoseis(hetkeseis);
                I2.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[1][1] = 0;
                bingoseis.setBingoseis(hetkeseis);
                I2.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button I3 = new Button(String.valueOf(bingokaart[1][2]));
        I3.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        I3.setMinHeight(60);
        I3.setMaxHeight(60);
        I3.setMinWidth(60);
        I3.setMaxWidth(60);
        I3.setStyle("-fx-background-color: #e3e3e3");
        I3.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[1][2] == 0){
                hetkeseis[1][2] = 1;
                bingoseis.setBingoseis(hetkeseis);
                I3.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[1][2] = 0;
                bingoseis.setBingoseis(hetkeseis);
                I3.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button I4 = new Button(String.valueOf(bingokaart[1][3]));
        I4.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        I4.setMinHeight(60);
        I4.setMaxHeight(60);
        I4.setMinWidth(60);
        I4.setMaxWidth(60);
        I4.setStyle("-fx-background-color: #e3e3e3");
        I4.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[1][3] == 0){
                hetkeseis[1][3] = 1;
                bingoseis.setBingoseis(hetkeseis);
                I4.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[1][3] = 0;
                bingoseis.setBingoseis(hetkeseis);
                I4.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button I5 = new Button(String.valueOf(bingokaart[1][4]));
        I5.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        I5.setMinHeight(60);
        I5.setMaxHeight(60);
        I5.setMinWidth(60);
        I5.setMaxWidth(60);
        I5.setStyle("-fx-background-color: #e3e3e3");
        I5.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[1][4] == 0){
                hetkeseis[1][4] = 1;
                bingoseis.setBingoseis(hetkeseis);
                I5.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[1][4] = 0;
                bingoseis.setBingoseis(hetkeseis);
                I5.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button N1 = new Button(String.valueOf(bingokaart[2][0]));
        N1.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        N1.setMinHeight(60);
        N1.setMaxHeight(60);
        N1.setMinWidth(60);
        N1.setMaxWidth(60);
        N1.setStyle("-fx-background-color: #e3e3e3");
        N1.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[2][0] == 0){
                hetkeseis[2][0] = 1;
                bingoseis.setBingoseis(hetkeseis);
                N1.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[2][0] = 0;
                bingoseis.setBingoseis(hetkeseis);
                N1.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button N2 = new Button(String.valueOf(bingokaart[2][1]));
        N2.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        N2.setMinHeight(60);
        N2.setMaxHeight(60);
        N2.setMinWidth(60);
        N2.setMaxWidth(60);
        N2.setStyle("-fx-background-color: #e3e3e3");
        N2.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[2][1] == 0){
                hetkeseis[2][1] = 1;
                bingoseis.setBingoseis(hetkeseis);
                N2.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[2][1] = 0;
                bingoseis.setBingoseis(hetkeseis);
                N2.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button N3 = new Button(String.valueOf(bingokaart[2][2]));
        N3.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        N3.setMinHeight(60);
        N3.setMaxHeight(60);
        N3.setMinWidth(60);
        N3.setMaxWidth(60);
        N3.setStyle("-fx-background-color: #e3e3e3");
        N3.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[2][2] == 0){
                hetkeseis[2][2] = 1;
                bingoseis.setBingoseis(hetkeseis);
                N3.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[2][2] = 0;
                bingoseis.setBingoseis(hetkeseis);
                N3.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button N4 = new Button(String.valueOf(bingokaart[2][3]));
        N4.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        N4.setMinHeight(60);
        N4.setMaxHeight(60);
        N4.setMinWidth(60);
        N4.setMaxWidth(60);
        N4.setStyle("-fx-background-color: #e3e3e3");
        N4.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[2][3] == 0){
                hetkeseis[2][3] = 1;
                bingoseis.setBingoseis(hetkeseis);
                N4.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[2][3] = 0;
                bingoseis.setBingoseis(hetkeseis);
                N4.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button N5 = new Button(String.valueOf(bingokaart[2][4]));
        N5.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        N5.setMinHeight(60);
        N5.setMaxHeight(60);
        N5.setMinWidth(60);
        N5.setMaxWidth(60);
        N5.setStyle("-fx-background-color: #e3e3e3");
        N5.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[2][4] == 0){
                hetkeseis[2][4] = 1;
                bingoseis.setBingoseis(hetkeseis);
                N5.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[2][4] = 0;
                bingoseis.setBingoseis(hetkeseis);
                N5.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button G1 = new Button(String.valueOf(bingokaart[3][0]));
        G1.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        G1.setMinHeight(60);
        G1.setMaxHeight(60);
        G1.setMinWidth(60);
        G1.setMaxWidth(60);
        G1.setStyle("-fx-background-color: #e3e3e3");
        G1.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[3][0] == 0){
                hetkeseis[3][0] = 1;
                bingoseis.setBingoseis(hetkeseis);
                G1.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[3][0] = 0;
                bingoseis.setBingoseis(hetkeseis);
                G1.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button G2 = new Button(String.valueOf(bingokaart[3][1]));
        G2.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        G2.setMinHeight(60);
        G2.setMaxHeight(60);
        G2.setMinWidth(60);
        G2.setMaxWidth(60);
        G2.setStyle("-fx-background-color: #e3e3e3");
        G2.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[3][1] == 0){
                hetkeseis[3][1] = 1;
                bingoseis.setBingoseis(hetkeseis);
                G2.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[3][1] = 0;
                bingoseis.setBingoseis(hetkeseis);
                G2.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button G3 = new Button(String.valueOf(bingokaart[3][2]));
        G3.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        G3.setMinHeight(60);
        G3.setMaxHeight(60);
        G3.setMinWidth(60);
        G3.setMaxWidth(60);
        G3.setStyle("-fx-background-color: #e3e3e3");
        G3.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[3][2] == 0){
                hetkeseis[3][2] = 1;
                bingoseis.setBingoseis(hetkeseis);
                G3.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[3][2] = 0;
                bingoseis.setBingoseis(hetkeseis);
                G3.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button G4 = new Button(String.valueOf(bingokaart[3][3]));
        G4.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        G4.setMinHeight(60);
        G4.setMaxHeight(60);
        G4.setMinWidth(60);
        G4.setMaxWidth(60);
        G4.setStyle("-fx-background-color: #e3e3e3");
        G4.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[3][3] == 0){
                hetkeseis[3][3] = 1;
                bingoseis.setBingoseis(hetkeseis);
                G4.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[3][3] = 0;
                bingoseis.setBingoseis(hetkeseis);
                G4.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button G5 = new Button(String.valueOf(bingokaart[3][4]));
        G5.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        G5.setMinHeight(60);
        G5.setMaxHeight(60);
        G5.setMinWidth(60);
        G5.setMaxWidth(60);
        G5.setStyle("-fx-background-color: #e3e3e3");
        G5.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[3][4] == 0){
                hetkeseis[3][4] = 1;
                bingoseis.setBingoseis(hetkeseis);
                G5.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[3][4] = 0;
                bingoseis.setBingoseis(hetkeseis);
                G5.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button O1 = new Button(String.valueOf(bingokaart[4][0]));
        O1.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        O1.setMinHeight(60);
        O1.setMaxHeight(60);
        O1.setMinWidth(60);
        O1.setMaxWidth(60);
        O1.setStyle("-fx-background-color: #e3e3e3");
        O1.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[4][0] == 0){
                hetkeseis[4][0] = 1;
                bingoseis.setBingoseis(hetkeseis);
                O1.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[4][0] = 0;
                bingoseis.setBingoseis(hetkeseis);
                O1.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button O2 = new Button(String.valueOf(bingokaart[4][1]));
        O2.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        O2.setMinHeight(60);
        O2.setMaxHeight(60);
        O2.setMinWidth(60);
        O2.setMaxWidth(60);
        O2.setStyle("-fx-background-color: #e3e3e3");
        O2.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[4][1] == 0){
                hetkeseis[4][1] = 1;
                bingoseis.setBingoseis(hetkeseis);
                O2.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[4][1] = 0;
                bingoseis.setBingoseis(hetkeseis);
                O2.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button O3 = new Button(String.valueOf(bingokaart[4][2]));
        O3.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        O3.setMinHeight(60);
        O3.setMaxHeight(60);
        O3.setMinWidth(60);
        O3.setMaxWidth(60);
        O3.setStyle("-fx-background-color: #e3e3e3");
        O3.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[4][2] == 0){
                hetkeseis[4][2] = 1;
                bingoseis.setBingoseis(hetkeseis);
                O3.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[4][2] = 0;
                bingoseis.setBingoseis(hetkeseis);
                O3.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button O4 = new Button(String.valueOf(bingokaart[4][3]));
        O4.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        O4.setMinHeight(60);
        O4.setMaxHeight(60);
        O4.setMinWidth(60);
        O4.setMaxWidth(60);
        O4.setStyle("-fx-background-color: #e3e3e3");
        O4.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[4][3] == 0){
                hetkeseis[4][3] = 1;
                bingoseis.setBingoseis(hetkeseis);
                O4.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[4][3] = 0;
                bingoseis.setBingoseis(hetkeseis);
                O4.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        Button O5 = new Button(String.valueOf(bingokaart[4][4]));
        O5.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 18));
        O5.setMinHeight(60);
        O5.setMaxHeight(60);
        O5.setMinWidth(60);
        O5.setMaxWidth(60);
        O5.setStyle("-fx-background-color: #e3e3e3");
        O5.setOnMouseClicked(event2 -> {
            int[][] hetkeseis = bingoseis.getBingoseis();
            if (hetkeseis[4][4] == 0){
                hetkeseis[4][4] = 1;
                bingoseis.setBingoseis(hetkeseis);
                O5.setStyle("-fx-background-color: #7de8d3");
            }
            else {
                hetkeseis[4][4] = 0;
                bingoseis.setBingoseis(hetkeseis);
                O5.setStyle("-fx-background-color: #e3e3e3");
            }
        });
        //Bingokaart
        ruudustik.add(B1, 0, 0);
        ruudustik.add(B2, 0, 1);
        ruudustik.add(B3, 0, 2);
        ruudustik.add(B4, 0, 3);
        ruudustik.add(B5, 0, 4);
        ruudustik.add(I1, 1, 0);
        ruudustik.add(I2, 1, 1);
        ruudustik.add(I3, 1, 2);
        ruudustik.add(I4, 1, 3);
        ruudustik.add(I5, 1, 4);
        ruudustik.add(N1, 2, 0);
        ruudustik.add(N2, 2, 1);
        ruudustik.add(N3, 2, 2);
        ruudustik.add(N4, 2, 3);
        ruudustik.add(N5, 2, 4);
        ruudustik.add(G1, 3, 0);
        ruudustik.add(G2, 3, 1);
        ruudustik.add(G3, 3, 2);
        ruudustik.add(G4, 3, 3);
        ruudustik.add(G5, 3, 4);
        ruudustik.add(O1, 4, 0);
        ruudustik.add(O2, 4, 1);
        ruudustik.add(O3, 4, 2);
        ruudustik.add(O4, 4, 3);
        ruudustik.add(O5, 4, 4);

        //Numbrite tabel
        GridPane tabel = new GridPane();
        tabel.setPrefSize(200, 100);
        ColumnConstraints veerg1 = new ColumnConstraints(30);
        RowConstraints rida1 = new RowConstraints(23);
        tabel.getRowConstraints().addAll(rida1,rida1,rida1,rida1,rida1,rida1,rida1,rida1,rida1,rida1,rida1,rida1,
                rida1,rida1,rida1);
        tabel.getColumnConstraints().addAll(veerg1, veerg1, veerg1, veerg1, veerg1);

        int[] tabeliNr = new int[75];
        for (int i = 0; i < 75; i++) {
            tabeliNr[i] = i;
        }
        //sildid numbritele
        List<Label> numbrid1 = new ArrayList<>();
        Label nr1 = new Label("1");
        Label nr2 = new Label("2");
        Label nr3 = new Label("3");
        Label nr4 = new Label("4");
        Label nr5 = new Label("5");
        Label nr6 = new Label("6");
        Label nr7 = new Label("7");
        Label nr8 = new Label("8");
        Label nr9 = new Label("9");
        Label nr10 = new Label("10");
        Label nr11 = new Label("11");
        Label nr12 = new Label("12");
        Label nr13 = new Label("13");
        Label nr14 = new Label("14");
        Label nr15 = new Label("15");
        Label nr16 = new Label("16");
        Label nr17 = new Label("17");
        Label nr18 = new Label("18");
        Label nr19 = new Label("19");
        Label nr20 = new Label("20");
        Label nr21 = new Label("21");
        Label nr22 = new Label("22");
        Label nr23 = new Label("23");
        Label nr24 = new Label("24");
        Label nr25 = new Label("25");
        Label nr26 = new Label("26");
        Label nr27 = new Label("27");
        Label nr28 = new Label("28");
        Label nr29 = new Label("29");
        Label nr30 = new Label("30");
        Label nr31 = new Label("31");
        Label nr32 = new Label("32");
        Label nr33 = new Label("33");
        Label nr34 = new Label("34");
        Label nr35 = new Label("35");
        Label nr36 = new Label("36");
        Label nr37 = new Label("37");
        Label nr38 = new Label("38");
        Label nr39 = new Label("39");
        Label nr40 = new Label("40");
        Label nr41 = new Label("41");
        Label nr42 = new Label("42");
        Label nr43 = new Label("43");
        Label nr44 = new Label("44");
        Label nr45 = new Label("45");
        Label nr46 = new Label("46");
        Label nr47 = new Label("47");
        Label nr48 = new Label("48");
        Label nr49 = new Label("49");
        Label nr50 = new Label("50");
        Label nr51 = new Label("51");
        Label nr52 = new Label("52");
        Label nr53 = new Label("53");
        Label nr54 = new Label("54");
        Label nr55 = new Label("55");
        Label nr56 = new Label("56");
        Label nr57 = new Label("57");
        Label nr58 = new Label("58");
        Label nr59 = new Label("59");
        Label nr60 = new Label("60");
        Label nr61 = new Label("61");
        Label nr62 = new Label("62");
        Label nr63 = new Label("63");
        Label nr64 = new Label("64");
        Label nr65 = new Label("65");
        Label nr66 = new Label("66");
        Label nr67 = new Label("67");
        Label nr68 = new Label("68");
        Label nr69 = new Label("69");
        Label nr70 = new Label("70");
        Label nr71 = new Label("71");
        Label nr72 = new Label("72");
        Label nr73 = new Label("73");
        Label nr74 = new Label("74");
        Label nr75 = new Label("75");

        Collections.addAll(numbrid1,nr1,nr2,nr3,nr4,nr5,nr6,nr7,nr8,nr9,nr10,nr11,nr12,nr13,nr14,nr15,nr16,nr17,nr18,nr19,nr20,nr21,nr22,nr23,
                nr24,nr25,nr26,nr27,nr28,nr29,nr30,nr31,nr32,nr33,nr34,nr35,nr36,nr37,nr38,nr39,nr40,nr41,nr42,nr43,nr44,nr45,nr46,nr47,
                nr48,nr49,nr50,nr51,nr52,nr53,nr54,nr55,nr56,nr57,nr58,nr59,nr60,nr61,nr62,nr63,nr64,nr65,nr66,nr67,nr68,nr69,nr70,nr71,nr72,
                nr73,nr74,nr75);
        for (int i = 0; i < numbrid1.size(); i++) {
            numbrid1.get(i).setFont(Font.font("Bahnschrift Light", FontWeight.BOLD,15));
            numbrid1.get(i).setTextFill(Color.DARKRED);
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 15; j++) {
                tabel.add(numbrid1.get(i * 15 + j), i, j);
            }
        }

        //Numbrinäitaja ja "start" nupp
        Button start = new Button("START");
        Circle NrNäitajaRing = new Circle(30, new Color(0.67,0.84,0.9,0.3));
        NrNäitajaRing.setStroke(Color.DARKCYAN);
        StackPane Näitaja = new StackPane();
        Label käesolevNr = new Label("NR");
        käesolevNr.setFont(Font.font("Bahnschrift Light", FontWeight.EXTRA_BOLD, 20));
        käesolevNr.setTextFill(Color.DARKGREEN);
        Näitaja.getChildren().addAll(NrNäitajaRing, käesolevNr);

        HBox nupud = new HBox();
        Button nuppBingo = new Button("BINGO");
        nupud.getChildren().addAll(nuppBingo,Näitaja, start);
        nupud.setSpacing(20);
        nupud.setPadding(new Insets(10,0,5,0));
        nupud.setAlignment(Pos.CENTER);

        //Kui vajutad "Start" nupule, siis
        //Näitab numbreid ise iga 5 sekundi järel - ja kogu mehaanika selle taga
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            private int i = 0;
            int j;
            public void run() {
                if (i < 75) {
                    j = LoosiNumbrid.getLoosiNumbrid().get(i);
                    numbrid1.get(j-1).setTextFill(Color.LIMEGREEN);
                    Platform.runLater(new Runnable() {
                        public void run() {
                            käesolevNr.setText(Integer.toString(j));
                        }
                    });
                    kasutatudNumbrid.add(j);
                    i++;
                }
                else
                    timer.cancel();

            }
        };

        start.setOnMouseClicked(event2 -> {
            start.setDisable(true);
            timer.scheduleAtFixedRate(task, 0, 5000);
        });
        //Horisontaalne bingo
        Label bingoHB = new Label("B");
        bingoHB.setFont(Font.font("Bahnschrift Light", FontWeight.EXTRA_BOLD, 25));
        bingoHB.setTextFill(Color.RED);
        bingoHB.setPadding(new Insets(0,50,0,22));
        Label bingoHI = new Label("I");
        bingoHI.setFont(Font.font("Bahnschrift Light", FontWeight.EXTRA_BOLD, 25));
        bingoHI.setTextFill(Color.ORANGE);
        bingoHI.setPadding(new Insets(0,45,0,0));
        Label bingoHN = new Label("N");
        bingoHN.setFont(Font.font("Bahnschrift Light", FontWeight.EXTRA_BOLD, 25));
        bingoHN.setTextFill(Color.BLUE);
        bingoHN.setPadding(new Insets(0,40,0,0));
        Label bingoHG = new Label("G");
        bingoHG.setFont(Font.font("Bahnschrift Light", FontWeight.EXTRA_BOLD, 25));
        bingoHG.setTextFill(Color.GREEN);
        bingoHG.setPadding(new Insets(0,40,0,0));
        Label bingoHO = new Label("O");
        bingoHO.setFont(Font.font("Bahnschrift Light", FontWeight.EXTRA_BOLD, 25));
        bingoHO.setTextFill(Color.PURPLE);

        HBox bingoH = new HBox();
        bingoH.getChildren().addAll(bingoHB,bingoHI,bingoHN,bingoHG,bingoHO);

        VBox nupudjaRuudustikjaBingo = new VBox();
        nupudjaRuudustikjaBingo.getChildren().addAll(bingoH,ruudustik,nupud);
        bingo.setRight(nupudjaRuudustikjaBingo);
        ruudustik.setPadding(new Insets(5,20,0,5));
        //--
        Scene stseen2 = new Scene(bingo, 600, 415, Color.SNOW);
        bingo.setBackground(new Background(myBI));
        teine.setScene(stseen2);
        teine.setMinHeight(450);
        teine.setMinWidth(600);

        //Bingo saamine, kontroll, faili kirjutamine ja lugemine õige korral, kolmas aken edetabeli jaoks
        Stage kolmas = new Stage();
        kolmas.setMinWidth(400);
        kolmas.setMinHeight(300);
        BingoKontroll kontroll = new BingoKontroll();
        VBox edetabel = new VBox();
        Scene stseen3 = new Scene(edetabel, 600, 415, Color.SNOW);
        edetabel.setBackground(new Background(myBI));
        kolmas.setScene(stseen3);
        nuppBingo.setOnMouseClicked(event -> {
            kolmas.setOnCloseRequest((ae) -> {
                Platform.exit();
                System.exit(0);
            });
            edetabel.setAlignment(Pos.CENTER);
            edetabel.setBackground(new Background(myBI));
            if ( kontroll.bingoKontroll(bingoseis.getBingoseis()) &&
                    kontroll.numbriKontroll(bingoseis.getBingoseis(), kasutatudNumbrid, bingokaart)){
                Date kuupäev = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                BingoMängija mängija = new BingoMängija(mängijaNimi.getText(), kasutatudNumbrid.size(), formatter.format(kuupäev));
                BingoFail bingofail = new BingoFail("BingoTulemused.txt");
                bingofail.kirjutaFaili(bingofail.getFailinimi(), mängija);
                String[] tulemused = bingofail.loeFailist(bingofail.getFailinimi());

                //Silt "EDETABEL" ja selle efektid
                Label edetabelPealkiri = new Label("E D E T A B E L");
                edetabelPealkiri.setFont(Font.font("Bahnschrift Light", FontWeight.BOLD, 40));
                edetabelPealkiri.setTextFill(Color.DARKORCHID);
                edetabelPealkiri.setTextFill(new LinearGradient(0,0,0,1,
                        true, CycleMethod.NO_CYCLE, new Stop(0.2f,Color.DARKORCHID),
                        new Stop(1.0f,Color.GOLD)));
                Bloom bloom = new Bloom();
                bloom.setThreshold(0.3);
                edetabelPealkiri.setEffect(bloom);

                //Sildi "EDETABEL" animatsioon
                Path path = new Path();
                path.getElements().add(new MoveTo(135,20));
                path.getElements().add(new VLineTo(10));
                path.setStroke(Color.TRANSPARENT);
                PathTransition pallHüppab = new PathTransition();
                pallHüppab.setDuration(Duration.millis(1000));
                pallHüppab.setPath(path);
                pallHüppab.setNode(edetabelPealkiri);
                pallHüppab.setCycleCount(Timeline.INDEFINITE);
                pallHüppab.setAutoReverse(true);
                pallHüppab.play();

                //koha/tulemuste sildid
                Label esimeneKoht = new Label("1. " + tulemused[0]);
                esimeneKoht.setFont(Font.font("Bahnschrift Light", 20));
                esimeneKoht.setTextFill(Color.RED);
                Label teineKoht = new Label("2. " + tulemused[1]);
                teineKoht.setFont(Font.font("Bahnschrift Light", 20));
                teineKoht.setTextFill(Color.ORANGERED);
                Label kolmasKoht = new Label("3. " + tulemused[2]);
                kolmasKoht.setFont(Font.font("Bahnschrift Light", 20));
                kolmasKoht.setTextFill(Color.BLUE);
                Label neljasKoht = new Label("4. " + tulemused[3]);
                neljasKoht.setFont(Font.font("Bahnschrift Light", 20));
                neljasKoht.setTextFill(Color.GREEN);
                Label viiesKoht = new Label("5. " + tulemused[4]);
                viiesKoht.setFont(Font.font("Bahnschrift Light", 20));
                viiesKoht.setTextFill(Color.PURPLE);
                edetabel.getChildren().addAll(edetabelPealkiri, esimeneKoht, teineKoht, kolmasKoht, neljasKoht, viiesKoht);

                kolmas.setTitle("EDETABEL");
                edetabel.setAlignment(Pos.CENTER);
                kolmas.show();
                teine.close();


            }
            else {
                Alert b = new Alert(Alert.AlertType.ERROR);
                b.setContentText("Valebingo! :(");
                b.show();
            }
        });



        //Mäng algab enteri vajutamisega
        mängijaNimi.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER))
            {
                if (mängijaNimi.getText().trim().isEmpty()) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Sul ei ole nimi kirjutatud!");
                    a.show();
                    throw new BingoErind("Nimi puudub");
                }
                else{
                    VBox tabelJaBingo = new VBox();
                    tabelJaBingo.getChildren().addAll(pealkiriHb,tabel);
                    bingo.setLeft(tabelJaBingo);
                    bingo.setMargin(tabelJaBingo,new Insets(0,20,0,20));
                    pealkiriHb.setAlignment(Pos.TOP_LEFT);
                    pealkiriHb.setPadding(new Insets(0,0,0,18));
                    tabel.setPadding(new Insets(0,0,0,15));
                    teine.show();
                    primaryStage.hide();
                }
            }
        });

        //Sulgemised
        primaryStage.setOnCloseRequest((ae) -> {
            Platform.exit();
            System.exit(0);
        });

        teine.setOnCloseRequest((ae) -> {
            Platform.exit();
            System.exit(0);
        });

        kolmas.setOnCloseRequest((ae) -> {
            Platform.exit();
            System.exit(0);
        });

    }
}