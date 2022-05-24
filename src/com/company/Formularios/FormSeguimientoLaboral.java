package com.company.Formularios;

import com.company.Entidades.Proveedor;
import com.company.Entidades.SeguimientoLaboral;
import com.company.Vistas.ViewProveedor;
import com.company.Vistas.ViewSeguimiento;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FormSeguimientoLaboral extends JDialog {

    public FormSeguimientoLaboral(ViewSeguimiento viewSeguimiento) {
        estado = 1;
        this.viewSeguimiento = viewSeguimiento;
        initWindow();
        initComps();
        initListeners();
        setVisible(true);

    }

    public FormSeguimientoLaboral(ViewSeguimiento viewSeguimiento, SeguimientoLaboral seguimientoLaboral) {
        estado = 2;
        SeguimientoLaboralSiendoModificado = seguimientoLaboral;
        this.viewSeguimiento = viewSeguimiento;
        initListeners();
        setSeguimiento(seguimientoLaboral);
        initWindow();
        initComps();
        setVisible(true);
    }

    public FormSeguimientoLaboral(ViewSeguimiento viewSeguimiento, SeguimientoLaboral seguimientoLaboral, boolean editable) {
        this.viewSeguimiento = viewSeguimiento;
        setSeguimiento(seguimientoLaboral);
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
        setTitle("Seguimiento Laboral");
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    public void centerFrame() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screen.height / 2, screen.width / 2);
        Dimension window = getSize();
        int width = (screen.width - window.width) / 2;
        int height = (screen.height - window.height) / 2;
        setLocation(width, height);
    }

    public void initComps() {

    }

    //endregion

    //region Metodos privados

    private void loadNewSeguimiento() {

        boolean conErrores = checkFields();

        if(conErrores){

        }else{

            SeguimientoLaboral seguimientoLaboral = getSeguimiento();
            if(viewSeguimiento.getNewSeguimientoFromFormulario(seguimientoLaboral)){
                dispose();
            }else{
                ShowErrorMessage("Error", "No se ha podido crear el seguimiento correctamente");
            }
            dispose();
        }
    }

    private void loadUpdateSeguimiento() {

        boolean conErrores = checkFields();

        if(conErrores){

        }else{
            SeguimientoLaboral seguimientoLaboral = getSeguimiento();
            if(viewSeguimiento.getUpdateSeguimientoFromFormulario(seguimientoLaboral)){
                dispose();
            }else {
                ShowErrorMessage("Error", "No se ha podido actualizar el seguimiento correctamente");
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
    private void setSeguimiento(SeguimientoLaboral seguimiento) {

        // FIXME: 24/05/2022 AL SETEAR LA ACTUACION DARA ERROR
        comboBoxActuacion.setSelectedItem(seguimiento.getActuacion());
        comboBoxTrabajador.setSelectedItem(seguimiento.getTrabajador());
        textFieldAno.setText(String.valueOf(seguimiento.getAno()));
        textFieldDia.setText(String.valueOf(seguimiento.getDia()));
        textFieldMes.setText(String.valueOf(seguimiento.getMes()));
        textFieldHora.setText(String.valueOf(seguimiento.getHora_entrada()));
        // FIXME: 24/05/2022 Añadir tipo a entidad
        //comboBoxTipo.setSelectedItem(seguimiento.getTipo());
        textFieldHorasTotales.setText(String.valueOf(seguimiento.getHoras_totales()));
        textFieldHorasExtra.setText(String.valueOf(seguimiento.getHoras_extra()));
    }

    private boolean checkFields() {
        if (textFieldAno.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Año no puede estar vacio");
            return true;
        }
        if (textFieldDia.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Dia no puede estar vacio");
            return true;
        }
        if (textFieldMes.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Mes no puede estar vacio");
            return true;
        }
        if (textFieldHora.getText().isEmpty()) {
            ShowErrorMessage("Error", "Campo Hora no puede estar vacio");
            return true;
        }

        return false;
    }

    private SeguimientoLaboral getSeguimiento() {
        SeguimientoLaboral seguimientoLaboral = new SeguimientoLaboral();

        boolean conErrores = checkFields();

        if (estado == 2) {
            seguimientoLaboral.setId(SeguimientoLaboralSiendoModificado.getId());
            // FIXME: 24/05/2022 VER COMO TRATAMOS ESTO
//            proveedor.setCIF(formattedTextFieldCIF.getText());
//            proveedor.setNombre_proveedor(textFieldNombre.getText());
//            proveedor.setDireccion(textFieldDireccion.getText());
//            proveedor.setMail1(textFieldMail1.getText());
//            proveedor.setMail2(textFieldMail2.getText());
//            proveedor.setTelefono1(textFieldTelefono1.getText());
//            proveedor.setTelefono2(textFieldTelefono2.getText());

        } else {
//            // FIXME: 24/05/2022 VER COMO TRATAMOS ESTO
//            proveedor.setCIF(formattedTextFieldCIF.getText());
//            proveedor.setNombre_proveedor(textFieldNombre.getText());
//            proveedor.setDireccion(textFieldDireccion.getText());
//            proveedor.setMail1(textFieldMail1.getText());
//            proveedor.setMail2(textFieldMail2.getText());
//            proveedor.setTelefono1(textFieldTelefono1.getText());
//            proveedor.setTelefono2(textFieldTelefono2.getText());
        }

        return seguimientoLaboral;
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
                        loadNewSeguimiento();
                    }

                    case 2 -> {
                        loadUpdateSeguimiento();
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
        textFieldDia.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        textFieldMes.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        textFieldHora.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != ':')) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

        textFieldAno.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
                    e.consume();  // ignorar el evento de teclado
                }
            }
        });

    }

    //endregion

    //region Variables
    private int estado = 0;
    private ViewSeguimiento viewSeguimiento;
    private SeguimientoLaboral SeguimientoLaboralSiendoModificado;
    private JLabel labelTitulo;
    private JButton aceptarButton;
    private JButton cancelarButton;
    private JComboBox comboBoxActuacion;
    private JComboBox comboBoxTrabajador;
    private JTextField textFieldAno;
    private JTextField textFieldDia;
    private JTextField textFieldMes;
    private JTextField textFieldHora;
    private JComboBox comboBoxTipo;
    private JTextField textFieldHorasTotales;
    private JTextField textFieldHorasExtra;
    private JPanel panelPrincipal;

    //endregion

}
