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
        actuaciones = crudActuacion.getAll();
        especificacionesActuacion = getEspecificacionActuacion();
        clientes = getClientes();
        setClienteObject();
        viewActuacion = new ViewActuacion(this, actuaciones,clientes, especificacionesActuacion);
    }

    //region CRUD
    public boolean createActuacion (Actuacion actuacion){
        try {
            int idActuacion = crudActuacion.createActuacion(actuacion);
            actuacion.setId(idActuacion);
            viewActuacion.addTableActuacion(actuacion);
            viewActuacion.ShowMessage( "CORRECTO", "Actuacion " + actuacion.getNombre() + " agregado con exito");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            viewActuacion.ShowErrorMessage("No se ha podido agregar el registro", "ERROR");
            return false;
        }
    }

    public boolean updateActuacion(Actuacion actuacion) {
        boolean result = crudActuacion.updateActuacion(actuacion);
        if (result){
            viewActuacion.updateTableActuacion(actuacion);
            viewActuacion.ShowMessage("CORRECTO","La actuacion " + actuacion.getNombre() + " ha sido actualizada");
        }else{
            viewActuacion.ShowErrorMessage("ERROR", "No se ha podiddo actualizar la actuacion " + actuacion.getNombre());
        }
        return result;
    }

    public boolean deleteActuacion(int id){
        boolean result = crudActuacion.deleteActuacion(id);
        if (result){
            viewActuacion.ShowMessage( "CORRECTO", "La actuacion " + id + " ha sido eliminada");
        }else{
            viewActuacion.ShowErrorMessage("ERROR", "La actuacion " + id + " ha sido eliminada");
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

    public String[] getColumnsNameCliente(){
        CRUDCliente crudCliente = new CRUDCliente();
        String[] listColumnsName = crudCliente.getColumnsCliente();
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

    private void setClienteObject() {
        int posicion = 0;
        for (Actuacion actuacion : actuaciones){

            for(Cliente cliente : clientes){
                if(actuacion.getIdCliente() ==  cliente.getId()) {
                    actuaciones.get(posicion).setCliente(cliente);
                }
            }

            posicion++;
        }
    }

    //endregion

    //region Variables
    private CRUDActuacion crudActuacion;
    private ViewActuacion viewActuacion;
    private ArrayList<Actuacion> actuaciones;
    private ArrayList<EspecificacionActuacion> especificacionesActuacion;
    private ArrayList<Cliente> clientes;

    //endregion
}
