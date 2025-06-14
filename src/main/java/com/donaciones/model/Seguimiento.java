package com.donaciones.model;

import java.util.Date;

public class Seguimiento {
    private int idSeguimiento;
    private int idEntrega; 
    private String observaciones;
    private Date fechaSeguimiento;

    public Seguimiento() {
    }

    public Seguimiento(int idSeguimiento, int idEntrega, String observaciones, Date fechaSeguimiento) {
        this.idSeguimiento = idSeguimiento;
        this.idEntrega = idEntrega;
        this.observaciones = observaciones;
        this.fechaSeguimiento = fechaSeguimiento;
    }

    public int getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(int idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechaSeguimiento() {
        return fechaSeguimiento;
    }

    public void setFechaSeguimiento(Date fechaSeguimiento) {
        this.fechaSeguimiento = fechaSeguimiento;
    }

    @Override
    public String toString() {
        return "Seguimiento{" +
               "idSeguimiento=" + idSeguimiento +
               ", idEntrega=" + idEntrega +
               ", observaciones='" + observaciones + '\'' +
               ", fechaSeguimiento=" + fechaSeguimiento +
               '}';
    }
}
