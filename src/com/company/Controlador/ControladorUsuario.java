package com.company.Controlador;

import com.company.BaseDatos.CRUDTrabajador;
import com.company.BaseDatos.CRUDUsuario;
import com.company.Entidades.Trabajador;
import com.company.Entidades.Usuario;
import com.company.Vistas.ViewTrabajador;

import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorUsuario {
    private CRUDUsuario crudUsuario;
    private ViewTrabajador viewTrabajador;

    //Constructor
    public ControladorUsuario() {
        crudUsuario = new CRUDUsuario();
        ArrayList<Usuario> usuarios = crudUsuario.getAll();
    }

    //region CRUD

    public ArrayList<Usuario> getUsers(){
        return crudUsuario.getAll();
    }

    public boolean createUsuario(Usuario usuario){
        int idUsuario = crudUsuario.createUsuario(usuario);
        if(idUsuario != 0){
            usuario.setId(idUsuario);
            viewTrabajador.ShowMessage("CORRECTO", "Usuario " + usuario.getNombre() + " agregado con exito");
            return true;
        }else{
            viewTrabajador.ShowErrorMessage("ERROR", "No se ha podido agregar al usuario: " + usuario.getNombre());
            return false;
        }

    }

//    public boolean updateTrabajador(Trabajador trabajador) {
//        boolean result = crudTrabajador.updateTrabajador(trabajador);
//        if (result){
//            viewTrabajador.updateTableTrabajador(trabajador);
//            viewTrabajador.ShowMessage( "CORRECTO", "Cliente " + trabajador.getNombre() + " ha sido actualizado");
//        }else{
//            viewTrabajador.ShowErrorMessage("ERROR", "No se ha podiddo actualizar cliente con el codigo: " + trabajador.getId());
//        }
//        return result;
//    }
//
//    public boolean deleteTrabajador(Trabajador trabajador){
//        boolean result = crudTrabajador.deleteTrabajador(trabajador.getId());
//        if (result){
//            viewTrabajador.ShowMessage("CORRECTO", "Trabajador " + trabajador.getNombre() + " ha sido borrado");
//        }else{
//            viewTrabajador.ShowErrorMessage("ERROR","Trabajador " + trabajador.getNombre() + " no se ha podido borrar");
//        }
//        return result;
//    }
//
//    //endregion
//
//    // region MetaDatos
//
//    public String[] getColumnsName(){
//        String[] listColumnsName = crudTrabajador.getColumnsTrabajador();
//        if (listColumnsName[0] == null){
//            System.out.println("Fallo en base de datos");
//        }
//        if (listColumnsName[0].equals("Error en CRUD")){
//            System.out.println("Fallo en CRUD");
//        }
//        return listColumnsName;
//    }

    //endregion
}
