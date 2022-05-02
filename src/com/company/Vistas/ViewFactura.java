package com.company.Vistas;

import javax.swing.*;

public class ViewFactura extends JFrame{
    private JPanel panelPrincipal;
    private JTabbedPane panelPestanas;
    private JPanel pTableFactura;
    private JTable tFacturas;
    private JPanel buscador;
    private JTextField filtro;
    private JButton buscar;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton reload;
    private JPanel panelBotones;

    public ViewFactura(){
        initView();
    }

    private void initView(){
        add(panelPrincipal);
        setVisible(true);
        setSize(800, 600);
    }
}
