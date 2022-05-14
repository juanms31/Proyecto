package com.company.Vistas;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewSeguimiento extends JFrame{

    public ViewSeguimiento(){
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
        setTitle("Seguimiento Laboral");
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    //endregion

    //region CRUD
    private boolean createSeguimiento(){


        return false;
    }

    private boolean readSeguimiento(){

        return false;
    }

    private boolean updateSeguimiento() {

        return false;
    }

    private boolean deleteSeguimiento(){


        return false;
    }

    //endregion

    //region Listeners
    private void listeners(){
        buttonAnadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createSeguimiento();
            }
        });

        buttonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readSeguimiento();
            }
        });

        buttonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSeguimiento();
            }
        });

        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSeguimiento();
            }
        });

    }

    //endregion

    //region Variables
    private JPanel panelPrincipal;
    private JTabbedPane panelPestanas;
    private JPanel PanelSeguimiento;
    private JTable TableSeguimiento;
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
