package com.company.Vistas;

import javax.swing.*;

public class ViewActuacion extends JFrame{
    private JPanel panelPrincipal;
    private JTabbedPane panelPestanas;
    private JPanel PanelObra;
    private JTable TableObra;
    private JPanel buscador;
    private JTextField filtro;
    private JButton buttonBuscar;
    private JButton buttonAnadir;
    private JButton buttonEliminar;
    private JButton buttonVer;
    private JButton buttonEditar;
    private JButton buttonRecargar;
    private JPanel panelBotones;
    private JButton buttonVolver;

    public ViewActuacion(){
        initView();
    }

    private void initView(){
        add(panelPrincipal);
        setVisible(true);
        setSize(800, 600);
    }
}
