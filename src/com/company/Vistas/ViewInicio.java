package com.company.Vistas;

import com.company.Recursos.RoundedBorder;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewInicio extends JFrame {
    private JPanel panelPrincipal;
    private JButton buttonVolver;
    private JPanel buscador;
    private JButton buttonBaseDatos;
    private JButton buttonCalendario;
    private JButton buttonChat;
    private JPanel panelFondo;
    private JPanel panelModulos;
    private JButton button1;
    private JButton button2;
    private JButton button3;

    int numImagen = 1;

    //region Constructor
    public ViewInicio() {


        initWindow();
        initListeners();
        initComps();
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
        setTitle("Inicio");
        setIconImage(Toolkit.getDefaultToolkit().getImage("src/com/company/Images/Logo/logoEnano.jpg"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 600));
        setImageBackground("fondo" + numImagen + ".jpg");
        add(panelPrincipal);
        repaint();
        revalidate();

    }

    private void initComps() {
        buttonBaseDatos.setBorder(new RoundedBorder(10));
        buttonChat.setBorder(new RoundedBorder(10));
        buttonCalendario.setBorder(new RoundedBorder(10));
    }

    private void setImageBackground(String image) {
        JPanel fondo = new JPanel() {
            public void paintComponent(Graphics g) {
                Image img = Toolkit.getDefaultToolkit().getImage("src/com/company/Images/Fondos/" + image);
                g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        fondo.setBorder(new EmptyBorder(5, 5, 5, 5));
        fondo.setLayout(new BorderLayout(0, 0));
        setContentPane(fondo);
        add(panelPrincipal);
        repaint();
        revalidate();
    }

    //endregion

    //region Listeners
    private void initListeners() {
        buttonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                setImageBackground("fondo"+numImagen+".jpg");
//                numImagen++;

                dispose();

                ViewLogin viewLogin = new ViewLogin();
                viewLogin.setVisible(true);
            }
        });

        buttonBaseDatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewBaseDatos viewBaseDatos = new ViewBaseDatos();
                viewBaseDatos.setVisible(true);
            }
        });
    }
    //endregion
}
