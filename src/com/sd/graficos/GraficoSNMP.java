package com.sd.graficos;

import com.sd.SnmpManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.List;

public class GraficoSNMP extends JFrame {

    // F - TAXA DE SNMP RECEBIDOS E ENVIADOS
    private static String snmpInPkts = "1.3.6.1.2.1.11.1.0";
    private static String snmpOutPkts = "1.3.6.1.2.1.11.2.0";
    private SnmpManager snmpManager;

    public GraficoSNMP(SnmpManager snmpManager) {
        this.snmpManager = snmpManager;
        setTitle("Gráfico SNMP");
        setSize(1000, 700);
        criarGrafico();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void criarGrafico() {
        DefaultCategoryDataset snmp = new DefaultCategoryDataset();
        List<ValorGrafico> snmpInValues = this.snmpManager.getHashForMib().get(snmpInPkts);
        List<ValorGrafico> snmpOutValues = this.snmpManager.getHashForMib().get(snmpOutPkts);
        snmpInValues.remove(0);
        snmpOutValues.remove(0);

        for (ValorGrafico valorGrafico : snmpInValues) {
            snmp.addValue(valorGrafico.getDiferenca(), "snmpInPackages", valorGrafico.getTempo());
        }
        for (ValorGrafico valorGrafico : snmpOutValues) {
            snmp.addValue(valorGrafico.getDiferenca(), "snmpOutPackages", valorGrafico.getTempo());
        }


        JFreeChart grafico = ChartFactory.createLineChart("Gráfico do SNMP", "Tempo",
                "Pacotes", snmp, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel painel = new ChartPanel(grafico);
        add(painel);
    }
}