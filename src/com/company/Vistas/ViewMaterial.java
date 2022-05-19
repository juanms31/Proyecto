package com.company.Vistas;

import com.company.Controlador.ControladorMaterial;;
import com.company.Entidades.Material;
import com.company.Formularios.formMaterial;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewMaterial extends JFrame{

    private ControladorMaterial controladorMaterial;
    private formMaterial formMaterial;
    private ArrayList<Material> materiales;

    //region Constructores

    public ViewMaterial(ControladorMaterial controladorMaterial, ArrayList<Material> materiales) {
        this.controladorMaterial = controladorMaterial;
        this.materiales = materiales;
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

    //region Metodos Publicos

    public void getMaterialFromFormulario(Material material){
        controladorMaterial.sendMaterialFromModel(material);
    }

    public void ShowMessage(String s) {
        JOptionPane.showInputDialog(s);
    }

    public void ErroMessage(String s) {
        JOptionPane.showInputDialog("Error" + s);
    }

    //endregion

    //region CRUD
    private void createMaterial(){
       formMaterial = new formMaterial(this);
       formMaterial.openForm();
    }

    private void readMaterial(){
        String cod = "cod"; //TODO coger el codigo desde la tabla
        Material material = controladorMaterial.readMaterial(cod);
        formMaterial.readMaterial(material);
    }

    private boolean updateMaterial() {
        Material material = new Material(); //TODO coger material desde donde tenga que venir
        boolean result = controladorMaterial.updateMaterial(material);
        return result;
    }

    private boolean deleteMaterial(){
        int cod = 1; //TODO coger el codigo desde la tabla
        boolean result = controladorMaterial.deleteMaterial(1);
        return result;
    }

    //endregion

    //region Metodos privados

    public void updateTableMaterial(Material material) {
        //TODO actualizar tabla
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
