package com.company.Controlador;

import com.company.BaseDatos.CRUDActuacion;
import com.company.BaseDatos.CRUDAlbaran;
import com.company.BaseDatos.CRUDProveedor;
import com.company.Entidades.Albaran;
import com.company.Entidades.Proveedor;
import com.company.Vistas.ViewAlbaran;
import com.company.Vistas.ViewProveedor;

import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorAlbaran {
    //Constructor
    public ControladorAlbaran(){
        crudAlbaran = new CRUDAlbaran(this);
        ArrayList<Albaran> albaranes = crudAlbaran.getAll();
        viewAlbaran = new ViewAlbaran(this, albaranes);
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

    public boolean deleteProveedor(int cod){
        boolean result = crudProveedor.deleteProveedor(cod);
        if (result){
            viewProveedor.ShowMessage( "CORRECTO", "El proveedor con codigo: " + cod + " ha sido borrado");
        }else{
            viewProveedor.ShowErrorMessage("ERROR", "El proveedor con codigo: " + cod + " no se ha podido borrar");
        }
        return result;
    }

    //endregion

    // region MetaDatos

    public String[] getColumnsName(){
        String[] listColumnsName = crudProveedor.getColumnsProveedor();
        if (listColumnsName[0] == null){
            System.out.println("Fallo en base de datos");
        }
        if (listColumnsName[0].equals("Error en CRUD")){
            System.out.println("Fallo en CRUD");
        }
        return listColumnsName;
    }

    //endregion

    //region Variables
    CRUDAlbaran crudAlbaran;
    ViewAlbaran viewAlbaran;

    //endregion
}
