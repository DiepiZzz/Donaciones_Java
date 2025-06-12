
package com.donaciones.model;

import java.math.BigDecimal;
import java.util.Date;

public class Asignacion {
    private int idAsignacion;
    private int idDonacion; 
    private int idInstitucion; 
    private BigDecimal cantidadAsignada;
    private Date fechaAsignacion;

    
    public Asignacion() {
    }

  
    public Asignacion(int idAsignacion, int idDonacion, int idInstitucion, BigDecimal cantidadAsignada, Date fechaAsignacion) {
        this.idAsignacion = idAsignacion;
        this.idDonacion = idDonacion;
        this.idInstitucion = idInstitucion;
        this.cantidadAsignada = cantidadAsignada;
        this.fechaAsignacion = fechaAsignacion;
    }

  
    public int getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(int idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public int getIdDonacion() {
        return idDonacion;
    }

    public void setIdDonacion(int idDonacion) {
        this.idDonacion = idDonacion;
    }

    public int getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(int idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public BigDecimal getCantidadAsignada() {
        return cantidadAsignada;
    }

    public void setCantidadAsignada(BigDecimal cantidadAsignada) {
        this.cantidadAsignada = cantidadAsignada;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    @Override
    public String toString() {
        return "Asignacion{" +
               "idAsignacion=" + idAsignacion +
               ", idDonacion=" + idDonacion +
               ", idInstitucion=" + idInstitucion +
               ", cantidadAsignada=" + cantidadAsignada +
               ", fechaAsignacion=" + fechaAsignacion +
               '}';
    }
}
