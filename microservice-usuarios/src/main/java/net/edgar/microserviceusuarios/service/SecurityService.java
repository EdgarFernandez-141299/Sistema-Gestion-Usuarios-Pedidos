package net.edgar.microserviceusuarios.service;


import net.edgar.microserviceusuarios.model.dto.security.request.LoginRequestDTO;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface SecurityService {

    String login(LoginRequestDTO loginRequestDTO) throws Exception;

    String refreshToken() throws InvalidKeySpecException, NoSuchAlgorithmException;
}
