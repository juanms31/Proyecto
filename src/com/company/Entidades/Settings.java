package com.company.Entidades;

public class Settings {

    public String getUrlFondo() {
        return urlFondo;
    }

    public void setUrlFondo(String urlFondo) {
        this.urlFondo = urlFondo;
    }

    public String getTipoFondo() {
        return tipoFondo;
    }

    public void setTipoFondo(String tipoFondo) {
        this.tipoFondo = tipoFondo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    private int id;
    private String urlFondo;
    private String tipoFondo;
    private int id_usuario;

}
