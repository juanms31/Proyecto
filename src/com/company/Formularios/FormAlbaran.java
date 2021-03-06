package com.company.Formularios;

import com.company.Entidades.*;
import com.company.Recursos.CheckDate;
import com.company.Recursos.RoundedBorder;
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
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public FormAlbaran(ViewAlbaran viewAlbaran, Albaran albaran,
                       ArrayList<Material> materiales,
                       ArrayList<MaterialCompradoProveedor> materialesCompradoProveedor,
                       ArrayList<Actuacion> actuaciones,
                       ArrayList<Proveedor> proveedores) {
        estado = 2;
        AlbaranSiendoModificado = albaran;
        this.viewAlbaran = viewAlbaran;
        this.materiales = materiales;
        this.materialesCompradoProveedor = materialesCompradoProveedor;

        this.actuaciones = actuaciones;
        this.proveedores = proveedores;

        initListeners();
        initWindow();
        initComps();
        setAlbaran(albaran);
        setMaterialesOut(materialesCompradoProveedor);

        setVisible(true);
    }

    public FormAlbaran(ViewAlbaran viewAlbaran, Albaran albaran, ArrayList<Material> materiales,
                       ArrayList<Actuacion> actuaciones, ArrayList<Proveedor> proveedores, boolean editable) {
        estado = 3;
        this.viewAlbaran = viewAlbaran;
        this.materiales = materiales;
        this.actuaciones = actuaciones;
        this.proveedores = proveedores;

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
        setResizable(true);
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

        aceptarButton.setBorder(new RoundedBorder(10));
        cancelarButton.setBorder(new RoundedBorder(10));

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

            DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            formattedTextFieldFechaEntrada.setText(dft.format(LocalDateTime.now()));

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

        boolean conErrores = checkFields();

        if(conErrores){

        }else{

            if(viewAlbaran.getUpdateAlbaranFromFormulario(getAlbaran())){

                if(viewAlbaran.getUpdateMaterialAlbaranFromFormulario(materialesCompradoProveedor)) {

                    dispose();
                    ShowMessage("Correcto", "Se ha actualizado el albaran correctamente");
                }
            }else{
                ShowErrorMessage("Error", "No se ha podido actualizar el albaran correctamente");
            }
            dispose();
        }
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

    public void setMaterialesCompradoProveedorFromFormulario(ArrayList<MaterialCompradoProveedor> materialesCompradoProveedor) {
        this.materialesCompradoProveedor = materialesCompradoProveedor;
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
        System.out.println("Fecha entrada albaran: " + albaran.getFechaEntradaAlbaran());
        String UTILDate = OldDateFormat.format(albaran.getFechaEntradaAlbaran());

        formattedTextFieldFechaEntrada.setText(UTILDate.toString());

    }

    private void setMaterialesOut(ArrayList<MaterialCompradoProveedor> materialesCompradoProveedor) {
        for(MaterialCompradoProveedor materialCompradoProveedor : materialesCompradoProveedor){
            materialesOut.add(materialCompradoProveedor.getMaterial());
        }
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

        validarFechas();

        if(materialesCompradoProveedor.isEmpty()){
            ShowErrorMessage("Error", "Debes a??adir materiales al albaran.");
            return true;
        }

        return false;
    }

    private boolean validarFechas() {
        CheckDate checkDate = new CheckDate();

        if (formattedTextFieldFechaEntrada.getText().equals("  -  -    ")) {
            ShowErrorMessage("Error", "Campo Fecha Entrada no puede estar vacio");
            return true;
        } else if (!checkDate.isValidDate(formattedTextFieldFechaEntrada.getText())) {
            ShowErrorMessage("Error", "La fecha de entrada no es valida");
            return true;
        }

        return false;
    }

    private Albaran getAlbaran() {

        Albaran albaran = new Albaran();

        if(estado == 1) {

            int cont = 0;

            if (materialesOut.isEmpty()) {

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

                materialesCompradoProveedor.get(cont).setMaterial(materialesOut.get(cont));
                materialesCompradoProveedor.get(cont).setProveedor(proveedores.get(comboBoxProveedores.getSelectedIndex() - 1));
                materialesCompradoProveedor.get(cont).setActuacion(actuaciones.get(comboBoxActuaciones.getSelectedIndex() - 1));
                materialesCompradoProveedor.get(cont).setAlbaran(albaran);

            } else {

                for (Material material : materialesOut) {

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

                    materialesCompradoProveedor.get(cont).setMaterial(materialesOut.get(cont));
                    materialesCompradoProveedor.get(cont).setProveedor(proveedores.get(comboBoxProveedores.getSelectedIndex() - 1));
                    materialesCompradoProveedor.get(cont).setActuacion(actuaciones.get(comboBoxActuaciones.getSelectedIndex() - 1));
                    materialesCompradoProveedor.get(cont).setAlbaran(albaran);

                    cont++;
                }
                albaran.setMateriales(materialesOut);

            }
        }else if(estado == 2){

            int cont = 0;

            if (materialesOut.isEmpty()) {

                albaran.setId(AlbaranSiendoModificado.getId());
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

                materialesCompradoProveedor.get(cont).setMaterial(materialesOut.get(cont));
                materialesCompradoProveedor.get(cont).setProveedor(proveedores.get(comboBoxProveedores.getSelectedIndex() - 1));
                materialesCompradoProveedor.get(cont).setActuacion(actuaciones.get(comboBoxActuaciones.getSelectedIndex() - 1));
                materialesCompradoProveedor.get(cont).setAlbaran(albaran);

            } else {

                for (Material material : materialesOut) {

                    albaran.setId(AlbaranSiendoModificado.getId());
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

                    materialesCompradoProveedor.get(cont).setMaterial(materialesOut.get(cont));
                    materialesCompradoProveedor.get(cont).setProveedor(proveedores.get(comboBoxProveedores.getSelectedIndex() - 1));
                    materialesCompradoProveedor.get(cont).setActuacion(actuaciones.get(comboBoxActuaciones.getSelectedIndex() - 1));
                    materialesCompradoProveedor.get(cont).setAlbaran(albaran);

                    cont++;
                }
                albaran.setMateriales(materialesOut);

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
                if(estado == 1) {
                    if (textFieldCodigo.getText().isEmpty()) {

                        ShowErrorMessage("Error", "El campo \"Codigo Albaran\" tiene que estar relleno.");

                    } else {
                        FormMaterialesCompradoProveedorAlbaran formMaterialesCompradoProveedorAlbaran =
                                new FormMaterialesCompradoProveedorAlbaran(FormAlbaran.this, textFieldCodigo.getText(), materiales, proveedores.get(comboBoxProveedores.getSelectedIndex()-1));
                    }
                }else if(estado == 2){
                    FormMaterialesCompradoProveedorAlbaran formMaterialesCompradoProveedorAlbaran =
                            new FormMaterialesCompradoProveedorAlbaran(FormAlbaran.this, textFieldCodigo.getText(), materiales, materialesCompradoProveedor, proveedores.get(comboBoxProveedores.getSelectedIndex()-1));
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
    private ArrayList<MaterialCompradoProveedor> materialesCompradoProveedor;
    int estado = 0;
    private Albaran AlbaranSiendoModificado;

    //endregion
}
