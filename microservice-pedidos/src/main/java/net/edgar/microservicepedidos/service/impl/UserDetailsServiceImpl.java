package net.edgar.microservicepedidos.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.edgar.microservicepedidos.model.dto.security.UsuarioDetallesDTO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static net.edgar.microserviceusuarios.constant.MicroservicePedidosConstant.SecurityConstant.DUMMY_PASSWORD;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UsuarioDetallesDTO
                .username(username)
                .password(DUMMY_PASSWORD) // El prefijo {noop} le indica a Spring Security que no debe realizar ninguna codificación de la contraseña, y dummyPassword es una contraseña ficticia que nunca será utilizada.
                .build();
    }


}
