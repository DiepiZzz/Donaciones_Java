package com.donacione.servicios;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.donaciones.model.Donante;
import com.donaciones.repository.DonanteRepository;


public class DonanteService {

    private DonanteRepository repositorio;

    public DonanteService() {
        this.repositorio = new DonanteRepository();
    }

    public void registrarDonante() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Donante donante = new Donante();

        System.out.println("\n--- REGISTRO DE DONANTE ---");
        System.out.print("Nombre: ");
        donante.setNombre(scanner.nextLine());

        System.out.print("Apellido: ");
        donante.setApellido(scanner.nextLine());

        System.out.print("Email: ");
        donante.setEmail(scanner.nextLine());

        System.out.print("Teléfono: ");
        donante.setTelefono(scanner.nextLine());

        boolean exito = repositorio.insert(donante);

        if (exito) {
            System.out.println("✅ Donante registrado exitosamente.");
        } else {
            System.out.println("❌ Error al registrar donante.");
        }
    }
    
    public void listarDonantes() throws SQLException {
    	
    	List<Donante> listaDonantes = repositorio.findAll();
    	
    	for (Donante donante : listaDonantes) {
			System.out.println(donante.toString());
		}
    	
    	
    	
    }
    
    
}

