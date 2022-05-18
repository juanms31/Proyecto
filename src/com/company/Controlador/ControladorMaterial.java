package com.company.Controlador;

import com.company.BaseDatos.CRUDMaterial;
import com.company.Entidades.Material;
import com.company.Vistas.ViewMaterial;

import java.sql.SQLException;

public class ControladorMaterial {

    private CRUDMaterial crudMaterial;
    private ViewMaterial viewMaterial;

    //Constructor
    public ControladorMaterial() {

        crudMaterial = new CRUDMaterial(this);
        viewMaterial = new ViewMaterial(this);

    }

    //region CRUD
    public boolean createMaterial(){
        return false;
    }

    public boolean readMaterial(int cod){

        //formMaterial.readMaterial(crudMaterial.readMaterial(cod));

        return false;
    }

    public boolean updateMaterial() {
        //crudMaterial.updateMaterial(formMaterial.updateMaterial());
        return false;
    }

    public boolean deleteMaterial(int cod){

        crudMaterial.deleteMaterial(cod);
        return false;
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
