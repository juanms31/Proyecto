package com.company.Entidades;

public class MaterialUtilizadoActuacion {

    //region SETTER AND GETTER

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_material() {
        return id_material;
    }

    public void setId_material(int id_material) {
        this.id_material = id_material;
    }

    public int getId_actuacion() {
        return id_actuacion;
    }

    public void setId_actuacion(int id_actuacion) {
        this.id_actuacion = id_actuacion;
    }

    //endregion

    //region Metodos publicos

    @Override
    public String toString() {
        return "MaterialUtilizadoActuacion{" +
                "id=" + id +
                ", id_material=" + id_material +
                ", id_actuacion=" + id_actuacion +
                '}';
    }

    //endregion

    //region ATRIBUTOS

    private int id;
    private int id_material;
    private int id_actuacion;

    //endregion
}
