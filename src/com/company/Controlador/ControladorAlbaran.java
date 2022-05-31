package com.company.Controlador;

import com.company.BaseDatos.CRUDAlbaran;
import com.company.BaseDatos.CRUDCliente;
import com.company.BaseDatos.CRUDMaterial;
import com.company.Entidades.Albaran;
import com.company.Entidades.Cliente;
import com.company.Entidades.Material;
import com.company.Vistas.ViewAlbaran;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorAlbaran {
    //Constructor
    public ControladorAlbaran() {
        crudAlbaran = new CRUDAlbaran(this);
        albaranes = crudAlbaran.getAll();
        materiales = getMateriales();
        viewAlbaran = new ViewAlbaran(this, albaranes, materiales);
    }

    //region CRUD
    public boolean createAlbaran(Albaran albaran){

        try {
            int idAlbaran = crudAlbaran.createAlbaran(albaran);
            albaran.setId(idAlbaran);
            viewAlbaran.addTableAlbaran(albaran);
            viewAlbaran.ShowMessage( "CORRECTO", "Albaran " + albaran.getId() + " agregado con exito");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            viewAlbaran.ShowErrorMessage("ERROR", "No se ha podido agregar el registro");
            return false;
        }
    }

    public boolean updateAlbaran(Albaran albaran) {
        boolean result = crudAlbaran.updateAlbaran(albaran);
        if (result){
            viewAlbaran.updateTableAlbaran(albaran);
            viewAlbaran.ShowMessage("CORRECTO","El albaran " + albaran.getId() + " ha sido actualizado");
        }else{
            viewAlbaran.ShowErrorMessage("ERROR", "No se ha podiddo actualizar el albaran " + albaran.getId());
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


    //region Variables
    CRUDAlbaran crudAlbaran;
    ViewAlbaran viewAlbaran;
    ArrayList<Albaran> albaranes;
    ArrayList<Material> materiales;

    //endregion
}
