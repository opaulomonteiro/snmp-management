package com.sd.graficos;

import com.sd.SnmpManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.List;

public class GraficoLink extends JFrame {

    // A - UTILIZAÇÃ DO LINKG = [ifInOctets, ifOutOctets, ifSpeed]
    private static String ifInOctets = "1.3.6.1.2.1.2.2.1.10.3";
    private static String ifOutOctets = "1.3.6.1.2.1.2.2.1.16.3";
    private static String ifSpeed = "1.3.6.1.2.1.2.2.1.5.1";

    private SnmpManager snmpManager;

    public GraficoLink(SnmpManager snmpManager) {
        this.snmpManager = snmpManager;
        setTitle("Gráfico Taxa de Utilização do Link");
        setSize(1000, 700);
        criarGrafico();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void criarGrafico() {
        DefaultCategoryDataset octets = new DefaultCategoryDataset();
        List<ValorGrafico> octetsInValues = this.snmpManager.getHashForMib().get(ifInOctets);
        List<ValorGrafico> octetsOutValues = this.snmpManager.getHashForMib().get(ifOutOctets);
        Long ifSpeedValue = this.snmpManager.getHashForMib().get(ifSpeed).get(0).getValor();
        octetsInValues.remove(0);
        octetsOutValues.remove(0);


        for (ValorGrafico inValues : octetsInValues) {
            for (ValorGrafico outValues : octetsOutValues) {
                System.out.println("vALOR: " + ((float) inValues.getDiferenca() + outValues.getDiferenca()) * 8 / ifSpeedValue);
                octets.addValue(((float) inValues.getDiferenca() + outValues.getDiferenca()) * 8 / ifSpeedValue,
                        "packages",
                        inValues.getTempo());
            }
        }

        JFreeChart grafico = ChartFactory.createLineChart("Gráfico do Link", "Tempo",
                "Pacotes", octets, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel painel = new ChartPanel(grafico);
        add(painel);
    }
}