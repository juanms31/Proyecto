package com.company.Entidades;

public class MOInstalacionMaterial {

    //region SETTER AND GETTER
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getNombreProveedor1() {
        return nombreProveedor1;
    }

    public void setNombreProveedor1(String nombreProveedor1) {
        this.nombreProveedor1 = nombreProveedor1;
    }

    public String getNombreProveedor2() {
        return nombreProveedor2;
    }

    public void setNombreProveedor2(String nombreProveedor2) {
        this.nombreProveedor2 = nombreProveedor2;
    }

    public double getPrecio1() {
        return precio1;
    }

    public void setPrecio1(double precio1) {
        this.precio1 = precio1;
    }

    public double getPrecio2() {
        return precio2;
    }

    public void setPrecio2(double precio2) {
        this.precio2 = precio2;
    }

    //endregion

    //region METODOS PUBLICOS

    @Override
    public String toString() {
        return "MOInstalacionMaterial{" +
                "id=" + id +
                ", idMaterial=" + idMaterial +
                ", nombreProveedor1='" + nombreProveedor1 + '\'' +
                ", nombreProveedor2='" + nombreProveedor2 + '\'' +
                ", precio1=" + precio1 +
                ", precio2=" + precio2 +
                '}';
    }

    //endregion

    //region ATRIBUTOS

    private int id;
    private int idMaterial;
    private String nombreProveedor1;
    private String nombreProveedor2;
    private double precio1;
    private double precio2;

    //endregion
}
