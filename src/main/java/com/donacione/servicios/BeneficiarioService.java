
package com.donacione.servicios;

import com.donaciones.model.Beneficiario;
import com.donaciones.repository.BeneficiarioRepository;
import java.sql.SQLException;
import java.util.List;

public class BeneficiarioService {

    private BeneficiarioRepository beneficiarioRepository;

    public BeneficiarioService() {
        this.beneficiarioRepository = new BeneficiarioRepository();
    }

    
    public void registrarBeneficiario(Beneficiario beneficiario) throws IllegalArgumentException, SQLException {
        if (beneficiario == null) {
            throw new IllegalArgumentException("El objeto Beneficiario no puede ser nulo.");
        }
        if (beneficiario.getNombre() == null || beneficiario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del beneficiario no puede estar vacío.");
        }
        if (beneficiario.getApellido() == null || beneficiario.getApellido().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido del beneficiario no puede estar vacío.");
        }
        

        beneficiarioRepository.insert(beneficiario);
        System.out.println("Beneficiario '" + beneficiario.getNombre() + " " + beneficiario.getApellido() + "' registrado con éxito. ID: " + beneficiario.getIdBeneficiario());
    }

    
    public Beneficiario buscarBeneficiarioPorId(int id) throws IllegalArgumentException, SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de beneficiario inválido. Debe ser mayor que cero.");
        }
        return beneficiarioRepository.findById(id);
    }

    
    public List<Beneficiario> obtenerTodosLosBeneficiarios() throws SQLException {
        return beneficiarioRepository.findAll();
    }

    
    public void actualizarBeneficiario(Beneficiario beneficiario) throws IllegalArgumentException, SQLException {
        if (beneficiario == null) {
            throw new IllegalArgumentException("El objeto Beneficiario no puede ser nulo para la actualización.");
        }
        if (beneficiario.getIdBeneficiario() <= 0) {
            throw new IllegalArgumentException("ID de beneficiario inválido para actualización.");
        }
        if (beneficiario.getNombre() == null || beneficiario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del beneficiario no puede estar vacío.");
        }
        if (beneficiario.getApellido() == null || beneficiario.getApellido().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido del beneficiario no puede estar vacío.");
        }

        
        if (beneficiarioRepository.findById(beneficiario.getIdBeneficiario()) == null) {
            throw new IllegalArgumentException("El beneficiario con ID " + beneficiario.getIdBeneficiario() + " no existe y no puede ser actualizado.");
        }

        beneficiarioRepository.update(beneficiario);
        System.out.println("Beneficiario con ID " + beneficiario.getIdBeneficiario() + " actualizado con éxito.");
    }

   
    public void eliminarBeneficiario(int id) throws IllegalArgumentException, SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de beneficiario inválido para eliminación.");
        }
        

        if (beneficiarioRepository.findById(id) == null) {
            throw new IllegalArgumentException("El beneficiario con ID " + id + " no existe y no puede ser eliminado.");
        }

        beneficiarioRepository.delete(id);
        System.out.println("Beneficiario con ID " + id + " eliminado con éxito.");
    }
}