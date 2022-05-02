package com.company.Vistas;

import javax.swing.*;

public class ViewMaterial extends JFrame{
    private JPanel panelPrincipal;
    private JTabbedPane panelPestanas;
    private JPanel Medicos;
    private JTable medicosTable;
    private JPanel buscador;
    private JTextField filtro;
    private JButton reload;
    private JButton buscar;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JPanel panelBotones;

    public ViewMaterial(){
        initView();
    }

    private void initView(){
        add(panelPrincipal);
        setVisible(true);
        setSize(800, 600);
    }
}
