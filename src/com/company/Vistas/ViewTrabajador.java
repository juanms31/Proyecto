package com.company.Vistas;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewTrabajador extends JFrame{


    public ViewTrabajador(){
        initWindow();

        setVisible(true);
    }

    //endregion

    //region Metodos Vista
    private void initWindow() {
        add(panelPrincipal);
        setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setExtendedState(MAXIMIZED_BOTH);
        setResizable(true);
        setMinimumSize(new Dimension(750,750));
        setLocationRelativeTo(null);
        setTitle("Trabajador");
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    //endregion

    //region CRUD
    private boolean createTrabajador(){


        return false;
    }

    private boolean readTrabajador(){


        return false;
    }

    private boolean updateTrabajador() {

        return false;
    }

    private boolean deleteTrabajador(){


        return false;
    }

    //endregion

    //region Listeners
    private void listeners(){
        buttonAnadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTrabajador();
            }
        });

        buttonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readTrabajador();
            }
        });

        buttonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTrabajador();
            }
        });

        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTrabajador();
            }
        });

    }

    //endregion


    //region Variables
    private JPanel panelPrincipal;
    private JTabbedPane panelPestanas;
    private JPanel PanelTrabajador;
    private JTable TableTrabajador;
    private JPanel buscador;
    private JTextField filtro;
    private JButton buscar;
    private JPanel panelBotones;
    private JButton buttonAnadir;
    private JButton buttonEliminar;
    private JButton buttonVer;
    private JButton buttonEditar;
    private JButton buttonRecargar;

    //endregion
}
