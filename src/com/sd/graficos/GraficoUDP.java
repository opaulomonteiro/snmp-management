package com.sd.graficos;

import com.sd.SnmpManager;
import com.sd.ValorGrafico;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.List;

public class GraficoUDP extends JFrame {

    // D -  TAXA DE UDP RECEBIDOS E ENVIADOS
    private static String udpInDatagrams = "1.3.6.1.2.1.7.1.0";
    private static String udpOutDatagrams = "1.3.6.1.2.1.7.4.0";

    private SnmpManager snmpManager;

    public GraficoUDP(SnmpManager snmpManager) {
        this.snmpManager = snmpManager;
        setTitle("Gráfico UDP");
        setSize(1000, 700);
        criarGrafico();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void criarGrafico() {
        DefaultCategoryDataset udp = new DefaultCategoryDataset();
        List<ValorGrafico> udpInValues = this.snmpManager.getHashForMib().get(udpInDatagrams);
        List<ValorGrafico> udpOutValues = this.snmpManager.getHashForMib().get(udpOutDatagrams);

        udpInValues.remove(0);
        udpOutValues.remove(0);

        for (ValorGrafico valorGrafico : udpInValues) {
            udp.addValue(valorGrafico.getDiferenca(), "udpInPackages", valorGrafico.getTempo());
        }
        for (ValorGrafico valorGrafico : udpOutValues) {
            udp.addValue(valorGrafico.getDiferenca(), "udpOutPackages", valorGrafico.getTempo());
        }

        JFreeChart grafico = ChartFactory.createLineChart("Gráfico do UDP", "Tempo",
                "Pacotes", udp, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel painel = new ChartPanel(grafico);
        add(painel);
    }
}