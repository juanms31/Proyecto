package com.company.Controlador;

import com.company.BaseDatos.CRUDMaterial;
import com.company.Entidades.Material;
import com.company.Vistas.ViewMaterial;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

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
    public boolean createMaterial(){
        return false;
    }

    public Material readMaterial(String cod){
        Material material = crudMaterial.readMaterial(cod);
        if (material.getCodigo() == null || material.getCodigo().isEmpty()){
            viewMaterial.ErroMessage("No se ha podido cargar el material con codigo: " + cod);
            //TODO en principio se puede leer el material sin consultar a la bbdd ya que esta cargado en memoria
        }else{
            viewMaterial.ShowMessage("Correcto, cargando material...");
            //TODO quizas este mensaje sobre y no sea necesario
        }
        return  material;
    }

    public boolean updateMaterial(Material material) {
        boolean result = crudMaterial.updateMaterial(material);
        if (result){
            viewMaterial.updateTableMaterial(material);
            viewMaterial.ShowMessage("El material con codigo: " + material.getCodigo() + " ha sido actualizado");
        }else{
            viewMaterial.ErroMessage("No se ha podiddo actualizar material con el codigo: " + material.getCodigo());
        }
        return result;
    }

    public boolean deleteMaterial(int cod){
        boolean result = crudMaterial.deleteMaterial(cod);
        if (result){
            viewMaterial.ShowMessage("El material con codigo: " + cod + " ha sido borrado");
        }else{
            viewMaterial.ErroMessage("El material con codigo: " + cod + " no se ha podido borrar");
        }
        return result;
    }

    public void sendMaterialFromModel(Material material) {
        try {
            int idMaterial = crudMaterial.createMaterial(material);
            material.setId(idMaterial);
            viewMaterial.updateTableMaterial(material);
            viewMaterial.ShowMessage("material con id " + idMaterial + " agregado con exito");
        } catch (SQLException e) {
            e.printStackTrace();
            viewMaterial.ErroMessage("No se ha podido agregar el registro");
        }
    }

    //endregion
}
