package com.sd.graficos;

import com.sd.SnmpManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.List;

public class GraficoIP extends JFrame {

    // B - TAXA DE DATAGRAMAS RECEBIDOS E ENVIADOS
    private static String ipInReceives = "1.3.6.1.2.1.4.3.0";
    private static String ipOutRequests = "1.3.6.1.2.1.4.10.0";
    private SnmpManager snmpManager;

    public GraficoIP(SnmpManager snmpManager) {
        this.snmpManager = snmpManager;
        setTitle("Gráfico IP");
        setSize(1000, 700);
        criarGrafico();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void criarGrafico() {

        DefaultCategoryDataset ip = new DefaultCategoryDataset();
        List<ValorGrafico> ipInValues = this.snmpManager.getHashForMib().get(ipInReceives);
        List<ValorGrafico> ipOutValues = this.snmpManager.getHashForMib().get(ipOutRequests);

        ipInValues.remove(0);
        ipOutValues.remove(0);

        for (ValorGrafico valorGrafico : ipInValues) {
            ip.addValue(valorGrafico.getDiferenca(), "ipInPackages", valorGrafico.getTempo());
        }
        for (ValorGrafico valorGrafico : ipOutValues) {
            ip.addValue(valorGrafico.getDiferenca(), "ipOutPackages", valorGrafico.getTempo());
        }

        JFreeChart grafico = ChartFactory.createLineChart("Gráfico do IP", "Tempo",
                "Pacotes", ip, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel painel = new ChartPanel(grafico);
        add(painel);
    }
}