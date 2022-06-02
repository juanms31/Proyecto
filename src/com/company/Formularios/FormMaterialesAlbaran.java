package com.company.Formularios;

import com.company.Entidades.Albaran;
import com.company.Entidades.Material;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FormMaterialesAlbaran extends JDialog {
    //region Constructor

    public FormMaterialesAlbaran(FormAlbaran formAlbaran, String codAlbaran, ArrayList<Material> materiales) {
        this.formAlbaran = formAlbaran;
        this.codAlbaran = codAlbaran;
        this.materiales = materiales;
        materialesOut = new ArrayList<>();
        initWindow();
        initComps();
        initListeners();
        setVisible(true);
    }
    //endregion

    //region Vista
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
        setTitle("Registro Materiales Albarán");
        setIconImage(new ImageIcon("src/com/company/Images/Logo/logoEnano.jpg").getImage());
    }

    public void centerFrame() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screen.width / 2, screen.height / 2);
        Dimension window = getSize();
        int width = (screen.width - window.width) / 2;
        int height = (screen.height - window.height) / 2;
        setLocation(width, height);
    }

    public void initComps() {

//        Rellenamos el combo box con los materiales
        comboBoxMateriales.addItem("Selecciona Material");
        for (Material material : materiales) {
            //Acotamos el nombre para que no sea muy largo a 15 caracteres..
            comboBoxMateriales.addItem(material.getCodigo() + " - " + material.getDescripcion().substring(0, 18) + "...");

        }

        textFieldCodigo.setText(codAlbaran);

        listMateriales.setModel(modelMateriales);
    }
    //endregion

    //region Mensajes

    public void ShowErrorMessage(String title, String msg) {
        JOptionPane.showMessageDialog(this,
                msg,
                title,
                JOptionPane.ERROR_MESSAGE);
    }
    //endregion

    //region Listeners
    private void initListeners() {
        actionListeners();
    }

    private void actionListeners() {
        buttonAnadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addElement();

            }
        });

        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeElement();

            }
        });

        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formAlbaran.setMaterialesFromFormulario(getMateriales());
                dispose();
            }
        });
    }

    //region Metodos Privados

    public void addElement(){
        int row = comboBoxMateriales.getSelectedIndex();
        if (row == 0) {
            ShowErrorMessage("Error", "Selecciona un material antes de añadirlo");
        } else {
            Material material = materiales.get(row - 1);
            materialesOut.add(material);
            modelMateriales.addElement(String.valueOf(material.getCodigo() + " - " + material.getDescripcion()));
        }
    }

    public void removeElement(){
        int row = listMateriales.getSelectedIndex();
        Material material = materiales.get(row);
        materialesOut.remove(row);
        modelMateriales.removeElementAt(row);
    }

    public ArrayList<Material> getMateriales(){
        return materialesOut;
    }

    //endregion



    //region Variables
    private FormAlbaran formAlbaran;
    private JLabel labelTitulo;
    private JTextField textFieldCodigo;
    private JButton aceptarButton;
    private JButton cancelarButton;
    private JPanel panelPrincipal;
    private JList listMateriales;
    private JButton buttonAnadir;
    private JButton buttonEliminar;
    private JComboBox comboBoxMateriales;
    private String codAlbaran;
    private ArrayList<Material> materiales;
    private ArrayList<Material> materialesOut;
    private DefaultListModel<String> modelMateriales = new DefaultListModel<>();
    //endregion

}
