package com.company.Formularios;

import com.company.Entidades.Albaran;
import com.company.Entidades.Cliente;
import com.company.Vistas.ViewAlbaran;
import com.company.Vistas.ViewCliente;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;

public class FormAlbaran extends JDialog {
    //region Constructores

    public FormAlbaran(ViewAlbaran viewAlbaran) {
        estado = 1;
        this.viewAlbaran = viewAlbaran;
        initWindow();
        initComps();
        initListeners();
        setVisible(true);

    }

    public FormAlbaran(ViewAlbaran viewAlbaran, Albaran albaran) {
        estado = 2;
        AlbaranSiendoModificado = albaran;
        this.viewAlbaran = viewAlbaran;
        initListeners();
        setAlbaran(albaran);
        initWindow();
        initComps();
        setVisible(true);
    }

    public FormAlbaran(ViewAlbaran viewAlbaran, Albaran albaran, boolean editable) {
        this.viewAlbaran = viewAlbaran;
        setAlbaran(albaran);
        initWindow();
        initComps();
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

    }

    //endregion

    //region Metodos privados

    private void loadNewAlbaran() {

        boolean conErrores = checkFields();

        if(conErrores){

        }else{

            Albaran albaran = getAlbaran();
            if(viewAlbaran.getNewAlbaranFromFormulario(albaran)){
                dispose();
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
            Albaran albaran = getAlbaran();
            if (viewAlbaran.getUpdateAlbaranFromFormulario(albaran)){
                dispose();
            }else {
                ShowErrorMessage("Error", "No se ha podido crear el albaran correctamente");
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
    private void setAlbaran(Albaran albaran) {
        textFieldCodigo.setText(String.valueOf(albaran.getId()));
        textFieldUnidades.setText(String.valueOf(albaran.getUnidades()));
        textFieldPrecioUnitario.setText(String.valueOf(albaran.getPrecioUnidad()));
        textFieldBase.setText(String.valueOf(albaran.getBaseImponible()));
        comboBoxNaturaleza.setSelectedItem(albaran.getNaturaleza());
        formattedTextFieldFechaEntrada.setText(String.valueOf(albaran.getFechaEntradaAlbaran()));
    }

    private boolean checkFields() {
        if (textFieldCodigo.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Codigo no puede estar vacio");
            return true;
        }
        if (textFieldUnidades.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Unidades no puede estar vacio");
            return true;
        }
        if (textFieldPrecioUnitario.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Precio Unitario no puede estar vacio");
            return true;
        }
        if (comboBoxNaturaleza.getSelectedItem().equals("")) {
            ShowErrorMessage("Error", "Campo Naturaleza no puede estar vacio");
            return true;
        }
        if (formattedTextFieldFechaEntrada.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Fecha Entrada no puede estar vacio");
            return true;
        }

        return false;
    }

    private Albaran getAlbaran() {
        Albaran albaran = new Albaran();

        boolean conErrores = checkFields();

        if (estado == 2) {
            albaran.setId(AlbaranSiendoModificado.getId());

            albaran.setUnidades(Integer.parseInt(textFieldUnidades.getText()));
            albaran.setPrecioUnidad(Double.parseDouble(textFieldPrecioUnitario.getText()));
            albaran.setBaseImponible(Double.parseDouble(textFieldBase.getText()));
            albaran.setNaturaleza(comboBoxNaturaleza.getSelectedItem().toString());
            albaran.setFechaEntradaAlbaran(Date.valueOf(formattedTextFieldFechaEntrada.getText()));

        } else {
            albaran.setUnidades(Integer.parseInt(textFieldUnidades.getText()));
            albaran.setPrecioUnidad(Double.parseDouble(textFieldPrecioUnitario.getText()));
            albaran.setBaseImponible(Double.parseDouble(textFieldBase.getText()));
            albaran.setNaturaleza(comboBoxNaturaleza.getSelectedItem().toString());
            albaran.setFechaEntradaAlbaran(Date.valueOf(formattedTextFieldFechaEntrada.getText()));
        }

        return albaran;
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
    }

    private void keyListeners() {
        textFieldUnidades.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        textFieldPrecioUnitario.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != ',')) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        textFieldBase.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != ',')) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

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
    private JLabel labelTitulo;
    private JTextField textFieldCodigo;
    private JTextField textFieldUnidades;
    private JTextField textFieldPrecioUnitario;
    private JTextField textFieldBase;
    private JButton aceptarButton;
    private JButton cancelarButton;
    private JFormattedTextField formattedTextFieldFechaEntrada;
    private JComboBox comboBoxNaturaleza;
    private JPanel panelPrincipal;
    int estado = 0;
    private Albaran AlbaranSiendoModificado;

    //endregion
}
