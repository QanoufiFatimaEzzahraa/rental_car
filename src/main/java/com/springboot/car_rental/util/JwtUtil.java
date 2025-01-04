package com.springboot.car_rental.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.springboot.car_rental.enums.UserRole;

import java.util.Date;

@Component
public class JwtUtil {

	@Value("${jwt.secret}") // Chargez la clé depuis les variables d'environnement ou un fichier properties
    private String SECRET_KEY;
    public String generateToken(String email, UserRole userRole) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", userRole) // Ajouter le rôle dans le token
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Expiration : 10 heures
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return (String) getClaims(token).get("role");
    }
    
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractEmail(token);
        return (userName.equals(userDetails.getUsername()));
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            // Token expiré
            throw new RuntimeException("Token expiré");
        } catch (SignatureException e) {
            // Signature invalide
            throw new RuntimeException("Signature invalide");
        } catch (Exception e) {
            // Autres exceptions
            throw new RuntimeException("Erreur lors de la validation du token");
        }
    }
}
