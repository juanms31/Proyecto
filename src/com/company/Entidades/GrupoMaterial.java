package com.company.Entidades;

public class GrupoMaterial {

    //region SETTER AND GETTER

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSiglasGrupo() {
        return siglasGrupo;
    }

    public void setSiglasGrupo(String siglasGrupo) {
        this.siglasGrupo = siglasGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    //endregion

    //region Metodos publicos

    @Override
    public String toString() {
        return "Grupo{" +
                "id=" + id +
                ", siglasGrupo='" + siglasGrupo + '\'' +
                ", nombreGrupo='" + nombreGrupo + '\'' +
                ", Descripcion='" + Descripcion + '\'' +
                '}';
    }

    //endregion

    //region ATRIBUTOS

    private int id;
    private String siglasGrupo;
    private String nombreGrupo;
    private String Descripcion;

    //endregion
}
