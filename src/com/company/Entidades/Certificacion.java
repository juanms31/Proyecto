package com.company.Entidades;

import java.sql.Date;

public class Certificacion {

    //region SETTER AND GETTER

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getFecha_certificacion() {
        return fecha_certificacion;
    }

    public void setFecha_certificacion(Date fecha_certificacion) {
        this.fecha_certificacion = fecha_certificacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Actuacion getActuacion() {
        return actuacion;
    }

    public void setActuacion(Actuacion actuacion) {
        this.actuacion = actuacion;
    }

    //endregion

    //region Metodos publicos

    @Override
    public String toString() {
        return "Certificacion{" +
                "id=" + id +
                ", valor=" + valor +
                ", fecha_certificacion=" + fecha_certificacion +
                ", observaciones='" + observaciones + '\'' +
                ", actuacion=" + actuacion +
                '}';
    }


    //endregion

    //region ATRIBUTOS
    private int id;
    private double valor;
    private Date fecha_certificacion;
    private String observaciones;
    private Actuacion actuacion;

    //endregion

}
