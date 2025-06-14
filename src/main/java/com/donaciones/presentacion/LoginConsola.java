package com.donaciones.presentacion;

import com.donaciones.model.Usuario;
import com.donaciones.servicios.AuthService;
import java.sql.SQLException;
import java.util.Scanner; 

public class LoginConsola {

    private AuthService authService;
    private Scanner scanner; 

    public LoginConsola() { 
        this.authService = new AuthService();
        
        
        this.scanner = new Scanner(System.in);
    }

   

    
    public Usuario iniciarSesion() {
        Usuario usuarioAutenticado = null;
        int opcionLogin;

        do {
            System.out.println("\n--- BIENVENIDO AL SISTEMA ---");
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Registrarse");
            System.out.println("0. Salir de la aplicación");
            System.out.print("Seleccione una opción: ");

            try {
                opcionLogin = Integer.parseInt(scanner.nextLine());

                switch (opcionLogin) {
                    case 1:
                        usuarioAutenticado = intentarLogin();
                        if (usuarioAutenticado != null) {
                            opcionLogin = 0; 
                        }
                        break;
                    case 2:
                        registrarNuevoUsuario();
                        break;
                    case 0:
                        System.out.println("Saliendo de la aplicación...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                opcionLogin = -1; 
            }
        } while (opcionLogin != 0 && usuarioAutenticado == null);

        return usuarioAutenticado;
    }

    private Usuario intentarLogin() {
        int intentos = 0;
        final int MAX_INTENTOS = 3;
        Usuario usuarioAutenticado = null;

        while (usuarioAutenticado == null && intentos < MAX_INTENTOS) {
            System.out.println("\n--- INICIO DE SESIÓN ---");
            System.out.print("Usuario: ");
            String username = scanner.nextLine();

            System.out.print("Contraseña: ");
            String password = scanner.nextLine();

            try {
                usuarioAutenticado = authService.autenticar(username, password);
                if (usuarioAutenticado == null) {
                    System.out.println("Usuario o contraseña incorrectos. Intento " + (intentos + 1) + " de " + MAX_INTENTOS + ".");
                    intentos++;
                } else {
                    System.out.println("¡Autenticación exitosa!");
                }
            } catch (SQLException e) {
                System.err.println("Error de base de datos durante la autenticación: " + e.getMessage());
                break;
            }
        }
        if (usuarioAutenticado == null && intentos >= MAX_INTENTOS) {
            System.out.println("Demasiados intentos fallidos. Volviendo al menú principal de login/registro.");
        }
        return usuarioAutenticado;
    }

    private void registrarNuevoUsuario() {
        System.out.println("\n--- REGISTRO DE NUEVO USUARIO ---");
        String username;
        String password;
        String confirmPassword;
        boolean usernameValid = false;

        do {
            System.out.print("Ingrese nuevo usuario: ");
            username = scanner.nextLine();
            if (username.isEmpty()) {
                System.out.println("El nombre de usuario no puede estar vacío.");
                continue;
            }
            try {
                if (authService.existeUsuario(username)) {
                    System.out.println("El nombre de usuario '" + username + "' ya existe. Por favor, elija otro.");
                } else {
                    usernameValid = true;
                }
            } catch (SQLException e) {
                System.err.println("Error al verificar el usuario: " + e.getMessage());
                usernameValid = false;
                break;
            }
        } while (!usernameValid);

        if (!usernameValid) {
            System.out.println("No se pudo proceder con el registro debido a un error del sistema.");
            return;
        }

        do {
            System.out.print("Ingrese contraseña: ");
            password = scanner.nextLine();
            System.out.print("Confirme contraseña: ");
            confirmPassword = scanner.nextLine();

            if (!password.equals(confirmPassword)) {
                System.out.println("Las contraseñas no coinciden. Intente de nuevo.");
            } else if (password.isEmpty()) {
                System.out.println("La contraseña no puede estar vacía.");
            }
        } while (!password.equals(confirmPassword) || password.isEmpty());

        try {
            if (authService.crearUsuario(username, password)) {
                System.out.println("¡Usuario '" + username + "' creado exitosamente!");
            } else {
                System.out.println("No se pudo crear el usuario. Revise los datos.");
            }
        } catch (SQLException e) {
            System.err.println("Error al registrar el usuario: " + e.getMessage());
        }
    }

    
    public void cerrarScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}