package com.company.Vistas;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ViewInicio extends JFrame {
    private JPanel panelPrincipal;
    private JButton button1;
    private JButton button2;
    private JButton button3;

    //region Constructor
    public ViewInicio(){


        initWindow();
        initListeners();
        setVisible(true);
    }

    //endregion

    //region Metodos Vista
    private void initWindow() {
        setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setExtendedState(MAXIMIZED_BOTH);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(750, 750));
        setLocationRelativeTo(null);
        setTitle("Inicio");
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
        setImageBackground();
        add(panelPrincipal);
    }

    private void setImageBackground() {
        JPanel fondo = new JPanel() {
            public void paintComponent(Graphics g) {
                Image img = Toolkit.getDefaultToolkit().getImage("src/com/company/Images/Logo/background.jpg");
                g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        fondo.setBorder(new EmptyBorder(5, 5, 5, 5));
        fondo.setLayout(new BorderLayout(0, 0));
        panelPrincipal = fondo;

    }

    //endregion

    //region Listeners
    private void initListeners() {

    }


    //endregion



}
