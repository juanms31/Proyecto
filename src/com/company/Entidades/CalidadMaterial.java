package com.company.Entidades;

public class CalidadMaterial {

    //region SETTER AND GETTER

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSiglasCalidad() {
        return siglasCalidad;
    }

    public void setSiglasCalidad(String siglasCalidad) {
        this.siglasCalidad = siglasCalidad;
    }

    public String getNombreCalidad() {
        return nombreCalidad;
    }

    public void setNombreCalidad(String nombreCalidad) {
        this.nombreCalidad = nombreCalidad;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    //endregion

    //region Metodos Publicos

    @Override
    public String toString() {
        return "CalidadMaterial{" +
                "id=" + id +
                ", siglasCalidad='" + siglasCalidad + '\'' +
                ", nombreCalidad='" + nombreCalidad + '\'' +
                ", Descripcion='" + Descripcion + '\'' +
                '}';
    }


    //endregion

    //region ATRIBUTOS

    private int id;
    private String siglasCalidad;
    private String nombreCalidad;
    private String Descripcion;

    //endregion
}
