package com.company.Formularios;

import com.company.Entidades.Material;
import com.company.Vistas.ViewMaterial;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class formMaterial extends JFrame{

    int estado = 0;

    //region Constructores

    public formMaterial(ViewMaterial viewMaterial) {
        estado = 1;
    }

    public formMaterial(ViewMaterial viewMaterial, Material material){
        estado = 2;

        setMaterial(material);
    }

    //endregion

    //region CRUD
    public Material createMaterial(){

        return getMaterial();
    }

    public Material updateMaterial() {
        return getMaterial();
    }

    //endregion

    //region SET Y GET MATERIAL
    private void setMaterial(Material material) {
        comboBoxGrupo.setSelectedItem(material.getGrupo());
        textFieldDescripcion.setText(material.getDescripcion());
        comboBoxEspecificacion.setSelectedItem(material.getEspecificacion());
        comboBoxUnidad.setSelectedItem(material.getIdUnidad());
        textFieldEspesor.setText(String.valueOf(material.getEspesor()));
        comboBoxCalidad.setSelectedItem(material.getCalidad());
        comboBoxProveedor.setSelectedItem(material.getProveedor1());
        spinnerPrecio1.setValue(material.getPrecio1());
        comboBoxProveedor2.setSelectedItem(material.getProveedor2());
        spinnerPrecio2.setValue(material.getPrecio2());
        comboBoxProveedor3.setSelectedItem(material.getProveedor3());
        spinnerPrecio3.setValue(material.getPrecio3());
    }

    private Material getMaterial(){
        Material material = new Material();

        material.setGrupo((String) comboBoxGrupo.getSelectedItem());
        material.setDescripcion(textFieldDescripcion.getText());
        material.setEspecificacion((String) comboBoxEspecificacion.getSelectedItem());
        material.setUnidad((String) comboBoxUnidad.getSelectedItem());
        material.setEspesor(Double.parseDouble(textFieldEspesor.getText()));
        material.setCalidad((String) comboBoxCalidad.getSelectedItem());
        material.setProveedor1((String) comboBoxProveedor.getSelectedItem());
        material.setPrecio1((Double) spinnerPrecio1.getValue());
        material.setProveedor2((String) comboBoxProveedor2.getSelectedItem());
        material.setPrecio2((Double) spinnerPrecio2.getValue());
        material.setProveedor3((String) comboBoxProveedor3.getSelectedItem());
        material.setPrecio3((Double) spinnerPrecio3.getValue());

        return material;
    }

    //endregion MATERIAL

    //region Listeners


    private void actionListeners(){
        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch (estado){
                    case 0 -> {

                    }
                    case 1 -> {

                    }

                    case 2 -> {

                    }
                }

            }
        });
    }

    private void keyListeners(){
        textFieldEspesor.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent e)
            {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if(((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != ','))
                {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        spinnerPrecio1.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent e)
            {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if(((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != ','))
                {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        spinnerPrecio2.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent e)
            {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if(((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != ','))
                {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        spinnerPrecio3.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent e)
            {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if(((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != ','))
                {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });
    }

    //endregion

    //region Variables
    private JButton aceptarButton;

    private JComboBox comboBoxProveedor2;
    private JComboBox comboBoxProveedor3;
    private JLabel labelTitulo;
    private JButton cancelarButton;
    private JLabel textFieldDescripcion;
    private JComboBox comboBoxGrupo;
    private JComboBox comboBoxEspecificacion;
    private JComboBox comboBoxUnidad;
    private JComboBox comboBoxCalidad;
    private JComboBox comboBoxProveedor;
    private JSpinner spinnerPrecio1;
    private JSpinner spinnerPrecio2;
    private JSpinner spinnerPrecio3;
    private JTextField textFieldEspesor;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


    //endregion
}
