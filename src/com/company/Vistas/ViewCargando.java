package com.company.Vistas;

import com.company.Entidades.Usuario;
import com.formdev.flatlaf.FlatDarculaLaf;
import javax.swing.*;
import java.awt.event.*;
import java.util.Locale;

public class ViewCargando extends JFrame {
    public static String DNI;
    private JPanel contentPane;
    private JProgressBar progressBar;
    private JLabel porcentaje;
    private JLabel JLabelBienvenido;
    private JFrame parent;

    public ViewCargando(JFrame parent, Usuario usuario) {
        this.parent = parent;

        setSize(570, 400);
        setLocationRelativeTo(parent);
        setResizable(false);
        setAlwaysOnTop(true);
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setTitle("Validando DNI");
        // FIXME: 02/06/2022 CAMBIAR ICONO
//        setIconImage(new ImageIcon("src/images/verificacion25.png").getImage());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        add(contentPane);
        setVisible(true);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    int numAle = 0;
                    if (i > 0 && i < 10) {
                        numAle = (int) (Math.random() * 500) + 1;
                        progressBar.setString("Validando Email...");

                    } else if (i > 90 && i < 99) {
                        progressBar.setString("Cargando...");

                    } else if (i > 30 && i < 40) {
                        numAle = (int) (Math.random() * 250) + 1;
                        progressBar.setString("Validando Contraseña.");

                    } else if (i > 40 && i < 50) {
                        numAle = (int) (Math.random() * 250) + 1;
                        progressBar.setString("Cargando Vistas...");

                    } else if (i > 50 && i < 60) {
                        numAle = (int) (Math.random() * 250) + 1;
                        progressBar.setString("Cargando Iconos..");

                    } else if (i > 60 && i < 70) {
                        numAle = (int) (Math.random() * 250) + 1;
                        progressBar.setString("Cargando Modulos..");
                    } else if (i > 70 && i < 90) {
                        numAle = (int) (Math.random() * 250) + 1;
                        progressBar.setString("Cargando..");
                        JLabelBienvenido.setText("Bienvenido " + usuario.getNombre());
                    }else if(i == 100){
                        ViewMain viewMain = new ViewMain();
                        viewMain.setVisible(true);
                    }

                    progressBar.setValue(i);
                    porcentaje.setText(i + "%");
                    try {
                        Thread.sleep(numAle);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread th = new Thread(runnable);
        th.start();
    }
}
