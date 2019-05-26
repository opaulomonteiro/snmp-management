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

public class GraficoIcmp extends JFrame {

    // E - TAXA DE ICMP RECEBIDOS E ENVIADOS
    private static String icmpInMsgs = "1.3.6.1.2.1.5.1.0";
    private static String icmpOutMsgs = "1.3.6.1.2.1.5.14.0";

    private SnmpManager snmpManager;

    public GraficoIcmp(SnmpManager snmpManager) {
        this.snmpManager = snmpManager;
        setTitle("Gráfico Icmp");
        setSize(1000, 700);
        criarGrafico();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void criarGrafico() {
        DefaultCategoryDataset icmp = new DefaultCategoryDataset();
        List<ValorGrafico> icmpInMsgsValues = this.snmpManager.getHashForMib().get(icmpInMsgs);
        List<ValorGrafico> icmpOutMsgsValues = this.snmpManager.getHashForMib().get(icmpOutMsgs);

        icmpInMsgsValues.remove(0);
        icmpOutMsgsValues.remove(0);

        for (ValorGrafico valorGrafico : icmpInMsgsValues) {
            icmp.addValue(valorGrafico.getDiferenca(), "inMessage", valorGrafico.getTempo());
        }
        for (ValorGrafico valorGrafico : icmpOutMsgsValues) {
            icmp.addValue(valorGrafico.getDiferenca(), "outMessages", valorGrafico.getTempo());
        }

        JFreeChart grafico = ChartFactory.createLineChart("Gráfico do ICMP", "Tempo",
                "Pacotes", icmp, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel painel = new ChartPanel(grafico);
        add(painel);
    }
}