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
        albaranes = crudAlbaran.getAll();
        materiales = getMateriales();
        actuaciones = getActuaciones();
        proveedores = getProveedores();
        viewAlbaran = new ViewAlbaran(this, albaranes, materiales, actuaciones, proveedores);
    }

    //region CRUD
    public boolean createAlbaran(ArrayList<Albaran> albaranes){

        Albaran albaran = albaranes.get(0);

        try {
            int idAlbaran = crudAlbaran.createAlbaran(albaran);
            albaran.setId(idAlbaran);
            viewAlbaran.addTableAlbaran(albaran);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAlbaran(Albaran albaran) {
        boolean result = crudAlbaran.updateAlbaran(albaran);
        if (result){
            viewAlbaran.updateTableAlbaran(albaran);
        }
        return result;
    }

    public boolean deleteAlbaran(int cod){
        boolean result = crudAlbaran.deleteAlbaran(cod);
        if (result){
            viewAlbaran.ShowMessage( "CORRECTO", "El albaran con codigo: " + cod + " ha sido borrado");
        }else{
            viewAlbaran.ShowErrorMessage("ERROR", "El albaran con codigo: " + cod + " no se ha podido borrar");
        }
        return result;
    }

    public boolean createMaterialesCompradoProveedor(ArrayList<MaterialCompradoProveedor> materialesCompradoProveedor) {
//        boolean result = crudAlbaran.createMaterialesCompradoProveedor(materialesCompradoProveedor);
//        if (result){
//            viewAlbaran.ShowMessage( "CORRECTO", "El albaran con codigo: " + cod + " ha sido borrado");
//        }else{
//            viewAlbaran.ShowErrorMessage("ERROR", "El albaran con codigo: " + cod + " no se ha podido borrar");
//        }
//        return result;
        return true;
    }

    //endregion

    // region MetaDatos

    public String[] getColumnsName(){
        String[] listColumnsName = crudAlbaran.getColumnsAlbaran();
        if (listColumnsName[0] == null){
            System.out.println("Fallo en base de datos");
        }
        if (listColumnsName[0].equals("Error en CRUD")){
            System.out.println("Fallo en CRUD");
        }
        return listColumnsName;
    }
    //endregion

    //region Parametros Constructor
    private ArrayList<Material> getMateriales(){
        ArrayList<Material> listMaterial;
        CRUDMaterial crudMaterial = new CRUDMaterial();
        listMaterial = crudMaterial.getAll();
        return listMaterial;
    }

    private ArrayList<Actuacion> getActuaciones(){
        ArrayList<Actuacion> listActuacion;
        CRUDActuacion crudActuacion = new CRUDActuacion();
        listActuacion = crudActuacion.getAll();
        return listActuacion;
    }

    private ArrayList<Proveedor> getProveedores(){
        ArrayList<Proveedor> listProveedores;
        CRUDProveedor crudProveedor = new CRUDProveedor();
        listProveedores = crudProveedor.getAll();
        return listProveedores;
    }


    //endregion


    //region Variables
    CRUDAlbaran crudAlbaran;
    ViewAlbaran viewAlbaran;
    ArrayList<Albaran> albaranes;
    ArrayList<Material> materiales;
    ArrayList<Actuacion> actuaciones;
    ArrayList<Proveedor> proveedores;



    //endregion
}
