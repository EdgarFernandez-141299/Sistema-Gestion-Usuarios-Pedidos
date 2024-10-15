package net.edgar.microserviceusuarios.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import net.edgar.microserviceusuarios.model.dto.GlobalSuccessResponseDTO;
import net.edgar.microserviceusuarios.model.dto.security.request.LoginRequestDTO;
import net.edgar.microserviceusuarios.service.SecurityService;
import net.edgar.microserviceusuarios.utility.ResponseUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static net.edgar.microserviceusuarios.constant.MicroserviceUsuariosConstant.SecurityConstant.BEARER_PREFIX;
import static net.edgar.microserviceusuarios.constant.MicroserviceUsuariosConstant.SecurityConstant.ACCESS_TOKEN_HEADER;


@RequiredArgsConstructor
@RequestMapping("/security")
@RestController
@Validated
public class SecurityController {

    private final SecurityService securityService;

    private static final String SUCCESS_LOGIN_MESSAGE = "Inicio de sesión exitoso";

    private static final String SUCCESS_REFRESH_TOKEN_MESSAGE = "El token se ha renovado exitosamente";


    @PostMapping("/login")
    public ResponseEntity<GlobalSuccessResponseDTO<Object>> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) throws Exception {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(ACCESS_TOKEN_HEADER, String.format("%s%s", BEARER_PREFIX, this.securityService.login(loginRequestDTO)));
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(ResponseUtils.generateSuccessResponse(
                        String.format("%s", SUCCESS_LOGIN_MESSAGE)));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<GlobalSuccessResponseDTO<Object>> refreshToken() throws InvalidKeySpecException, NoSuchAlgorithmException {
        return ResponseEntity.ok()
                .header(ACCESS_TOKEN_HEADER, this.securityService.refreshToken())
                .body(ResponseUtils.generateSuccessResponse(
                        String.format("%s", SUCCESS_REFRESH_TOKEN_MESSAGE)));
    }

}
