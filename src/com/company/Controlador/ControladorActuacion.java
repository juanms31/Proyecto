package com.company.Controlador;

import com.company.BaseDatos.*;
import com.company.Entidades.*;
import com.company.Vistas.ViewActuacion;
import com.company.Vistas.ViewProveedor;

import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorActuacion {

    //Constructor
    public ControladorActuacion(){
        crudActuacion = new CRUDActuacion();
        ArrayList<Actuacion> actuaciones = crudActuacion.getAll();
        ArrayList<EspecificacionActuacion> especificacionActuacions = getEspecificacionActuacion();
        ArrayList<Cliente> clientes = getClientes();
        viewActuacion = new ViewActuacion(this, actuaciones,clientes, especificacionActuacions);
    }

    //region CRUD
    public boolean createActuacion (Actuacion actuacion){
        try {
            int idActuacion = crudActuacion.createActuacion(actuacion);
            actuacion.setId(idActuacion);
            viewActuacion.addTableActuacion(actuacion);
            viewActuacion.ShowMessage( "CORRECTO", "Actuacion " + actuacion.getId() + " agregado con exito");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            viewActuacion.ShowErrorMessage("No se ha podido agregar el registro", "ERROR");
            return false;
        }
    }

//    public Cliente readProveedor(int cod){
//        Cliente cliente = crudProveedor.readProveedor(cod);
//        if (cliente.getId() > 0){
//            viewProveedor.ShowMessage("CORRECTO", "No se ha podido cargar el cliente con codigo: " + cod, );
//            //TODO en principio se puede leer el material sin consultar a la bbdd ya que esta cargado en memoria
//        }else{
//            viewProveedor.ShowMessage("Correcto, cargando cliente...", "CORRECTO");
//            //TODO quizas este mensaje sobre y no sea necesario
//        }
//        return  cliente;
//    }

    public boolean updateActuacion(Actuacion actuacion) {
        boolean result = crudActuacion.updateActuacion(actuacion);
        if (result){
            viewActuacion.updateTableActuacion(actuacion);
            viewActuacion.ShowMessage("CORRECTO","La actuacion " + actuacion.getId() + " ha sido actualizada");
        }else{
            viewActuacion.ShowErrorMessage("ERROR", "No se ha podiddo actualizar la actuacion " + actuacion.getId());
        }
        return result;
    }

    public boolean deleteActuacion(int id){
        boolean result = crudActuacion.deleteActuacion(id);
        if (result){
            viewActuacion.ShowMessage( "CORRECTO", "La actuacion " + id + " ha sido actualizada");
        }else{
            viewActuacion.ShowErrorMessage("ERROR", "La actuacion " + id + " ha sido actualizada");
        }
        return result;
    }

    //endregion

    // region MetaDatos

    public String[] getColumnsName(){
        String[] listColumnsName = crudActuacion.getColumnActuacion();
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
    private ArrayList<EspecificacionActuacion> getEspecificacionActuacion(){
        ArrayList<EspecificacionActuacion> listEspecificacionActuacion;
        CRUDEspecificacionActuacion crudEspecificacionActuacion = new CRUDEspecificacionActuacion();
        listEspecificacionActuacion = crudEspecificacionActuacion.getAll();
        return listEspecificacionActuacion;
    }

    private ArrayList<Cliente> getClientes(){
        ArrayList<Cliente> listClientes;
        CRUDCliente crudCliente = new CRUDCliente();
        listClientes = crudCliente.getAll();
        return listClientes;
    }

    //endregion

    //region Variables
    CRUDActuacion crudActuacion;
    ViewActuacion viewActuacion;

    //endregion
}
