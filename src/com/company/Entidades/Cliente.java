package com.company.Entidades;

public class Cliente {

    // region Atributos de clase
    private int id;
    private String CIF;
    private String nombre;
    private String direccion;
    private String mail1;
    private String mail2;
    private String telef1;
    private String telef2;

    // endregion

    // region Getter y Setter

    public String getCIF() {
        return CIF;
    }

    public void setCIF(String CIF) {
        this.CIF = CIF;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMail1() {
        return mail1;
    }

    public void setMail1(String mail1) {
        this.mail1 = mail1;
    }

    public String getMail2() {
        return mail2;
    }

    public void setMail2(String mail2) {
        this.mail2 = mail2;
    }

    public String getTelef1() {
        return telef1;
    }

    public void setTelef1(String telef1) {
        this.telef1 = telef1;
    }

    public String getTelef2() {
        return telef2;
    }

    public void setTelef2(String telef2) {
        this.telef2 = telef2;
    }

    //endregion

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", mail1='" + mail1 + '\'' +
                ", mail2='" + mail2 + '\'' +
                ", telef1='" + telef1 + '\'' +
                ", telef2='" + telef2 + '\'' +
                '}';
    }
}
