package com.company.Entidades;

import java.sql.Date;

public class SeguimientoLaboral {

    //region SETTER AND GETTER

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public Actuacion getActuacion() {
        return actuacion;
    }

    public void setActuacion(Actuacion actuacion) {
        this.actuacion = actuacion;
    }

    public String getHora_entrada() {
        return hora_entrada;
    }

    public void setHora_entrada(String hora_entrada) {
        this.hora_entrada = hora_entrada;
    }

    public String getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(String hora_salida) {
        this.hora_salida = hora_salida;
    }

    public int getHoras_totales() {
        return horas_totales;
    }

    public void setHoras_totales(int horas_totales) {
        this.horas_totales = horas_totales;
    }

    public int getHoras_extra() {
        return horas_extra;
    }

    public void setHoras_extra(int horas_extra) {
        this.horas_extra = horas_extra;
    }

    public int getIdActuacion() {
        return idActuacion;
    }

    public void setIdActuacion(int idActuacion) {
        this.idActuacion = idActuacion;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaCompleta() {
        return fechaCompleta;
    }

    public void setFechaCompleta(String fechaCompleta) {
        this.fechaCompleta = fechaCompleta;
    }

    //endregion

    //region Metodos publicos

    @Override
    public String toString() {
        return "SeguimientoLaboral{" +
                "cod=" + id +
                ", ano=" + ano +
                ", dia=" + dia +
                ", mes=" + mes +
                ", trabajador=" + trabajador +
                ", actuacion=" + actuacion +
                ", hora_entrada=" + hora_entrada +
                ", hora_salida=" + hora_salida +
                ", horas_totales=" + horas_totales +
                ", horas_extra=" + horas_extra +
                '}';
    }

    //endregion

    //region ATRIBUTOS

    private int id;
    private int ano;
    private int dia;
    private int mes;
    private String fechaCompleta;
    private String hora_entrada;
    private String hora_salida;
    private int horas_totales;
    private int horas_extra;
    private int idActuacion;
    private int idTrabajador;
    private Trabajador trabajador;
    private Actuacion actuacion;
    private String tipo; //para controlar la entrada y la salida

    //endregion
}
