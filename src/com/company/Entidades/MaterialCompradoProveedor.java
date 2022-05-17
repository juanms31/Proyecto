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

    public int getId_material() {
        return id_material;
    }

    public void setId_material(int id_material) {
        this.id_material = id_material;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public int getId_actuacion() {
        return id_actuacion;
    }

    public void setId_actuacion(int id_actuacion) {
        this.id_actuacion = id_actuacion;
    }

    public int getId_albaran() {
        return id_albaran;
    }

    public void setId_albaran(int id_albaran) {
        this.id_albaran = id_albaran;
    }

    public Date getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(Date fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    //endregion

    //region Metodos publicos

    @Override
    public String toString() {
        return "MaterialCompradoProveedor{" +
                "id=" + id +
                ", id_material=" + id_material +
                ", id_proveedor=" + id_proveedor +
                ", id_actuacion=" + id_actuacion +
                ", id_albaran=" + id_albaran +
                ", fecha_compra=" + fecha_compra +
                '}';
    }

    //endregion

    //region ATRIBUTOS

    private int id;
    private int id_material;
    private int id_proveedor;
    private int id_actuacion;
    private int id_albaran;
    private Date fecha_compra;

    //endregion
}
