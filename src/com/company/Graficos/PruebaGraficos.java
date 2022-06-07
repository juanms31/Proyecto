package com.company.Graficos;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class PruebaGraficos extends JFrame{

    public void metodoGrafico(){
        DefaultCategoryDataset datos = new DefaultCategoryDataset();

        int n1, n2, n3;
        n1 = 8;
        n2 = 5;
        n3 = 7;

        datos.setValue(n1, "Matematicas", "Pedro");
        datos.setValue(n1, "Español", "Pedro");
        datos.setValue(n2, "Matematicas", "MAnolo");
        datos.setValue(n2, "Español", "MAnolo");
        datos.setValue(n3, "Matematicas", "Serafin");
        datos.setValue(n3, "Español", "Serafin");

        //Nombre grafico
        //Nombre barras o columnas
        //datos del grafico
        //orientacion
        //leyenda de barras individuales por color
        //herramientas
        //url del grafico
        JFreeChart graficoBarras = ChartFactory.createBarChart3D(
                "Calificacion de matematicas",
                "Estudiantes de matematicas",
                "Calificacion",
                datos,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        //PAnel de charpanel
        ChartPanel panel = new ChartPanel(graficoBarras);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(400, 200));

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

        //TODO ahora hay q hacer que entren parametros y vamos hasiendo cositas chulas
        //TODO la dificultad estaria (en el caso de usarlo en las propias tablas) de add hilos >> Pero se me da bien coser
    }

    public static void main(String[] args) {
        PruebaGraficos pruebaGraficos = new PruebaGraficos();
        pruebaGraficos.metodoGrafico();
    }
}