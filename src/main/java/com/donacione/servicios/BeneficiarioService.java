// Archivo: com/donaciones/service/BeneficiarioService.java
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

    /**
     * Registra un nuevo beneficiario en el sistema.
     * Realiza validaciones básicas antes de persistir el objeto.
     *
     * @param beneficiario El objeto Beneficiario a registrar.
     * @throws IllegalArgumentException Si el beneficiario es nulo o sus campos son inválidos.
     * @throws SQLException Si ocurre un error durante la operación de base de datos.
     */
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
        // Puedes añadir más validaciones aquí, como formato de teléfono, dirección, etc.

        beneficiarioRepository.insert(beneficiario);
        System.out.println("Beneficiario '" + beneficiario.getNombre() + " " + beneficiario.getApellido() + "' registrado con éxito. ID: " + beneficiario.getIdBeneficiario());
    }

    /**
     * Busca un beneficiario por su ID.
     *
     * @param id El ID del beneficiario a buscar.
     * @return El objeto Beneficiario si se encuentra, o null si no existe.
     * @throws IllegalArgumentException Si el ID es inválido (menor o igual a cero).
     * @throws SQLException Si ocurre un error durante la operación de base de datos.
     */
    public Beneficiario buscarBeneficiarioPorId(int id) throws IllegalArgumentException, SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de beneficiario inválido. Debe ser mayor que cero.");
        }
        return beneficiarioRepository.findById(id);
    }

    /**
     * Obtiene una lista de todos los beneficiarios registrados.
     *
     * @return Una lista de objetos Beneficiario.
     * @throws SQLException Si ocurre un error durante la operación de base de datos.
     */
    public List<Beneficiario> obtenerTodosLosBeneficiarios() throws SQLException {
        return beneficiarioRepository.findAll();
    }

    /**
     * Actualiza la información de un beneficiario existente.
     *
     * @param beneficiario El objeto Beneficiario con la información actualizada.
     * @throws IllegalArgumentException Si el beneficiario es nulo, su ID es inválido o no existe en la BD.
     * @throws SQLException Si ocurre un error durante la operación de base de datos.
     */
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

        // Verificar si el beneficiario realmente existe antes de intentar actualizar
        if (beneficiarioRepository.findById(beneficiario.getIdBeneficiario()) == null) {
            throw new IllegalArgumentException("El beneficiario con ID " + beneficiario.getIdBeneficiario() + " no existe y no puede ser actualizado.");
        }

        beneficiarioRepository.update(beneficiario);
        System.out.println("Beneficiario con ID " + beneficiario.getIdBeneficiario() + " actualizado con éxito.");
    }

    /**
     * Elimina un beneficiario por su ID.
     *
     * @param id El ID del beneficiario a eliminar.
     * @throws IllegalArgumentException Si el ID es inválido o el beneficiario no existe.
     * @throws SQLException Si ocurre un error durante la operación de base de datos.
     */
    public void eliminarBeneficiario(int id) throws IllegalArgumentException, SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de beneficiario inválido para eliminación.");
        }
        // Opcional: Verificar si el beneficiario tiene entregas o asignaciones asociadas
        // antes de eliminarlo para mantener la integridad referencial.
        // Esto requeriría métodos en EntregaRepository y AsignacionRepository (e.g., countEntregasByBeneficiarioId).

        if (beneficiarioRepository.findById(id) == null) {
            throw new IllegalArgumentException("El beneficiario con ID " + id + " no existe y no puede ser eliminado.");
        }

        beneficiarioRepository.delete(id);
        System.out.println("Beneficiario con ID " + id + " eliminado con éxito.");
    }
}