package net.edgar.microservicepedidos.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JWTHelper {

    private final KeyLoaderHelper keyLoaderHelper;

    public String extractUsername(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return this.extractClaim(token, claims -> claims.get("usuario", String.class));
    }

    public String extrtactSubject(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return this.extractClaim(token, claims -> claims.get("sub", String.class));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return claimsResolver.apply(extractAllClaims(token));
    }

    public void validateToken(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.parseToken(token);
    }

    private Claims extractAllClaims(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return parseToken(token);
    }

    private Claims parseToken(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return Jwts.parser()
                .setSigningKey(this.keyLoaderHelper.loadPublicKey())
                .parseClaimsJws(token).getBody();
    }

}
