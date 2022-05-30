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
            viewTrabajador.ShowMessage("CORRECTO", "Trabajador " + trabajador.getNombre() + " agregado con exito");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            viewTrabajador.ShowErrorMessage("ERROR", "No se ha podido agregar el registro");
            return false;
        }
    }

    public boolean updateTrabajador(Trabajador trabajador) {
        boolean result = crudTrabajador.updateTrabajador(trabajador);
        if (result){
            viewTrabajador.updateTableTrabajador(trabajador);
            viewTrabajador.ShowMessage( "CORRECTO", "Cliente " + trabajador.getNombre() + " ha sido actualizado");
        }else{
            viewTrabajador.ShowErrorMessage("ERROR", "No se ha podiddo actualizar cliente con el codigo: " + trabajador.getId());
        }
        return result;
    }

    public boolean deleteTrabajador(Trabajador trabajador){
        boolean result = crudTrabajador.deleteTrabajador(trabajador.getId());
        if (result){
            viewTrabajador.ShowMessage("CORRECTO", "Trabajador " + trabajador.getNombre() + " ha sido borrado");
        }else{
            viewTrabajador.ShowErrorMessage("ERROR","Trabajador " + trabajador.getNombre() + " no se ha podido borrar");
        }
        return result;
    }

    //endregion

    // region MetaDatos

    public String[] getColumnsName(){
        String[] listColumnsName = crudTrabajador.getColumnsTrabajador();
        if (listColumnsName[0] == null){
            System.out.println("Fallo en base de datos");
        }
        if (listColumnsName[0].equals("Error en CRUD")){
            System.out.println("Fallo en CRUD");
        }
        return listColumnsName;
    }

    //endregion
}
