package com.example.demo.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.function.Function;
@Component
public class JwtUtil {
    private String secret = "MiClaveSecretaSuperSeguraDe32Caracteres!";

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // [cite: 76]
                .setIssuedAt(new Date()) // [cite: 77]
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // Dura 1 hora [cite: 78, 229]
                .signWith(SignatureAlgorithm.HS256, secret.getBytes()) // [cite: 79]
                .compact();
    }

    public boolean validateToken(String token, String username) {
        final String extractedUser = extractUsername(token);
        return (extractedUser.equals(username) && !isTokenExpired(token)); // [cite: 82, 87]
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }
}
