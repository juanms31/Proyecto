package com.company.Formularios;

import com.company.Entidades.*;
import com.company.Vistas.ViewAlbaran;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FormAlbaran extends JDialog {
    //region Constructores

    public FormAlbaran(ViewAlbaran viewAlbaran, ArrayList<Material> materiales, ArrayList<Actuacion> actuaciones, ArrayList<Proveedor> proveedores) {
        estado = 1;
        this.viewAlbaran = viewAlbaran;
        this.materiales = materiales;
        this.actuaciones = actuaciones;
        this.proveedores = proveedores;

        initWindow();
        initComps();
        initListeners();
        setVisible(true);

    }

    public FormAlbaran(ViewAlbaran viewAlbaran, Albaran albaran, ArrayList<Material> materiales, ArrayList<Actuacion> actuaciones, ArrayList<Proveedor> proveedores) {
        estado = 2;
        AlbaranSiendoModificado = albaran;
        this.viewAlbaran = viewAlbaran;
        this.materiales = materiales;
        this.actuaciones = actuaciones;
        this.proveedores = proveedores;

        initListeners();
        initWindow();
        initComps();
        setAlbaran(albaran);

        setVisible(true);
    }

    public FormAlbaran(ViewAlbaran viewAlbaran, Albaran albaran, ArrayList<Material> materiales,
                       ArrayList<Actuacion> actuaciones, ArrayList<Proveedor> proveedores, boolean editable) {
        this.viewAlbaran = viewAlbaran;
        this.materiales = materiales;
        this.actuaciones = actuaciones;
        this.proveedores = proveedores;
        AlbaranSiendoModificado = albaran;

        initWindow();
        initComps();
        setAlbaran(albaran);
        initListeners();
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
        setTitle("Albaranes");
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    public void centerFrame() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setSize( screen.width / 2, screen.height / 2);
        Dimension window = getSize();
        int width = (screen.width - window.width) / 2;
        int height = (screen.height - window.height) / 2;
        setLocation(width, height);
    }

    public void initComps() {

        //Rellenar Actuaciones
        comboBoxActuaciones.addItem("Selecciona Actuacion");
        for(Actuacion actuacion: actuaciones){
            comboBoxActuaciones.addItem(actuacion.getId() + " - " + actuacion.getNombre());
        }

        //Rellenar Proveedor
        comboBoxProveedores.addItem("Selecciona Proveedor");
        for(Proveedor proveedor: proveedores){
            comboBoxProveedores.addItem(proveedor.getCIF() + " - " + proveedor.getNombre_proveedor());
        }

        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter("##-##-####");
            formattedTextFieldFechaEntrada.setFormatterFactory(new DefaultFormatterFactory(formatter));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    //endregion

    //region Metodos privados

    private void loadNewAlbaran() {

        boolean conErrores = checkFields();

        if(conErrores){

        }else{

            if(viewAlbaran.getNewAlbaranFromFormulario(getAlbaran())){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(viewAlbaran.getMaterialesAlbaranFromFormulario(materialesCompradoProveedor)) {
                    dispose();
                    ShowMessage("Correcto", "Se ha creado el albaran correctamente");
                }
            }else{
                ShowErrorMessage("Error", "No se ha podido crear el albaran correctamente");
            }
            dispose();
        }
    }

    private void loadUpdateAlbaran() {

//        boolean conErrores = checkFields();
//
//        if(conErrores){
//
//        }else{
//            if (viewAlbaran.getUpdateAlbaranFromFormulario(getAlbaran())){
//                dispose();
//            }else {
//                ShowErrorMessage("Error", "No se ha podido crear el albaran correctamente");
//            }
//        }
    }

    //region Mensajes
    public void ShowMessage(String title, String msg) {
        JOptionPane.showMessageDialog(this,
                msg,
                title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void ShowWarningMessage(String title, String msg) {
        JOptionPane.showMessageDialog(this,
                msg,
                title,
                JOptionPane.WARNING_MESSAGE);
    }

    public void ShowErrorMessage(String title, String msg) {
        JOptionPane.showMessageDialog(this,
                msg,
                title,
                JOptionPane.ERROR_MESSAGE);
    }

    //endregion

    //region Metodos Privados
    public void setMaterialesFromFormulario(ArrayList<Material> materiales) {
        this.materialesOut = materiales;
    }

    //endregion

    //region SET Y GET MATERIAL
    private void setAlbaran(Albaran albaran) {
        textFieldCodigo.setText(albaran.getCod());
        comboBoxActuaciones.setSelectedItem(albaran.getActuacion().getId() + " - " + albaran.getActuacion().getNombre());
        comboBoxProveedores.setSelectedItem(albaran.getProveedor().getCIF() + " - " + albaran.getProveedor().getNombre_proveedor());
        textAreaConcepto.setText(albaran.getConcepto());
        //Procesamos Fecha
        SimpleDateFormat OldDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String UTILDate = OldDateFormat.format(albaran.getFechaEntradaAlbaran());

        formattedTextFieldFechaEntrada.setText(UTILDate.toString());

    }

    private boolean checkFields() {
        if (textFieldCodigo.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Codigo no puede estar vacio");
            return true;
        }
        if(comboBoxActuaciones.getSelectedIndex() == 0){
            ShowErrorMessage("Error", "El campo Actuacion no puede estar vacio");
            return true;
        }
        if(comboBoxProveedores.getSelectedIndex() == 0){
            ShowErrorMessage("Error", "El campo Proveedor no puede estar vacio");
            return true;
        }
        if (formattedTextFieldFechaEntrada.getText().equals("  -  -    ")) {
            ShowErrorMessage("Error", "Campo Fecha Entrada no puede estar vacio");
            return true;
        }

        return false;
    }

    private Albaran getAlbaran() {

        Albaran albaran = new Albaran();
        int cont = 0;

        if(materialesOut.isEmpty()) {



            MaterialCompradoProveedor materialCompradoProveedor = new MaterialCompradoProveedor();

            albaran.setCod(textFieldCodigo.getText());
            System.out.println("ID ALBARAN FROM FORMULARIO1: " + textFieldCodigo.getText());
            albaran.setProveedor(proveedores.get(comboBoxProveedores.getSelectedIndex() - 1));
            albaran.setActuacion(actuaciones.get(comboBoxActuaciones.getSelectedIndex() - 1));
            albaran.setConcepto(textAreaConcepto.getText());
            //Procesamos Fecha
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            try {
                java.util.Date UTILDate = dateFormat.parse(formattedTextFieldFechaEntrada.getText());
                java.sql.Date SQLDate = new Date(UTILDate.getTime());
                albaran.setFechaEntradaAlbaran(SQLDate);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            materialesCompradoProveedor.add(materialCompradoProveedor);
        }else {

            for (Material material : materialesOut) {

                MaterialCompradoProveedor materialCompradoProveedor = new MaterialCompradoProveedor();

                albaran.setCod(textFieldCodigo.getText());
                albaran.setProveedor(proveedores.get(comboBoxProveedores.getSelectedIndex() - 1));
                albaran.setActuacion(actuaciones.get(comboBoxActuaciones.getSelectedIndex() - 1));
                albaran.setConcepto(textAreaConcepto.getText());

                //Procesamos Fecha
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                try {
                    java.util.Date UTILDate = dateFormat.parse(formattedTextFieldFechaEntrada.getText());
                    java.sql.Date SQLDate = new Date(UTILDate.getTime());
                    albaran.setFechaEntradaAlbaran(SQLDate);

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                materialCompradoProveedor.setMaterial(materialesOut.get(cont));
                materialCompradoProveedor.setProveedor(proveedores.get(comboBoxProveedores.getSelectedIndex() - 1));
                materialCompradoProveedor.setActuacion(actuaciones.get(comboBoxActuaciones.getSelectedIndex() - 1));
                materialCompradoProveedor.setAlbaran(albaran);

                materialesCompradoProveedor.add(materialCompradoProveedor);
                cont++;
            }
        }
        return albaran;
    }

    //endregion Albaran

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
                        loadNewAlbaran();
                    }

                    case 2 -> {
                        loadUpdateAlbaran();
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

        buttonAnadirMateriales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textFieldCodigo.getText().isEmpty()){

                    ShowErrorMessage("Error", "El campo \"Codigo Albaran\" tiene que estar relleno.");

                }else {
                    FormMaterialesCompradoProveedorAlbaran formMaterialesCompradoProveedorAlbaran = new FormMaterialesCompradoProveedorAlbaran(FormAlbaran.this, textFieldCodigo.getText(), materiales);
                }

            }
        });
    }

    private void keyListeners() {

        formattedTextFieldFechaEntrada.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != '/')) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });
    }

    //endregion


    //region Variables

    private ViewAlbaran viewAlbaran;
    private JTextField textFieldCodigo;
    private JButton aceptarButton;
    private JButton cancelarButton;
    private JFormattedTextField formattedTextFieldFechaEntrada;
    private JPanel panelPrincipal;
    private JButton buttonAnadirMateriales;
    private JComboBox comboBoxProveedores;
    private JComboBox comboBoxActuaciones;
    private JTextArea textAreaConcepto;
    private JLabel labelTitulo;

    private ArrayList<Actuacion> actuaciones;
    private ArrayList<Proveedor> proveedores;
    private ArrayList<Material> materiales;
    private ArrayList<Material> materialesOut =  new ArrayList<>();
    private ArrayList<MaterialCompradoProveedor> materialesCompradoProveedor = new ArrayList<>();



    int estado = 0;
    private Albaran AlbaranSiendoModificado;

    //endregion
}
