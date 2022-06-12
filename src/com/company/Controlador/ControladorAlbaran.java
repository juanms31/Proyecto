package com.company.Controlador;

import com.company.BaseDatos.*;
import com.company.Entidades.*;
import com.company.Vistas.ViewAlbaran;

import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorAlbaran {
    //Constructor
    public ControladorAlbaran() {
        crudAlbaran = new CRUDAlbaran(this);
        crudMaterialCompradoProveedor = new CRUDMaterialCompradoProveedor();
        crudMaterialUtilizadoActuacion = new CRUDMaterialUtilizadoActuacion();
        albaranes = crudAlbaran.getAll();
        materiales = getMateriales();
        materialesCompradosProveedor = getMaterialesCompradosProveedor();
        actuaciones = getActuaciones();
        proveedores = getProveedores();
        viewAlbaran = new ViewAlbaran(this, albaranes, materiales, materialesCompradosProveedor, actuaciones, proveedores);
    }

    //region CRUD
    public boolean createAlbaran(Albaran albaran) {

        try {
            int idAlbaran = crudAlbaran.createAlbaran(albaran);
            albaran.setId(idAlbaran);
            setIdAlbaran(albaran.getCod(), idAlbaran);

            viewAlbaran.addTableAlbaran(albaran);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void setIdAlbaran(String cod, int id){
        int i = 0;
        for(Albaran albaran : albaranes){
            if(cod.equals(albaran.getCod())){
                albaranes.get(i).setId(id);
            }
            i++;
        }
    }

    private Albaran getAlbaranFromCod(String cod){
        int i = 0;
        for(Albaran albaran : albaranes){
            if(cod.equals(albaran.getCod())){
                return albaranes.get(i);
            }
            i++;
        }
        return new Albaran();
    }

    public boolean updateAlbaran(Albaran albaran) {
        boolean result = crudAlbaran.updateAlbaran(albaran);
        if (result) {
            viewAlbaran.updateTableAlbaran(albaran);
        }
        return result;
    }

    public boolean deleteAlbaran(int cod) {
        boolean result = crudAlbaran.deleteAlbaran(cod);
        return result;
    }

    public boolean createMaterialesCompradoProveedor(ArrayList<MaterialCompradoProveedor> materialesCompradoProveedor) {

        int i = 0;
        boolean result = false;
        Albaran albaran = getAlbaranFromCod(materialesCompradoProveedor.get(0).getAlbaran().getCod());

        try {
            for (MaterialCompradoProveedor materialCompradoProveedor : materialesCompradoProveedor) {

                materialCompradoProveedor.setAlbaran(albaran);

                int idMaterialCompradoProveedor = crudMaterialCompradoProveedor.createMaterialCompradoProveedor(materialCompradoProveedor);

                System.out.println(materialCompradoProveedor.getAlbaran().toString());

                crudMaterialUtilizadoActuacion.createMaterialUtilizadoActuacion(
                        materialCompradoProveedor.getMaterial().getId(),
                        materialCompradoProveedor.getActuacion().getId());

                materialesCompradoProveedor.get(i++).setId(idMaterialCompradoProveedor);

                materialCompradoProveedor.setId(idMaterialCompradoProveedor);

                if(idMaterialCompradoProveedor != 0){
                    result = true;
                }

                viewAlbaran.addTableMaterialAlbaran(materialCompradoProveedor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }

    public boolean updateMaterialesAlbaran(ArrayList<MaterialCompradoProveedor> materialesCompradoProveedor) {
        int i = 0;
        boolean result = false;
        Albaran albaran = getAlbaranFromCod(materialesCompradoProveedor.get(0).getAlbaran().getCod());

        try {
            for (MaterialCompradoProveedor materialCompradoProveedor : materialesCompradoProveedor) {

                materialCompradoProveedor.setAlbaran(albaran);

                result = crudMaterialCompradoProveedor.updateMaterialCompradoProveedor(materialCompradoProveedor);

                viewAlbaran.updateMaterialesCompradosAlbaran(materialCompradoProveedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }

    //endregion

    // region MetaDatos

    public String[] getColumnsName() {
        String[] listColumnsName = crudAlbaran.getColumnsAlbaran();
        if (listColumnsName[0] == null){
            viewAlbaran.ShowErrorMessage("Error", "No se han detectado atributos para el albaran en la BBDD. Contacte con un administrador");
        }
        if (listColumnsName[0].equals("Error en CRUD")){
            viewAlbaran.ShowErrorMessage("Error", "No se han detectado atributos para el albaran en la BBDD. Contacte con un administrador");
        }
        return listColumnsName;
    }
    //endregion

    //region Parametros Constructor
    private ArrayList<Material> getMateriales() {
        ArrayList<Material> listMaterial;
        CRUDMaterial crudMaterial = new CRUDMaterial();
        listMaterial = crudMaterial.getAll();
        return listMaterial;
    }

    private ArrayList<MaterialCompradoProveedor> getMaterialesCompradosProveedor() {
        crudMaterialCompradoProveedor = new CRUDMaterialCompradoProveedor();
        return crudMaterialCompradoProveedor.getAll();

    }

    private ArrayList<Actuacion> getActuaciones() {
        ArrayList<Actuacion> listActuacion;
        CRUDActuacion crudActuacion = new CRUDActuacion();
        listActuacion = crudActuacion.getAll();
        return listActuacion;
    }

    private ArrayList<Proveedor> getProveedores() {
        ArrayList<Proveedor> listProveedores;
        CRUDProveedor crudProveedor = new CRUDProveedor();
        listProveedores = crudProveedor.getAll();
        return listProveedores;
    }


    //endregion


    //region Variables
    CRUDAlbaran crudAlbaran;

    CRUDMaterialCompradoProveedor crudMaterialCompradoProveedor;
    CRUDMaterialUtilizadoActuacion crudMaterialUtilizadoActuacion;
    ViewAlbaran viewAlbaran;
    ArrayList<Albaran> albaranes;
    ArrayList<Material> materiales;
    ArrayList<MaterialCompradoProveedor> materialesCompradosProveedor;
    ArrayList<Actuacion> actuaciones;
    ArrayList<Proveedor> proveedores;


    //endregion
}
