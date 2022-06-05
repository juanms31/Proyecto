package com.company.Graficos;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraficosBasicos extends JFrame{

    public void getFrame(ChartPanel panel){
        //JFrame
        setSize(600, 300);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setVisible(true);

        //Creo panel de jframe
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jPanel.add(panel, BorderLayout.NORTH);
        add(jPanel, BorderLayout.CENTER);

        pack();
        repaint();
    }

    public ChartPanel getGraficoBarras(ArrayList<NodoGraficoBarras> listGraficData, String title, String categoryAxis, String valueAxis){
        //DATOS A TENER EN CUENTA DE JFreeChart
        //Nombre grafico
        //Nombre barras o columnas
        //datos del grafico
        //orientacion
        //leyenda de barras individuales por color
        //herramientas
        //url del grafico

        DefaultCategoryDataset datos = new DefaultCategoryDataset();
        for (NodoGraficoBarras p : listGraficData) {
            datos.setValue(p.getValue(), p.getRowKey(), p.getColumnKey());
        }

        JFreeChart graficoBarras = ChartFactory.createBarChart3D(
                title,
                categoryAxis,
                valueAxis,
                datos,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        //Panel de charpanel
        ChartPanel panel = new ChartPanel(graficoBarras);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(400, 200));

        return panel;
    }

    public ChartPanel metodoGraficoCircular(ArrayList<NodoGraficoCircular> listNodos, String title){
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (NodoGraficoCircular nCircular : listNodos) {
            dataset.setValue(nCircular.getComparableKey(), nCircular.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart3D(
                "Stock moviles",
                dataset,
                true,
                true,
                false
        );

        //Panel de charpanel
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(400, 200));

        return panel;
    }

    public static void main(String[] args) {
        NodoGraficoCircular nCircular1 = new NodoGraficoCircular();
        nCircular1.setComparableKey("Zapatillas");
        nCircular1.setValue(5.0);

        NodoGraficoCircular nCircular2 = new NodoGraficoCircular();
        nCircular2.setComparableKey("Camisetas");
        nCircular2.setValue(6.2);

        NodoGraficoCircular nCircular3 = new NodoGraficoCircular();
        nCircular3.setComparableKey("Pantalones");
        nCircular3.setValue(2.2);

        NodoGraficoCircular nCircular4 = new NodoGraficoCircular();
        nCircular4.setComparableKey("Casco");
        nCircular4.setValue(3.0);

        NodoGraficoCircular nCircular5 = new NodoGraficoCircular();
        nCircular5.setComparableKey("Anillos");
        nCircular5.setValue(5.54);

        ArrayList<NodoGraficoCircular> listNodoCircular = new ArrayList<>();
        listNodoCircular.add(nCircular1);
        listNodoCircular.add(nCircular2);
        listNodoCircular.add(nCircular3);
        listNodoCircular.add(nCircular4);
        listNodoCircular.add(nCircular5);

        GraficosBasicos graficosBasicos = new GraficosBasicos();
        var panel = graficosBasicos.metodoGraficoCircular(listNodoCircular, "ROPA");

        graficosBasicos.getFrame(panel);
    }
}
