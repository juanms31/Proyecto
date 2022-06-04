package com.company.Formularios;

import com.company.Entidades.Material;
import com.company.Entidades.MaterialCompradoProveedor;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;

public class FormMaterialesCompradoProveedorAlbaran extends JDialog {
    //region Constructor

    public FormMaterialesCompradoProveedorAlbaran(FormAlbaran formAlbaran, String codAlbaran, ArrayList<Material> materiales) {
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
            comboBoxMateriales.addItem(material.getCodigo() + " - " + material.getDescripcion().substring(0, material.getDescripcion().length() / 2) + "...");

        }

        textFieldCodigo.setText(codAlbaran);

        initTable();
    }

    //endregion

    //region METODOS TABLA
    public void initTable() {
        TableMateriales.setShowGrid(true);
        TableMateriales.setCellSelectionEnabled(false);
        TableMateriales.setAutoCreateRowSorter(true);
        TableMateriales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableMateriales.setRowSelectionAllowed(true);
        TableMateriales.setDragEnabled(false);

        TableCellEditor tableCellEditor = new TableCellEditor() {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return null;
            }

            @Override
            public Object getCellEditorValue() {
                return null;
            }

            @Override
            public boolean isCellEditable(EventObject anEvent) {
                return true;
            }

            @Override
            public boolean shouldSelectCell(EventObject anEvent) {
                return false;
            }

            @Override
            public boolean stopCellEditing() {
                return false;
            }

            @Override
            public void cancelCellEditing() {

            }

            @Override
            public void addCellEditorListener(CellEditorListener l) {

            }

            @Override
            public void removeCellEditorListener(CellEditorListener l) {

            }
        };

        TableMateriales.setCellEditor(tableCellEditor);

        sorter = new TableRowSorter<TableModel>(TableMateriales.getModel());

        TableMateriales.setRowSorter(sorter);

        //Filling Headers
        modelMaterialesAlbaran = new DefaultTableModel(headers, 0);

        TableMateriales.setModel(modelMaterialesAlbaran);
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
                formAlbaran.setMaterialesCompradoProveedorFromFormulario(getMaterialCompradoProveedor());
                dispose();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void mouseListeners() {
        TableMateriales.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    TableMateriales.isCellEditable(TableMateriales.getSelectedRow(), TableMateriales.getSelectedColumn());
                }
            }
        });
    }

    //endregion

    //region Metodos Privados

    public void addElement() {
        if (materialesOut.contains(materiales.get(comboBoxMateriales.getSelectedIndex() - 1))) {
            ShowErrorMessage("Error", "Selecciona solo un material por línea.");
        } else {
            int row = comboBoxMateriales.getSelectedIndex();
            if (row == 0) {
                ShowErrorMessage("Error", "Selecciona un material de la lista para añadirlo");
            } else {
                Material material = materiales.get(row - 1);
                materialesOut.add(material);
                modelMaterialesAlbaran.addRow(getMaterialObject(material));
            }
        }
    }

    public Object[] getMaterialObject(Material material) {
        int y = 0;
        Object[] newMaterial = new Object[headers.length];
        newMaterial[y++] = material.getCodigo();
        newMaterial[y++] = material.getDescripcion();
        newMaterial[y++] = "";
        newMaterial[y++] = material.getPrecio1();


        return newMaterial;
    }

    public void removeElement() {
        int row = TableMateriales.getSelectedRow();
        if (row == -1) {

            ShowErrorMessage("Error", "Selecciona un material para eliminar.");

        } else {
            Material material = materiales.get(row);
            materialesOut.remove(row);
            modelMaterialesAlbaran.removeRow(row);
        }

    }

    public ArrayList<Material> getMateriales() {
        return materialesOut;
    }

    public ArrayList<MaterialCompradoProveedor> getMaterialCompradoProveedor() {

        ArrayList<MaterialCompradoProveedor> materialesCompradoProveedor = new ArrayList<>();

        for (int i = 0; i < TableMateriales.getRowCount(); i++) {
            int j = 1;

            MaterialCompradoProveedor materialCompradoProveedor = new MaterialCompradoProveedor();
            materialCompradoProveedor.setMaterial(materialesOut.get(i));
            j++;

            materialCompradoProveedor.setUnidades(Integer.valueOf(String.valueOf(TableMateriales.getValueAt(i, j++))));
            materialCompradoProveedor.setPrecioUnidad(Double.valueOf(String.valueOf(TableMateriales.getValueAt(i, j))));
            materialCompradoProveedor.setBaseImponible(materialCompradoProveedor.getUnidades()*materialCompradoProveedor.getPrecioUnidad());

            materialesCompradoProveedor.add(materialCompradoProveedor);
        }

        return materialesCompradoProveedor;
    }

    //endregion


    //region Variables
    private FormAlbaran formAlbaran;
    private JLabel labelTitulo;
    private JTextField textFieldCodigo;
    private JButton aceptarButton;
    private JButton cancelarButton;
    private JPanel panelPrincipal;
    private JButton buttonAnadir;
    private JButton buttonEliminar;
    private JComboBox comboBoxMateriales;
    private JTable TableMateriales;
    private String codAlbaran;
    private ArrayList<Material> materiales;
    private ArrayList<Material> materialesOut;
    private DefaultTableModel modelMaterialesAlbaran;
    private TableRowSorter sorter;

    private String[] headers = {"COD", "DESC. MATERIAL", "UNIDADES", "PRECIO UNITARIO"};

    //endregion

}
