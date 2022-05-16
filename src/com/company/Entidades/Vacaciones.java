package com.company.Entidades;

import java.sql.Date;

public class Vacaciones {

    //region SETTER AND GETTER
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public java.sql.Date getFecha_solicitada_inicio() {
        return fecha_solicitada_inicio;
    }

    public void setFecha_solicitada_inicio(Date fecha_solicitada_inicio) {
        this.fecha_solicitada_inicio = fecha_solicitada_inicio;
    }

    public java.sql.Date getFecha_solicitada_fin() {
        return fecha_solicitada_fin;
    }

    public void setFecha_solicitada_fin(Date fecha_solicitada_fin) {
        this.fecha_solicitada_fin = fecha_solicitada_fin;
    }

    public java.sql.Date getFecha_aprobada_inicio() {
        return fecha_aprobada_inicio;
    }

    public void setFecha_aprobada_inicio(Date fecha_aprobada_inicio) {
        this.fecha_aprobada_inicio = fecha_aprobada_inicio;
    }

    public java.sql.Date getFecha_aprobada_fin() {
        return fecha_aprobada_fin;
    }

    public void setFecha_aprobada_fin(Date fecha_aprobada_fin) {
        this.fecha_aprobada_fin = fecha_aprobada_fin;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    //endregion

    @Override
    public String toString() {
        return "Vacaciones{" +
                "id=" + id +
                ", idTrabajador=" + idTrabajador +
                ", fecha_solicitada_inicio=" + fecha_solicitada_inicio +
                ", fecha_solicitada_fin=" + fecha_solicitada_fin +
                ", fecha_aprobada_inicio=" + fecha_aprobada_inicio +
                ", fecha_aprobada_fin=" + fecha_aprobada_fin +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }

    //region ATRIBUTOS

    private  int id;
    private int idTrabajador;
    private Date fecha_solicitada_inicio;
    private Date fecha_solicitada_fin;
    private Date fecha_aprobada_inicio;
    private Date fecha_aprobada_fin;
    private String observaciones;

    //endregion
}
