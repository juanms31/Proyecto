package com.company.Entidades;

import java.sql.Date;

public class Actuacion {

    //region SETTER AND GETTER

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getEspecificacion() {
        return especificacion;
    }

    public void setEspecificacion(String especificacion) {
        this.especificacion = especificacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha_solicitud() {
        return fecha_solicitud;
    }

    public void setFecha_solicitud(Date fecha_solicitud) {
        this.fecha_solicitud = fecha_solicitud;
    }

    public Date getFecha_envio() {
        return fecha_envio;
    }

    public void setFecha_envio(Date fecha_envio) {
        this.fecha_envio = fecha_envio;
    }

    public Date getFecha_comienzo() {
        return fecha_comienzo;
    }

    public void setFecha_comienzo(Date fecha_comienzo) {
        this.fecha_comienzo = fecha_comienzo;
    }

    public Date getFecha_finalizacion() {
        return fecha_finalizacion;
    }

    public void setFecha_finalizacion(Date fecha_finalizacion) {
        this.fecha_finalizacion = fecha_finalizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getHojaPlanificacion() {
        return hojaPlanificacion;
    }

    public void setHojaPlanificacion(String hojaPlanificacion) {
        this.hojaPlanificacion = hojaPlanificacion;
    }

    public String getHojaPresupuesto() {
        return hojaPresupuesto;
    }

    public void setHojaPresupuesto(String hojaPresupuesto) {
        this.hojaPresupuesto = hojaPresupuesto;
    }

    public double getTotalCertificicaciones() {
        return totalCertificicaciones;
    }

    public void setTotalCertificicaciones(double totalCertificicaciones) {
        this.totalCertificicaciones = totalCertificicaciones;
    }

    public double getPorPertificar() {
        return porPertificar;
    }

    public void setPorPertificar(double porPertificar) {
        this.porPertificar = porPertificar;
    }

    public int getHorasOfertadas() {
        return horasOfertadas;
    }

    public void setHorasOfertadas(int horasOfertadas) {
        this.horasOfertadas = horasOfertadas;
    }

    public int getHorasEjecutadas() {
        return horasEjecutadas;
    }

    public void setHorasEjecutadas(int horasEjecutadas) {
        this.horasEjecutadas = horasEjecutadas;
    }

    public double getMaterialOfertado() {
        return materialOfertado;
    }

    public void setMaterialOfertado(double materialOfertado) {
        this.materialOfertado = materialOfertado;
    }

    public double getGastoMaterial() {
        return gastoMaterial;
    }

    public void setGastoMaterial(double gastoMaterial) {
        this.gastoMaterial = gastoMaterial;
    }

    public double getResultadoBalance() {
        return resultadoBalance;
    }

    public void setResultadoBalance(double resultadoBalance) {
        this.resultadoBalance = resultadoBalance;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    //endregion

    //region Metodos publicos

    @Override
    public String toString() {
        return "Actuacion{" +
                "cod=" + id +
                ", especificacion='" + especificacion + '\'' +
                ", estado='" + estado + '\'' +
                ", fecha_solicitud=" + fecha_solicitud +
                ", fecha_envio=" + fecha_envio +
                ", fecha_comienzo=" + fecha_comienzo +
                ", fecha_finalizacion=" + fecha_finalizacion +
                ", descripcion='" + descripcion + '\'' +
                ", importe=" + importe +
                ", hojaPlanificacion='" + hojaPlanificacion + '\'' +
                ", hojaPresupuesto='" + hojaPresupuesto + '\'' +
                ", totalCertificicaciones=" + totalCertificicaciones +
                ", porPertificar=" + porPertificar +
                ", horasOfertadas=" + horasOfertadas +
                ", horasEjecutadas=" + horasEjecutadas +
                ", materialOfertado=" + materialOfertado +
                ", gastoMaterial=" + gastoMaterial +
                ", resultadoBalance=" + resultadoBalance +
                ", idCliente=" + idCliente +
                ", cliente=" + cliente +
                '}';
    }

    //endregion

    //region ATRIBUTOS

    private int id;
    private String especificacion;
    private String estado;
    private Date fecha_solicitud;
    private Date fecha_envio;
    private Date fecha_comienzo;
    private Date fecha_finalizacion;
    private String descripcion;
    private double importe;
    private String hojaPlanificacion;
    private String hojaPresupuesto;
    private double totalCertificicaciones;
    private double porPertificar;
    private int horasOfertadas;
    private int horasEjecutadas;
    private double materialOfertado;
    private double gastoMaterial;
    private double resultadoBalance;
    private int idCliente;
    private Cliente cliente;

    //endregion
}
