package com.donacione.servicios;

import java.sql.SQLException;
import java.util.List;

import com.donaciones.model.Donacion;
import com.donaciones.repository.DonacionRepository;

public class DonacionesService {
	
	private DonacionRepository repositorio;
	
	public DonacionesService() {
		this.repositorio = new DonacionRepository();
	}
	
	public void listarDonaciones() throws SQLException {
		
		List<Donacion> listaDonaciones = repositorio.findAll();
		
		for (Donacion donacion : listaDonaciones) {
			System.out.println(donacion.toString());
		}
	}
	
	
}
