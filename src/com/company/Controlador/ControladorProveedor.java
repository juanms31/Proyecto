package com.company.Controlador;

import com.company.BaseDatos.CRUDProveedor;
import com.company.Entidades.Proveedor;
import com.company.Vistas.ViewProveedor;

import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorProveedor {
    private CRUDProveedor crudProveedor;
    private ViewProveedor viewProveedor;

    //Constructor
    public ControladorProveedor(){
        crudProveedor = new CRUDProveedor(this);
        ArrayList<Proveedor> proveedores = crudProveedor.getAll();
        viewProveedor = new ViewProveedor(this, proveedores);
    }

    //region CRUD
    public boolean createProveedor(Proveedor proveedor){
        try {
            int idProveedor = crudProveedor.createProveedor(proveedor);
            proveedor.setId(idProveedor);
            viewProveedor.addTableProveedor(proveedor);
            viewProveedor.ShowMessage( "CORRECTO", "Proveedor " + proveedor.getNombre_proveedor() + " agregado con exito");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            viewProveedor.ShowErrorMessage("No se ha podido agregar el registro", "ERROR");
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

    public boolean updateProveedor(Proveedor proveedor) {
        boolean result = crudProveedor.updateProveedor(proveedor);
        if (result){
            viewProveedor.updateTableProveedor(proveedor);
            viewProveedor.ShowMessage("CORRECTO","El proveedor " + proveedor.getNombre_proveedor() + " ha sido actualizado");
        }else{
            viewProveedor.ShowErrorMessage("ERROR", "No se ha podiddo actualizar el proveedor " + proveedor.getNombre_proveedor());
        }
        return result;
    }

    public boolean deleteProveedor(int cod){
        boolean result = crudProveedor.deleteProveedor(cod);
        if (result){
            viewProveedor.ShowMessage("El material con codigo: " + cod + " ha sido borrado", "CORRECTO");
        }else{
            viewProveedor.ShowErrorMessage("El material con codigo: " + cod + " no se ha podido borrar", "ERROR");
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
}
