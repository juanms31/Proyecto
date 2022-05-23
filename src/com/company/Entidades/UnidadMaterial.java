package com.company.Entidades;

public class UnidadMaterial {

    //region SETTER AND GETTER

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSiglasUnidad() {
        return siglasUnidad;
    }

    public void setSiglasUnidad(String siglasUnidad) {
        this.siglasUnidad = siglasUnidad;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    //endregion

    //region Metodos publicos

    @Override
    public String toString() {
        return "UnidadMaterial{" +
                "id=" + id +
                ", siglasUnidad='" + siglasUnidad + '\'' +
                ", nombreUnidad='" + nombreUnidad + '\'' +
                ", Descripcion='" + Descripcion + '\'' +
                '}';
    }

    //endregion

    //region ATRIBUTOS

    private int id;
    private String siglasUnidad;
    private String nombreUnidad;
    private String Descripcion;

    //endregion
}
