package com.donaciones.model;

import java.math.BigDecimal;
import java.util.Date;

public class Entrega {
    private int idEntrega;
    private int idAsignacion; // Clave foránea
    private int idBeneficiario; // Clave foránea
    private BigDecimal cantidadEntregada;
    private Date fechaEntrega;

  
    public Entrega() {
    }

   
    public Entrega(int idEntrega, int idAsignacion, int idBeneficiario, BigDecimal cantidadEntregada, Date fechaEntrega) {
        this.idEntrega = idEntrega;
        this.idAsignacion = idAsignacion;
        this.idBeneficiario = idBeneficiario;
        this.cantidadEntregada = cantidadEntregada;
        this.fechaEntrega = fechaEntrega;
    }


    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }

    public int getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(int idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public int getIdBeneficiario() {
        return idBeneficiario;
    }

    public void setIdBeneficiario(int idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    public BigDecimal getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(BigDecimal cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    @Override
    public String toString() {
        return "Entrega{" +
               "idEntrega=" + idEntrega +
               ", idAsignacion=" + idAsignacion +
               ", idBeneficiario=" + idBeneficiario +
               ", cantidadEntregada=" + cantidadEntregada +
               ", fechaEntrega=" + fechaEntrega +
               '}';
    }
}