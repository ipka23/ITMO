package services;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import jakarta.ejb.Stateless;
import jakarta.ws.rs.core.NewCookie;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Stateless
public class JwtService {
    public SecretKey JWT_SIGN_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public long JWT_EXPIRATION_TIME = TimeUnit.DAYS.toMillis(7);

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
                .signWith(JWT_SIGN_KEY)
                .compact();
    }
    public String extractSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(JWT_SIGN_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean tokenIsValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(JWT_SIGN_KEY)
                    .build()
                    .parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (JwtException e){
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return !Jwts.parserBuilder()
                .setSigningKey(JWT_SIGN_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }


    public NewCookie createJwtCookie(String jwt) {
        return new NewCookie.Builder("JWT")
                .value(jwt)
                .httpOnly(true)
                .maxAge((int) Duration.ofDays(7).getSeconds())
                .build();
    }
}
