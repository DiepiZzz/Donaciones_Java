package com.donaciones.model;

public class InstitucionBenefica {
    private int idInstitucion;
    private String nombre;
    private String direccion;
    private String contacto;
    private String telefono;

    public InstitucionBenefica() {
    }

    public InstitucionBenefica(int idInstitucion, String nombre, String direccion, String contacto, String telefono) {
        this.idInstitucion = idInstitucion;
        this.nombre = nombre;
        this.direccion = direccion;
        this.contacto = contacto;
        this.telefono = telefono;
    }

    public int getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(int idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "InstitucionBenefica{" +
               "idInstitucion=" + idInstitucion +
               ", nombre='" + nombre + '\'' +
               ", direccion='" + direccion + '\'' +
               ", contacto='" + contacto + '\'' +
               ", telefono='" + telefono + '\'' +
               '}';
    }
}