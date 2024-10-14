package net.edgar.microserviceusuarios.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import net.edgar.microserviceusuarios.model.dto.security.UsuarioDetallesDTO;
import net.edgar.microserviceusuarios.utility.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JWTHelper {

    @Value("${jwt.token.validity}")
    public Long jwtTokenValidity;

    private final KeyLoaderHelper keyLoaderHelper;

    public String extractUsername(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return this.extractClaim(token, claims -> claims.get("usuario", String.class));
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return claimsResolver.apply(extractAllClaims(token));
    }


    public String generateToken(UsuarioDetallesDTO usuarioDetallesDTO) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Map<String, Object> claims = new HashMap<>();
        claims.put("usuario", usuarioDetallesDTO.getUsername());
        return this.createToken(claims, String.valueOf(usuarioDetallesDTO.getIdUsuario()));
    }

    private String createToken(Map<String, Object> claims, String subject) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity))
                .signWith(SignatureAlgorithm.RS256, this.keyLoaderHelper.loadPrivateKey())
                .compact();
    }

    public String generateRefreshToken() throws InvalidKeySpecException, NoSuchAlgorithmException {

        UsuarioDetallesDTO usuarioDetallesDTO = SecurityUtils.obtenerDetallesUsuarioAutenticado();

        Map<String, Object> claims = new HashMap<>();
        claims.put("usuario", usuarioDetallesDTO.getUsername());

        return createToken(claims, String.valueOf(usuarioDetallesDTO.getIdUsuario()));
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
