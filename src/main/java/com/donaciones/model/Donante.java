
package com.donaciones.model; 

import java.util.Date; 

public class Donante {
    private int idDonante;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;

    
    public Donante() {
    }

    
    public Donante(int idDonante, String nombre, String apellido, String email, String telefono) {
        this.idDonante = idDonante;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
    }

 
    public int getIdDonante() {
        return idDonante;
    }

    public void setIdDonante(int idDonante) {
        this.idDonante = idDonante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Donante{" +
               "idDonante=" + idDonante +
               ", nombre='" + nombre + '\'' +
               ", apellido='" + apellido + '\'' +
               ", email='" + email + '\'' +
               ", telefono='" + telefono + '\'' +
               '}';
    }
}
