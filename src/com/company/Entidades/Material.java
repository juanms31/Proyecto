package com.company.Entidades;

public class Material {

    //region <Atributos de clase>

    private int id;
    private String codigo;
    private String grupo;
    private String descripcion;
    private String especificacion;
    private String unidad;
    private double espesor;
    private String calidad;
    private String proveedor1;
    private double precio1;
    private String proveedor2;
    private double precio2;
    private String proveedor3;
    private double precio3;
    private Integer idGrupo;
    private Integer idEspecifiacion;
    private Integer idUnidad;

    //endregion

    //region <Setter and Getter>
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEspecificacion() {
        return especificacion;
    }

    public void setEspecificacion(String especificacion) {
        this.especificacion = especificacion;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public double getEspesor() {
        return espesor;
    }

    public void setEspesor(double espesor) {
        this.espesor = espesor;
    }

    public String getCalidad() {
        return calidad;
    }

    public void setCalidad(String calidad) {
        this.calidad = calidad;
    }

    public String getProveedor1() {
        return proveedor1;
    }

    public void setProveedor1(String proveedor1) {
        this.proveedor1 = proveedor1;
    }

    public double getPrecio1() {
        return precio1;
    }

    public void setPrecio1(double precio1) {
        this.precio1 = precio1;
    }

    public String getProveedor2() {
        return proveedor2;
    }

    public void setProveedor2(String proveedor2) {
        this.proveedor2 = proveedor2;
    }

    public double getPrecio2() {
        return precio2;
    }

    public void setPrecio2(double precio2) {
        this.precio2 = precio2;
    }

    public String getProveedor3() {
        return proveedor3;
    }

    public void setProveedor3(String proveedor3) {
        this.proveedor3 = proveedor3;
    }

    public double getPrecio3() {
        return precio3;
    }

    public void setPrecio3(double precio3) {
        this.precio3 = precio3;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Integer getIdEspecifiacion() {
        return idEspecifiacion;
    }

    public void setIdEspecifiacion(Integer idEspecifiacion) {
        this.idEspecifiacion = idEspecifiacion;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    //endregion

    //region Metodos Publicos

    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", grupo='" + grupo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", especificacion='" + especificacion + '\'' +
                ", unidad='" + unidad + '\'' +
                ", espesor=" + espesor +
                ", calidad='" + calidad + '\'' +
                ", proveedor1='" + proveedor1 + '\'' +
                ", precio1=" + precio1 +
                ", proveedor2='" + proveedor2 + '\'' +
                ", precio2=" + precio2 +
                ", proveedor3='" + proveedor3 + '\'' +
                ", precio3=" + precio3 +
                '}';
    }

    //endregion
}
