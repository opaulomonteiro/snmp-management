package com.sd;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class GraficoBarra extends JFrame {

    public GraficoBarra() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Grafico Barra");
        setSize(1000, 700);

        criarGrafico();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void criarGrafico() {

        DefaultCategoryDataset barra = new DefaultCategoryDataset();
        barra.addValue(40.5, "valor", "Link");
        barra.addValue(38.2, "valor", "Ip - recebidos");
        barra.addValue(37.3, "valor", "Ip - enviados");
        barra.addValue(31.5, "valor", "tcp - recebidos");
        barra.addValue(35.7, "valor", "tcp - enviados");
        barra.addValue(42.5, "valor", "udp - recebidos");
        barra.addValue(42.5, "valor", "udp - enviados");
        barra.addValue(42.5, "valor", "icmp - recebidos");
        barra.addValue(42.5, "valor", "icmp - enviados");
        barra.addValue(42.5, "valor", "snmp - recebidos");
        barra.addValue(42.5, "valor", "snmp - enviados");


        JFreeChart grafico = ChartFactory.createLineChart3D("Meu Grafico", "Tempo",
                "Pacotes", barra, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel painel = new ChartPanel(grafico);
        add(painel);
    }
}