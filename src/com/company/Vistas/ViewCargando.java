package com.company.Vistas;

import javax.swing.*;
import java.awt.*;

public class ViewCargando extends JFrame{
    private JProgressBar progressBar1;
    private JPanel panelPrincipal;

    public ViewCargando() throws HeadlessException {
        add(panelPrincipal);
        setSize(300,300);
        Principal principal = new Principal();
        principal.setVisible(true);
    }
}
