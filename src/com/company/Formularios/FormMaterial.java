package com.company.Formularios;

import com.company.Entidades.*;
import com.company.Vistas.ViewMaterial;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class FormMaterial extends JDialog {

    //region Constructores

    public FormMaterial(ViewMaterial viewMaterial, ArrayList<Proveedor> proveedores,
                        ArrayList<GrupoMaterial> grupoMateriales, ArrayList<EspecificacionMaterial> especificacionMateriales,
                        ArrayList<UnidadMaterial> unidadMateriales, ArrayList<CalidadMaterial> calidadMateriales) {
        estado = 1;
        this.viewMaterial = viewMaterial;
        this.proveedores = proveedores;
        this.grupoMateriales = grupoMateriales;
        this.especificacionMateriales = especificacionMateriales;
        this.unidadMateriales = unidadMateriales;
        this.calidadMateriales = calidadMateriales;

        initWindow();
        initComps();
        initListeners();
        setVisible(true);

    }

    public FormMaterial(ViewMaterial viewMaterial, Material material, ArrayList<Proveedor> proveedores,
                        ArrayList<GrupoMaterial> grupoMateriales, ArrayList<EspecificacionMaterial> especificacionMateriales,
                        ArrayList<UnidadMaterial> unidadMateriales, ArrayList<CalidadMaterial> calidadMateriales) {
        estado = 2;
        MaterialSiendoModificado = material;
        this.viewMaterial = viewMaterial;
        this.proveedores = proveedores;
        this.grupoMateriales = grupoMateriales;
        this.especificacionMateriales = especificacionMateriales;
        this.unidadMateriales = unidadMateriales;
        this.calidadMateriales = calidadMateriales;
        initListeners();
        initWindow();
        initComps();
        setMaterial(material);
        setVisible(true);
    }

    public FormMaterial(ViewMaterial viewMaterial, Material material, ArrayList<Proveedor> proveedores,
                        ArrayList<GrupoMaterial> grupoMateriales, ArrayList<EspecificacionMaterial> especificacionMateriales,
                        ArrayList<UnidadMaterial> unidadMateriales, ArrayList<CalidadMaterial> calidadMateriales, boolean editable) {
        this.viewMaterial = viewMaterial;
        initWindow();
        initComps();
        setMaterial(material);
        initListeners();
        //TODO ver como tratamos editable
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
        centerFrame();
        setModal(true);
        setResizable(false);
        setMinimumSize(new Dimension(500, 500));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Materiales");
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    public void centerFrame() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screen.width / 2, screen.height - 100);
        Dimension window = getSize();
        int width = (screen.width - window.width) / 2;
        int height = (screen.height - window.height) / 2;
        setLocation(width, height);
    }

    public void initComps() {

        //Rellenar grupo
        comboBoxGrupo.addItem("Selecciona grupo ");
        for(GrupoMaterial grupo: grupoMateriales){
            comboBoxGrupo.addItem(grupo.getSiglasGrupo());
        }

        //Rellenar especificacion
        comboBoxEspecificacion.addItem("Selecciona Especificacion ");
        for(EspecificacionMaterial especificacionMaterial: especificacionMateriales){
            comboBoxEspecificacion.addItem(especificacionMaterial.getNombreEspecificacion());
        }

        //Rellenar UD
        comboBoxUnidad.addItem("Selecciona Unidad ");
        for(UnidadMaterial unidadMaterial: unidadMateriales){
            comboBoxUnidad.addItem(unidadMaterial.getSiglasUnidad());
        }

        //Rellenar Calidad
        comboBoxCalidad.addItem("Selecciona Calidad ");
        for(CalidadMaterial calidadMaterial: calidadMateriales){
            comboBoxCalidad.addItem(calidadMaterial.getSiglasCalidad());
        }

        //Rellenar Proveedor1
        for(Proveedor proveedor: proveedores){
            comboBoxProveedor.addItem(proveedor.getNombre_proveedor());
        }

        //Rellenar Proveedor2
        for(Proveedor proveedor: proveedores){
            comboBoxProveedor2.addItem(proveedor.getNombre_proveedor());
        }

        //Rellenar Proveedor3
        for(Proveedor proveedor: proveedores){
            comboBoxProveedor3.addItem(proveedor.getNombre_proveedor());
        }
    }
    //endregion

    //region Metodos privados

    private void loadNewMaterial() {

        boolean conErrores = checkFields();

        if (conErrores) {

        } else {

            Material material = getMaterial();
            if (viewMaterial.getNewMaterialFromFormulario(material)) {
                dispose();
            } else {
                ShowErrorMessage("Error", "No se ha podido crear el material correctamente");
            }
            dispose();
        }
    }

    private void loadUpdateMaterial() {

        boolean conErrores = checkFields();

        if (conErrores) {

        } else {
            Material material = getMaterial();
            if (viewMaterial.getUpdateMaterialFromFormulario(material)) {
                dispose();
            } else {
                ShowErrorMessage("Error", "No se ha podido crear el material correctamente");
            }
        }
    }

    public void ShowErrorMessage(String title, String msg) {
        JOptionPane.showMessageDialog(this,
                msg,
                title,
                JOptionPane.ERROR_MESSAGE);
    }

    //endregion

    //region SET Y GET MATERIAL
    private void setMaterial(Material material) {

        MaterialSiendoModificado.setId(material.getId());
        MaterialSiendoModificado.setCodigo(material.getCodigo());
        comboBoxGrupo.setSelectedItem(material.getGrupo());
        textFieldDescripcion.setText(material.getDescripcion());
        comboBoxEspecificacion.setSelectedItem(material.getEspecificacion());
        comboBoxUnidad.setSelectedItem(material.getUnidad());
        textFieldEspesor.setText(String.valueOf(material.getEspesor()));
        comboBoxCalidad.setSelectedItem(material.getCalidad());
        comboBoxProveedor.setSelectedItem(material.getProveedor1());
        spinnerPrecio1.setValue(material.getPrecio1());
        comboBoxProveedor2.setSelectedItem(material.getProveedor2());
        spinnerPrecio2.setValue(material.getPrecio2());
        comboBoxProveedor3.setSelectedItem(material.getProveedor3());
        spinnerPrecio3.setValue(material.getPrecio3());
    }

    private boolean checkFields() {
        if (textFieldDescripcion.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Descripcion no puede estar vacio");
            return true;
        }
        if (textFieldEspesor.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Espesor no puede estar vacio");
            return true;
        }
        return false;
    }

    private Material getMaterial() {
        Material material = new Material();

        boolean conErrores = checkFields();

        if (estado == 2) {
            material.setId(MaterialSiendoModificado.getId());
            material.setCodigo(MaterialSiendoModificado.getCodigo());
            material.setGrupo((String) comboBoxGrupo.getSelectedItem());
            material.setDescripcion(textFieldDescripcion.getText());
            material.setEspecificacion((String) comboBoxEspecificacion.getSelectedItem());
            material.setUnidad((String) comboBoxUnidad.getSelectedItem());
            material.setEspesor(Double.parseDouble(textFieldEspesor.getText()));
            material.setCalidad((String) comboBoxCalidad.getSelectedItem());
            material.setProveedor1((String) comboBoxProveedor.getSelectedItem());
            material.setPrecio1(Double.valueOf(String.valueOf(spinnerPrecio1.getValue())));
            material.setProveedor2((String) comboBoxProveedor2.getSelectedItem());
            material.setPrecio2(Double.valueOf(String.valueOf(spinnerPrecio2.getValue())));
            material.setProveedor3((String) comboBoxProveedor3.getSelectedItem());
            material.setPrecio3(Double.valueOf(String.valueOf(spinnerPrecio3.getValue())));


        } else {
//            material.setCodigo(MaterialSiendoModificado.getCodigo());
            material.setGrupo((String) comboBoxGrupo.getSelectedItem());
            material.setDescripcion(textFieldDescripcion.getText());
            material.setEspecificacion((String) comboBoxEspecificacion.getSelectedItem());
            material.setUnidad((String) comboBoxUnidad.getSelectedItem());
            material.setEspesor(Double.parseDouble(textFieldEspesor.getText()));
            material.setCalidad((String) comboBoxCalidad.getSelectedItem());
            material.setProveedor1((String) comboBoxProveedor.getSelectedItem());
            material.setPrecio1(Double.valueOf(String.valueOf(spinnerPrecio1.getValue())));
            material.setProveedor2((String) comboBoxProveedor2.getSelectedItem());
            material.setPrecio2(Double.valueOf(String.valueOf(spinnerPrecio2.getValue())));
            material.setProveedor3((String) comboBoxProveedor3.getSelectedItem());
            material.setPrecio3(Double.valueOf(String.valueOf(spinnerPrecio3.getValue())));
        }

        return material;
    }

    //endregion MATERIAL

    //region Listeners

    private void initListeners() {
        actionListeners();
        keyListeners();
    }

    private void actionListeners() {
        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch (estado) {
                    case 0 -> {
                    }
                    case 1 -> {
                        loadNewMaterial();
                    }

                    case 2 -> {
                        loadUpdateMaterial();
                    }
                }

            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void keyListeners() {
        textFieldEspesor.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != '.')) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });


        spinnerPrecio1.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != ',')) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        spinnerPrecio2.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != ',')) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        spinnerPrecio3.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != ',')) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });
    }

    //endregion

    //region Variables
    private JButton aceptarButton;

    private Material MaterialSiendoModificado;
    private JComboBox comboBoxProveedor2;
    private JComboBox comboBoxProveedor3;
    private JLabel labelTitulo;
    private JButton cancelarButton;
    private JTextField textFieldDescripcion;
    private JComboBox comboBoxGrupo;
    private JComboBox comboBoxEspecificacion;
    private JComboBox comboBoxUnidad;
    private JComboBox comboBoxCalidad;
    private JComboBox comboBoxProveedor;
    private JSpinner spinnerPrecio1;
    private JSpinner spinnerPrecio2;
    private JSpinner spinnerPrecio3;
    private JTextField textFieldEspesor;
    private JPanel panelPrincipal;

    private ViewMaterial viewMaterial;
    int estado = 0;

    ArrayList<Proveedor> proveedores;
    ArrayList<GrupoMaterial> grupoMateriales;
    ArrayList<EspecificacionMaterial> especificacionMateriales;
    ArrayList<UnidadMaterial> unidadMateriales;
    ArrayList<CalidadMaterial> calidadMateriales;


    //endregion
}
