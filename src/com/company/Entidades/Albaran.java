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

    public ArrayList<Material> getMateriales() {
        return materiales;
    }

    public void setMateriales(ArrayList<Material> materiales) {
        this.materiales = materiales;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Date getFechaEntradaAlbaran() {
        return fechaEntradaAlbaran;
    }

    public void setFechaEntradaAlbaran(Date fechaEntradaAlbaran) {
        this.fechaEntradaAlbaran = fechaEntradaAlbaran;
    }

    public int getId_actuacion() {
        return id_actuacion;
    }

    public void setId_actuacion(int id_actuacion) {
        this.id_actuacion = id_actuacion;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    @Override
    public String toString() {
        return "Albaran{" +
                "id=" + id +
                ", cod='" + cod + '\'' +
                ", actuacion=" + actuacion +
                ", proveedor=" + proveedor +
                ", id_actuacion=" + id_actuacion +
                ", id_proveedor=" + id_proveedor +
                ", concepto='" + concepto + '\'' +
                ", fechaEntradaAlbaran=" + fechaEntradaAlbaran +
                ", materiales=" + materiales +
                '}';
    }

    //endregion


    //region ATRIBUTOS

    private int id;
    private String cod;
    private Actuacion actuacion;
    private Proveedor proveedor;
    private int id_actuacion;
    private int id_proveedor;
    private String concepto;
    private Date fechaEntradaAlbaran;
    private ArrayList<Material> materiales;

    //endregion
}
