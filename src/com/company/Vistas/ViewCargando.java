package com.company.Vistas;

import javax.swing.*;
import java.awt.*;

public class ViewCargando extends JFrame{
    private JProgressBar progressBarCargando;
    private JPanel panelPrincipal;
    private JLabel JLabelMensajeError;

    public ViewCargando() throws HeadlessException {
        add(panelPrincipal);
        setSize(300,300);
        ViewPrincipal viewPrincipal = new ViewPrincipal();
        viewPrincipal.setVisible(true);
    }
}
