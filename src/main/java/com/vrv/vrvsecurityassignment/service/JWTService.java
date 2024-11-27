package com.vrv.vrvsecurityassignment.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service  // Marks this class as a service to be managed by Spring's container
public class JWTService {

    private String secretKey = "";  // Secret key for signing JWTs

    // Constructor that generates a base64-encoded secret key for HMAC algorithm
    public JWTService() {
        this.secretKey = Base64.getEncoder().encodeToString(Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256).getEncoded());
    }

    // Method to generate a JWT token for a given username and role
    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);  // Storing the role as a claim in the token
        return Jwts.builder()
                .setClaims(claims)  // Add custom claims
                .setSubject(username)  // Set the subject (username)
                .setIssuedAt(new Date(System.currentTimeMillis()))  // Set the issued time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // Set token expiration time (10 hours)
                .signWith(getKey())  // Sign the token with the secret key
                .compact();  // Build and return the JWT
    }

    // Helper method to get the secret key used to sign JWTs
    private SecretKey getKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);  // Decode the base64-encoded key
        return Keys.hmacShaKeyFor(bytes);  // Return the HMAC key
    }

    // Method to extract the username (subject) from a JWT token
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);  // Extract the subject (username)
    }

    // Method to extract the role from the JWT token
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));  // Extract role from claims
    }

    // Generic method to extract claims (e.g., username or role) from the token
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);  // Extract all claims from the token
        return claimResolver.apply(claims);  // Apply the claim resolver to extract the specific claim
    }

    // Method to parse and extract all claims from a JWT token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())  // Set the signing key to verify the JWT's signature
                .build()
                .parseClaimsJws(token)  // Parse the token and extract claims
                .getBody();  // Return the claims body
    }

    // Method to validate the JWT token by comparing username and checking if it's expired
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);  // Extract the username from the token
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);  // Check if username matches and token is not expired
    }

    // Method to check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());  // Check if the token's expiration date is before the current time
    }

    // Method to extract the expiration date from the token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);  // Extract the expiration date claim
    }
}
