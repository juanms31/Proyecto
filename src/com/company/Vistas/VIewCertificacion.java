package com.company.Vistas;

import javax.swing.*;

public class VIewCertificacion extends JFrame{
    private JPanel panelPrincipal;
    private JTabbedPane panelPestanas;
    private JPanel PanelCertificacion;
    private JTable TableCertificacion;
    private JPanel buscador;
    private JTextField filtro;
    private JButton buscar;
    private JPanel panelBotones;
    private JButton buttonAnadir;
    private JButton buttonEliminar;
    private JButton buttonVer;
    private JButton buttonEditar;
    private JButton buttonRecargar;

    public VIewCertificacion(){
        initView();
    }

    private void initView() {
        add(panelPrincipal);
        setVisible(true);
        setSize(800, 600);
    }
}
