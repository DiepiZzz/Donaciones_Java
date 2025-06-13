/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tcc_bd2;

import java.sql.SQLException;

import com.donaciones.model.Donante;
import com.donaciones.repository.DonanteRepository;

/**
 *
 * @author USER
 */
public class Tcc_bd2 {

    public static void main(String[] args) {
        DonanteRepository repo = new DonanteRepository();
        Donante donante = new Donante(0, "jj", "jj", "jj", "jj");
        try {
			repo.insert(donante);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
