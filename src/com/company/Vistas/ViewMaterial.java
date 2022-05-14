package com.company.Vistas;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewMaterial extends JFrame{

    //region Constructores
    public ViewMaterial(){
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
        setTitle("Materiales");
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    //endregion

    //region CRUD
    private boolean createMaterial(){


        return false;
    }

    private boolean readMaterial(){

        return false;
    }

    private boolean updateMaterial() {

        return false;
    }

    private boolean deleteMaterial(){


        return false;
    }

    //endregion

    //region Listeners
    private void listeners(){
        buttonAnadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createMaterial();
            }
        });

        buttonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readMaterial();
            }
        });

        buttonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMaterial();
            }
        });

        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMaterial();
            }
        });

    }

    //endregion

    //region Variables

    private JPanel panelPrincipal;
    private JTabbedPane panelPestanas;
    private JPanel PanelMaterial;
    private JTable TableMaterial;
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
