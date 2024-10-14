package net.edgar.microserviceusuarios.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.edgar.microserviceusuarios.helper.JWTHelper;
import net.edgar.microserviceusuarios.model.dto.security.UsuarioDetallesDTO;
import net.edgar.microserviceusuarios.model.dto.security.request.LoginRequestDTO;
import net.edgar.microserviceusuarios.service.SecurityService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static net.edgar.microserviceusuarios.constant.MicroserviceUsuariosConstant.SecurityConstant.BEARER_PREFIX;


@Slf4j
@RequiredArgsConstructor
@Service
public class SecurityServiceImpl implements SecurityService {

    private final AuthenticationManager authenticationManager;

    private final JWTHelper jwtHelper;

    @Override
    public String login(LoginRequestDTO loginRequestDTO) throws InvalidKeySpecException, NoSuchAlgorithmException {

        log.info("Autenticando al usuario {}", loginRequestDTO.getUsuario());

        UsuarioDetallesDTO usuarioDetallesDTO = (UsuarioDetallesDTO) this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsuario(),
                        loginRequestDTO.getClaveAcceso())).getPrincipal();

        return this.jwtHelper.generateToken(usuarioDetallesDTO);
    }

    @Override
    public String refreshToken() throws InvalidKeySpecException, NoSuchAlgorithmException {
        return String.format("%s%s", BEARER_PREFIX, this.jwtHelper.generateRefreshToken());
    }
}
