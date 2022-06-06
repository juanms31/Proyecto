package com.company.Controlador;

import com.company.BaseDatos.*;
import com.company.Entidades.*;
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
        ArrayList<Proveedor> proveedores = getProveedores();
        ArrayList<GrupoMaterial> grupoMateriales = getGruposMaterial();
        ArrayList<EspecificacionMaterial> especificacionMateriales = getEspecifiacionMaterial();
        ArrayList<UnidadMaterial> unidadMateriales = getUnidadMaterial();
        ArrayList<CalidadMaterial> calidadMateriales = getCalidadMaterial();
        viewMaterial = new ViewMaterial(this, materiales, proveedores, grupoMateriales, especificacionMateriales,
                unidadMateriales, calidadMateriales);

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
                estado = true;
            }
            else{
                material.setCodigo("NA");
                estado = true;
            }
            viewMaterial.addTableMaterial(material);
        } catch (SQLException e) {
            e.printStackTrace();
            estado = false;
        }
        return estado;
    }

    public boolean updateMaterial(Material material) {
        boolean result = crudMaterial.updateMaterial(material);
        if (result){
            viewMaterial.updateTableMaterial(material);
        }
        return result;
    }

    public boolean deleteMaterial(String id){
        return crudMaterial.deleteMaterial(id);
    }

    //endregion

    // region MetaDatos

    public String[] getColumnsName(){
        String[] listColumnsName = crudMaterial.getColumnsMaterial();
        if (listColumnsName[0] == null){
            viewMaterial.ShowErrorMessage("Error", "No se han detectado atributos para el cliente en la BBDD. Contacte con un administrador");
        }
        if (listColumnsName[0].equals("Error en CRUD")){
            viewMaterial.ShowErrorMessage("Error", "No se han detectado atributos para el cliente en la BBDD. Contacte con un administrador");
        }
        return listColumnsName;
    }

    //endregion

    //region Parametros constructor

    private ArrayList<Proveedor> getProveedores(){
        ArrayList<Proveedor> listProveedores;
        CRUDProveedor crudProveedor = new CRUDProveedor();
        listProveedores = crudProveedor.getAll();
        return listProveedores;
    }

    private ArrayList<GrupoMaterial> getGruposMaterial(){
        ArrayList<GrupoMaterial> listGrupoMaterials;
        CRUDGrupo crudGrupo = new CRUDGrupo();
        listGrupoMaterials = crudGrupo.getAll();
        return listGrupoMaterials;
    }

    private ArrayList<EspecificacionMaterial> getEspecifiacionMaterial(){
        ArrayList<EspecificacionMaterial> listEspecificacionMaterial;
        CRUDEspecificacionMaterial crudEspecificacionMaterial = new CRUDEspecificacionMaterial();
        listEspecificacionMaterial = crudEspecificacionMaterial.getAll();
        return listEspecificacionMaterial;
    }

    private ArrayList<UnidadMaterial> getUnidadMaterial(){
        ArrayList<UnidadMaterial> listUnidadMaterial;
        CRUDUnidadMaterial crudUnidadMaterial = new CRUDUnidadMaterial();
        listUnidadMaterial = crudUnidadMaterial.getAll();
        return listUnidadMaterial;
    }

    private ArrayList<CalidadMaterial> getCalidadMaterial(){
        ArrayList<CalidadMaterial> listCalidadMaterial;
        CRUDCalidadMaterial crudCalidadMaterial = new CRUDCalidadMaterial();
        listCalidadMaterial = crudCalidadMaterial.getAll();
        return listCalidadMaterial;
    }

    //endregion
}
