package com.example.demo.controllers;
import com.example.demo.models.UserCredentials;
import com.example.demo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserCredentials credentials) {
        // Esto va a imprimir en la consola de IntelliJ (abajo)
        System.out.println("--- INTENTO DE CONEXIÓN ---");

        if (credentials == null) {
            System.out.println("ERROR: El objeto credentials llegó NULO");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cuerpo vacío");
        }

        System.out.println("Usuario: [" + credentials.getUsername() + "]");
        System.out.println("Password: [" + credentials.getPassword() + "]");

        // Usamos equalsIgnoreCase y trim para evitar errores de espacios o mayúsculas
        String userLog = (credentials.getUsername() != null) ? credentials.getUsername().trim() : "";
        String passLog = (credentials.getPassword() != null) ? credentials.getPassword().trim() : "";

        if ("admin".equalsIgnoreCase(userLog) && "1234".equals(passLog)) {
            System.out.println("RESULTADO: ¡Login Exitoso!");
            String token = jwtUtil.generateToken(userLog);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            System.out.println("RESULTADO: Credenciales incorrectas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error");
        }
    }
}