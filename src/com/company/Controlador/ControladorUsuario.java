package com.company.Controlador;

import com.company.BaseDatos.CRUDUsuario;
import com.company.Entidades.Usuario;
import com.company.Vistas.ViewUsuario;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorUsuario {

    //Constructor
    public ControladorUsuario(Usuario usuario) {
        crudUsuario = new CRUDUsuario();
        viewUsuario = new ViewUsuario(usuario);
        ArrayList<Usuario> usuarios = crudUsuario.getAll();
    }
    public ControladorUsuario() {
        crudUsuario = new CRUDUsuario();
    }


    //region CRUD

    public ArrayList<Usuario> getUsers(){
        return crudUsuario.getAll();
    }

    public boolean createUsuario(Usuario usuario){
        try {
            int idUsuario = crudUsuario.createUsuario(usuario);
            usuario.setId(idUsuario);

            ShowMessage("CORRECTO", "Usuario " + usuario.getNombre() + " agregado con exito");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            ShowErrorMessage("ERROR", "No se ha podido agregar el registro");
            return false;
        }

    }

    //region Mensajes
    public void ShowMessage(String title, String msg) {
        JOptionPane.showMessageDialog(null,
                msg ,
                title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void ShowWarningMessage(String title, String msg) {
        JOptionPane.showMessageDialog(null,
                msg ,
                title,
                JOptionPane.WARNING_MESSAGE);
    }

    public void ShowErrorMessage(String title, String msg) {
        JOptionPane.showMessageDialog(null,
                msg ,
                title,
                JOptionPane.ERROR_MESSAGE);
    }

    //endregion

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

    //region Variables
    private CRUDUsuario crudUsuario;
    private ViewUsuario viewUsuario;
    //endregion

}
