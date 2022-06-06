package com.company.Controlador;

import com.company.BaseDatos.CRUDTrabajador;
import com.company.Entidades.Trabajador;
import com.company.Vistas.ViewTrabajador;

import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorTrabajador {

    private CRUDTrabajador crudTrabajador;
    private ViewTrabajador viewTrabajador;

    //Constructor
    public ControladorTrabajador() {
        crudTrabajador = new CRUDTrabajador(this);
        ArrayList<Trabajador> trabajadores = crudTrabajador.getAll();
        viewTrabajador = new ViewTrabajador(this, trabajadores);
    }

    //region CRUD

    public boolean createTrabajador(Trabajador trabajador){
        try {
            int idTrabajador = crudTrabajador.createTrabajador(trabajador);
            trabajador.setId(idTrabajador);
            viewTrabajador.addTableTrabajador(trabajador);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTrabajador(Trabajador trabajador) {
        boolean result = crudTrabajador.updateTrabajador(trabajador);
        if (result){
            viewTrabajador.updateTableTrabajador(trabajador);
        }
        return result;
    }

    public boolean deleteTrabajador(Trabajador trabajador){
        boolean result = crudTrabajador.deleteTrabajador(trabajador.getId());

        return result;
    }

    //endregion

    // region MetaDatos

    public String[] getColumnsName(){
        String[] listColumnsName = crudTrabajador.getColumnsTrabajador();
        if (listColumnsName[0] == null){
            viewTrabajador.ShowErrorMessage("Error", "No se han detectado atributos para el trabajador en la BBDD. Contacte con un administrador");
        }
        if (listColumnsName[0].equals("Error en CRUD")){
            viewTrabajador.ShowErrorMessage("Error", "No se han detectado atributos para el trabajador en la BBDD. Contacte con un administrador");
        }
        return listColumnsName;
    }

    //endregion
}
