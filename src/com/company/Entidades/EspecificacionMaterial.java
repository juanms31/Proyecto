package com.company.Entidades;

public class EspecificacionMaterial {

    //region SETTER AND GETTER

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSiglasEspecificacion() {
        return siglasEspecificacion;
    }

    public void setSiglasEspecificacion(String siglasEspecificacion) {
        this.siglasEspecificacion = siglasEspecificacion;
    }

    public String getNombreEspecificacion() {
        return nombreEspecificacion;
    }

    public void setNombreEspecificacion(String nombreEspecificacion) {
        this.nombreEspecificacion = nombreEspecificacion;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    //endregion

    //region ATRIBUTOS

    private int id;
    private String siglasEspecificacion;
    private String nombreEspecificacion;
    private String Descripcion;

    //endregion
}
