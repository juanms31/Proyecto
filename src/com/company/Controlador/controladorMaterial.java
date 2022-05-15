package com.company.Controlador;

import com.company.BaseDatos.CRUDMaterial;
import com.company.Entidades.Material;
import com.company.Formularios.formMaterial;

import java.sql.SQLException;

public class controladorMaterial {

    private CRUDMaterial crudMaterial;
    private formMaterial formMaterial;

    //Constructor
    public controladorMaterial() {

        crudMaterial = new CRUDMaterial();
        formMaterial = new formMaterial();
    }

    //region CRUD
    public boolean createMaterial(){
        try {
            crudMaterial.createMaterial(formMaterial.createMaterial());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean readMaterial(int cod){

        formMaterial.readMaterial(crudMaterial.readMaterial(cod));

        return false;
    }

    public boolean updateMaterial() {
        crudMaterial.updateMaterial(formMaterial.updateMaterial());
        return false;
    }

    public boolean deleteMaterial(int cod){

        crudMaterial.deleteMaterial(cod);
        return false;
    }

    //endregion
}
