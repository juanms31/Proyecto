package com.company.Controlador;

import com.company.BaseDatos.CRUDMaterial;
import com.company.Formularios.formMaterial;
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
