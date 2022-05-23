package com.company.Controlador;

import com.company.BaseDatos.CRUDMaterial;
import com.company.Entidades.Material;
import com.company.Vistas.ViewMaterial;

import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorMaterial {

    private CRUDMaterial crudMaterial;
    private ViewMaterial viewMaterial;

    //Constructor
    public ControladorMaterial() {
        crudMaterial = new CRUDMaterial(this);
        ArrayList<Material> materiales = crudMaterial.getAll();
        viewMaterial = new ViewMaterial(this, materiales);

    }

    //region CRUD
    public boolean createMaterial(Material material){
        boolean estado = false;
        try {
            int idMaterial = crudMaterial.createMaterial(material);
            material.setId(idMaterial);
            material.setCodigo(material.getGrupo()+idMaterial);
            boolean result = crudMaterial.updateMaterial(material);
            if (result){
                viewMaterial.ShowMessage("CORRECTO", "Material con codigo " + material.getCodigo() + " agregado con exito");
                estado = true;
            }
            else{
                material.setCodigo("NA");
                viewMaterial.ShowMessage("CORRECTO", "Material con id " + material.getId() + " agregado con exito");
                estado = true;
            }
            viewMaterial.addTableMaterial(material);
        } catch (SQLException e) {
            e.printStackTrace();
            viewMaterial.ShowErrorMessage("ERROR","No se ha podido agregar el registro" );
            estado = false;
        }
        return estado;
    }

    public Material readMaterial(String cod){
        Material material = crudMaterial.readMaterial(cod);
        if (material.getCodigo() == null || material.getCodigo().isEmpty()){
            viewMaterial.ShowMessage("No se ha podido cargar el material con codigo: " + cod, "CORRECTO");
            //TODO en principio se puede leer el material sin consultar a la bbdd ya que esta cargado en memoria
        }else{
            viewMaterial.ShowMessage("Correcto, cargando material...", "CORRECTO");
            //TODO quizas este mensaje sobre y no sea necesario
        }
        return  material;
    }

    public boolean updateMaterial(Material material) {
        boolean result = crudMaterial.updateMaterial(material);
        if (result){
            viewMaterial.updateTableMaterial(material);
            viewMaterial.ShowMessage( "CORRECTO", "El material con codigo: " + material.getCodigo() + " ha sido actualizado");
        }else{
            viewMaterial.ShowErrorMessage( "ERROR", "No se ha podiddo actualizar material con el codigo: " + material.getCodigo());
        }
        return result;
    }

    public boolean deleteMaterial(String id){
        boolean result = crudMaterial.deleteMaterial(id);
        if (result){
            viewMaterial.ShowMessage("CORRECTO","El material con codigo: " + id + " ha sido borrado");
        }else{
            viewMaterial.ShowErrorMessage( "ERROR","El material con codigo: " + id + " no se ha podido borrar");
        }
        return result;
    }

    //endregion

    // region MetaDatos

    public String[] getColumnsName(){
        String[] listColumnsName = crudMaterial.getColumnsMaterial();
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
