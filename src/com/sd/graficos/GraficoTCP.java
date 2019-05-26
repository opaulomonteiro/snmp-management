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

public class GraficoTCP extends JFrame {

    // C - TAXA DE TCP RECEBIDOS E ENVIADOS
    private static String tcpInSegs = "1.3.6.1.2.1.6.10.0";
    private static String tcpOutSegs = "1.3.6.1.2.1.6.11.0";

    private SnmpManager snmpManager;

    public GraficoTCP(SnmpManager snmpManager) {
        this.snmpManager = snmpManager;
        setTitle("Gráfico TCP");
        setSize(1000, 700);
        criarGrafico();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void criarGrafico() {
        DefaultCategoryDataset tcp = new DefaultCategoryDataset();
        List<ValorGrafico> tcpInValues = this.snmpManager.getHashForMib().get(tcpInSegs);
        List<ValorGrafico> tcpOutValues = this.snmpManager.getHashForMib().get(tcpOutSegs);

        tcpInValues.remove(0);
        tcpOutValues.remove(0);
        for (ValorGrafico valorGrafico : tcpInValues) {
            tcp.addValue(valorGrafico.getDiferenca(), "tcpInPackages", valorGrafico.getTempo());
        }

        for (ValorGrafico valorGrafico : tcpOutValues) {
            tcp.addValue(valorGrafico.getDiferenca(), "tcpOutPackages", valorGrafico.getTempo());
        }

        JFreeChart grafico = ChartFactory.createLineChart("Gráfico do TCP", "Tempo",
                "Pacotes", tcp, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel painel = new ChartPanel(grafico);
        add(painel);
    }
}
