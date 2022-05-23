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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellido1) {
        this.apellidos = apellido1;
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

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    //endregion

    //region METODOS PUBLICOS

    @Override
    public String toString() {
        return "Trabajador{" +
                "id=" + id +
                ", fnac=" + fnac +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", nombre='" + nombre + '\'' +
                ", puesto='" + puesto + '\'' +
                ", salario=" + salario +
                '}';
    }


    //endregion

    //region ATRIBUTOS

    private int id;

    private String DNI;
    private Date fnac;
    private String nacionalidad;
    private String apellidos;
    private String nombre;
    private String puesto;
    private double salario;

    //endregion
}
