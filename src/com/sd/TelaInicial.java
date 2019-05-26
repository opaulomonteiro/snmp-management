package com.sd;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Timer;


public class TelaInicial extends Application {

    private Stage primaryStage;
    private Scene cena1;
    private LinkedHashMap<String, List<ValorGrafico>> hashForMib = new LinkedHashMap<>();
    private SnmpManager snmpManager;


    public Scene TelaInicial() {
        GridPane telaInicial = new GridPane();
        telaInicial.setAlignment(Pos.CENTER);
        Button iniciar = new Button("Iniciar");
        iniciar.setPrefSize(80, 40);
        iniciar.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12));

        Text inserirIP = new Text("Insira o IP:");
        TextField ip = new TextField();

        Text insiraComunicade = new Text("Insira a comunidade:");
        TextField comunidade = new TextField();

        Text definicaoTempo = new Text("Insira o tempo:");
        TextField tempo = new TextField();


        HBox hbox2 = new HBox();
        hbox2.setSpacing(10);
        hbox2.getChildren().add(inserirIP);
        hbox2.getChildren().add(ip);

        HBox hbox3 = new HBox();
        hbox3.setSpacing(10);
        hbox3.getChildren().add(insiraComunicade);
        hbox3.getChildren().add(comunidade);

        HBox hbox4 = new HBox();
        hbox4.setSpacing(10);
        hbox4.getChildren().add(definicaoTempo);
        hbox4.getChildren().add(tempo);

        HBox hbox01 = new HBox();
        hbox01.setSpacing(50);
        hbox01.getChildren().add(iniciar);

        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.getChildren().add(hbox2);
        vbox.getChildren().add(hbox3);
        vbox.getChildren().add(hbox4);
        vbox.getChildren().add(hbox01);

        telaInicial.add(vbox, 0, 0);

        cena1 = new Scene(telaInicial, 450, 300);

        iniciar.setOnAction(e -> {

            this.snmpManager = new SnmpManager(ip.getText(), comunidade.getText(), hashForMib);

            SnmpManagerScheduler scheduler = new SnmpManagerScheduler(System.currentTimeMillis(), snmpManager);

            new Timer().scheduleAtFixedRate(scheduler, 0, Integer.valueOf(tempo.getText()) * 1000);
            Stage secondTage = new Stage();
            secondTage.setScene(new TelaGraficos().TelaInicial(snmpManager));
            secondTage.show();
        });
        return cena1;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setScene(TelaInicial());
        primaryStage.setTitle("Trabalho Redes");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}