package com.company.Formularios;

import com.company.Entidades.Material;
import com.company.Vistas.ViewMaterial;

import javax.swing.*;

public class formMaterial extends JFrame{

    public formMaterial(ViewMaterial viewMaterial) {
        this.viewMaterial = viewMaterial;
    }

    //region CRUD

    public Material createMaterial(){
        return new Material();
    }

    public boolean readMaterial(Material material){

        return false;
    }

    public Material updateMaterial() {

        return new Material();
    }

    public boolean deleteMaterial(){


        return false;
    }

    //endregion

    //region Metodos publicos

    public void openForm() {
        setVisible(true);
    }

    //endregion


    //region Variables
    private JButton aceptarButton;
    private JButton cancelarButton;
    private JTextField textFieldDescripcion;
    private JComboBox comboBoxGrupo;
    private JComboBox comboBoxEspecificacion;
    private JComboBox comboBoxUnidad;
    private JSpinner spinnerPrecio1;
    private JSpinner spinnerPrecio2;
    private JSpinner spinnerPrecio3;
    private JTextField textFieldEspesor;
    private JComboBox comboBoxCalidad;
    private JComboBox comboBoxProveedor;
    private JComboBox comboBoxProveedor2;
    private JComboBox comboBoxProveedor3;
    private JLabel labelTitulo;
    private final ViewMaterial viewMaterial;


    //endregion
}
