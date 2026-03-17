package com.example.demo.controllers;
import com.example.demo.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {
    private final JwtUtil jwtUtil;

    public UserController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/perfil")
    public ResponseEntity<?> getPerfil(@RequestHeader("Authorization") String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token ausente");
        }

        String token = header.substring(7);

        try {
            String username = jwtUtil.extractUsername(token);
            if (jwtUtil.validateToken(token, username)) {
                Map<String, Object> userData = new HashMap<>();
                userData.put("nombre", "Tu Nombre de Alumno");
                userData.put("rol", "Estudiante Programación IV");
                userData.put("lastLogin", new Date());
                return ResponseEntity.ok(userData);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
