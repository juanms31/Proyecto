package com.company.Entidades;

import java.sql.Date;
import java.util.ArrayList;

public class Albaran {

    //region SETTER AND GETTER

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Actuacion getActuacion() {
        return actuacion;
    }

    public void setActuacion(Actuacion actuacion) {
        this.actuacion = actuacion;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public Date getFechaEntradaAlbaran() {
        return fechaEntradaAlbaran;
    }

    public void setFechaEntradaAlbaran(Date fechaEntradaAlbaran) {
        this.fechaEntradaAlbaran = fechaEntradaAlbaran;
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

    public int getIdActuacion() {
        return idActuacion;
    }

    public void setIdActuacion(int idActuacion) {
        this.idActuacion = idActuacion;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public ArrayList<Material> getMateriales() {
        return materiales;
    }

    public void setMateriales(ArrayList<Material> materiales) {
        this.materiales = materiales;
    }

    //endregion

    //region Metodos publicos

    @Override
    public String toString() {
        return "Albaran{" +
                "id=" + id +
                ", actuacion=" + actuacion +
                ", proveedor=" + proveedor +
                ", concepto='" + concepto + '\'' +
                ", unidades=" + unidades +
                ", fechaEntradaAlbaran=" + fechaEntradaAlbaran +
                ", PrecioUnidad=" + PrecioUnidad +
                ", BaseImponible=" + BaseImponible +
                '}';
    }

    //endregion

    //region ATRIBUTOS

    private int id;
    private Actuacion actuacion;
    private Proveedor proveedor;
    private String concepto;
    private int unidades;
    private Date fechaEntradaAlbaran;
    private double PrecioUnidad;
    private double BaseImponible;
    private int idActuacion;
    private int idProveedor;
    private ArrayList<Material> materiales;

    //endregion
}
