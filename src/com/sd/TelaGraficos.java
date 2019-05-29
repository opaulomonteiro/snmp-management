package com.sd;

import com.sd.graficos.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TelaGraficos extends Application {

    private Stage primaryStage;
    private Scene cena1;
    private SnmpManager snmpManager;

    public Scene TelaInicial(SnmpManager snmpManager) {
        this.snmpManager = snmpManager;
        GridPane telaPrincipal = new GridPane();
        telaPrincipal.setAlignment(Pos.CENTER);

        Text texto = new Text("Escolha o gráfico que deseja ver!");
        texto.setFont(Font.font("Calibri", FontWeight.LIGHT, FontPosture.REGULAR, 18));


        Button link = new Button("Link");
        link.setPrefSize(80, 40);
        link.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12));
        link.setOnAction(e -> new GraficoLink(this.snmpManager));

        Button ip = new Button("IP");
        ip.setPrefSize(80, 40);
        ip.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12));
        ip.setOnAction(e -> new GraficoIP(this.snmpManager));

        Button udp = new Button("UDP");
        udp.setPrefSize(80, 40);
        udp.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12));
        udp.setOnAction(e -> new GraficoUDP(this.snmpManager));

        Button tcp = new Button("TCP");
        tcp.setPrefSize(80, 40);
        tcp.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12));
        tcp.setOnAction(e -> new GraficoTCP(this.snmpManager));

        Button icmp = new Button("ICMP");
        icmp.setPrefSize(80, 40);
        icmp.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12));
        icmp.setOnAction(e -> new GraficoIcmp(this.snmpManager));

        Button snmp = new Button("SNMP");
        snmp.setPrefSize(80, 40);
        snmp.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12));
        snmp.setOnAction(e -> new GraficoSNMP(this.snmpManager));

        HBox hbox1 = new HBox();
        hbox1.setSpacing(50);
        hbox1.getChildren().add(link);
        hbox1.getChildren().add(ip);
        hbox1.getChildren().add(tcp);

        HBox hbox2 = new HBox();
        hbox2.setSpacing(50);
        hbox2.getChildren().add(udp);
        hbox2.getChildren().add(icmp);
        hbox2.getChildren().add(snmp);

        VBox vbox = new VBox();
        vbox.setSpacing(30);
        vbox.getChildren().add(texto);
        vbox.getChildren().add(hbox1);
        vbox.getChildren().add(hbox2);

        telaPrincipal.add(vbox, 0, 0);
        cena1 = new Scene(telaPrincipal, 500, 300);
        return cena1;
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
