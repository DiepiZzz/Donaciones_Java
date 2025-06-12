
package com.donaciones.model;

import java.math.BigDecimal;
import java.util.Date;

public class Donacion {
    private int idDonacion;
    private String tipoDonacion;
    private BigDecimal cantidad; 
    private Date fechaDonacion; 
    private int idDonante; 

    
    public Donacion() {
    }

    
    public Donacion(int idDonacion, String tipoDonacion, BigDecimal cantidad, Date fechaDonacion, int idDonante) {
        this.idDonacion = idDonacion;
        this.tipoDonacion = tipoDonacion;
        this.cantidad = cantidad;
        this.fechaDonacion = fechaDonacion;
        this.idDonante = idDonante;
    }

    
    public int getIdDonacion() {
        return idDonacion;
    }

    public void setIdDonacion(int idDonacion) {
        this.idDonacion = idDonacion;
    }

    public String getTipoDonacion() {
        return tipoDonacion;
    }

    public void setTipoDonacion(String tipoDonacion) {
        this.tipoDonacion = tipoDonacion;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaDonacion() {
        return fechaDonacion;
    }

    public void setFechaDonacion(Date fechaDonacion) {
        this.fechaDonacion = fechaDonacion;
    }

    public int getIdDonante() {
        return idDonante;
    }

    public void setIdDonante(int idDonante) {
        this.idDonante = idDonante;
    }

    @Override
    public String toString() {
        return "Donacion{" +
               "idDonacion=" + idDonacion +
               ", tipoDonacion='" + tipoDonacion + '\'' +
               ", cantidad=" + cantidad +
               ", fechaDonacion=" + fechaDonacion +
               ", idDonante=" + idDonante +
               '}';
    }
}