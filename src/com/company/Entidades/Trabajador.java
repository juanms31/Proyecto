package com.company.Entidades;

import java.sql.Date;

public class Trabajador {

    //region SETTER AND GETTER
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public java.sql.Date getFnac() {
        return fnac;
    }

    public void setFnac(Date fnac) {
        this.fnac = fnac;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    //endregion

    //region METODOS PUBLICOS

    @Override
    public String toString() {
        return "Trabajador{" +
                "id=" + id +
                ", fnac=" + fnac +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", apellido1='" + apellido1 + '\'' +
                ", apellido2='" + apellido2 + '\'' +
                ", nombre='" + nombre + '\'' +
                ", puesto='" + puesto + '\'' +
                ", salario=" + salario +
                '}';
    }

    //endregion

    //region ATRIBUTOS

    private int id;
    private Date fnac;
    private String nacionalidad;
    private String apellido1;
    private String apellido2;
    private String nombre;
    private String puesto;
    private double salario;

    //endregion
}
