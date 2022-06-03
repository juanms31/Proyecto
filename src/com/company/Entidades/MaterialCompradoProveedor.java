package com.company.Entidades;

import java.sql.Date;

public class MaterialCompradoProveedor {

    //region SETTER AND GETTER

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Actuacion getActuacion() {
        return actuacion;
    }

    public void setActuacion(Actuacion actuacion) {
        this.actuacion = actuacion;
    }

    public Albaran getAlbaran() {
        return albaran;
    }

    public void setAlbaran(Albaran albaran) {
        this.albaran = albaran;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public double getPrecioUnidad() {
        return PrecioUnidad;
    }

    public void setPrecioUnidad(double precioUnidad) {
        PrecioUnidad = precioUnidad;
    }

    public double getBaseImponible() {
        return BaseImponible;
    }

    public void setBaseImponible(double baseImponible) {
        BaseImponible = baseImponible;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    //endregion

    //region Metodos publicos

    @Override
    public String toString() {
        return "MaterialCompradoProveedor{" +
                "id=" + id +
                ", material=" + material +
                ", proveedor=" + proveedor +
                ", actuacion=" + actuacion +
                ", albaran=" + albaran +
                ", unidades=" + unidades +
                ", PrecioUnidad=" + PrecioUnidad +
                ", BaseImponible=" + BaseImponible +
                '}';
    }


    //endregion

    //region ATRIBUTOS

    private int id;
    private Material material;
    private Proveedor proveedor;
    private Actuacion actuacion;
    private Albaran albaran;
    private int unidades;
    private double PrecioUnidad;
    private double BaseImponible;

    //endregion
}
