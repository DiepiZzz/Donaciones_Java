
package com.donaciones.model;

public class Beneficiario {
    private int idBeneficiario;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;

 
    public Beneficiario() {
    }

    
    public Beneficiario(int idBeneficiario, String nombre, String apellido, String direccion, String telefono) {
        this.idBeneficiario = idBeneficiario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
    }

   
    public int getIdBeneficiario() {
        return idBeneficiario;
    }

    public void setIdBeneficiario(int idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Beneficiario{" +
               "idBeneficiario=" + idBeneficiario +
               ", nombre='" + nombre + '\'' +
               ", apellido='" + apellido + '\'' +
               ", direccion='" + direccion + '\'' +
               ", telefono='" + telefono + '\'' +
               '}';
    }
}
