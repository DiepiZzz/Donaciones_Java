package com.donaciones.model;

import java.math.BigDecimal;
import java.util.Date;

public class Inventario {
    private int idInventario;
    private String tipoAlimento;
    private BigDecimal cantidadDisponible;
    private Date fechaCaducidad;
    private int idDonacion; // Clave foránea

    public Inventario() {
    }

    public Inventario(int idInventario, String tipoAlimento, BigDecimal cantidadDisponible, Date fechaCaducidad, int idDonacion) {
        this.idInventario = idInventario;
        this.tipoAlimento = tipoAlimento;
        this.cantidadDisponible = cantidadDisponible;
        this.fechaCaducidad = fechaCaducidad;
        this.idDonacion = idDonacion;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public String getTipoAlimento() {
        return tipoAlimento;
    }

    public void setTipoAlimento(String tipoAlimento) {
        this.tipoAlimento = tipoAlimento;
    }

    public BigDecimal getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(BigDecimal cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getIdDonacion() {
        return idDonacion;
    }

    public void setIdDonacion(int idDonacion) {
        this.idDonacion = idDonacion;
    }

    @Override
    public String toString() {
        return "Inventario{" +
               "idInventario=" + idInventario +
               ", tipoAlimento='" + tipoAlimento + '\'' +
               ", cantidadDisponible=" + cantidadDisponible +
               ", fechaCaducidad=" + fechaCaducidad +
               ", idDonacion=" + idDonacion +
               '}';
    }
}