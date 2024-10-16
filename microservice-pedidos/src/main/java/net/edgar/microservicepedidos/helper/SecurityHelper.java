package net.edgar.microservicepedidos.helper;

import lombok.RequiredArgsConstructor;
import net.edgar.microservicepedidos.model.dto.security.UsuarioDetallesDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityHelper {

    private final UserDetailsService userDetailsService;

    public UserDetails loadUserAuthorizationDetails(String subject, String username) {
        UsuarioDetallesDTO usuarioDetallesDTO = (UsuarioDetallesDTO) this.userDetailsService.loadUserByUsername(username);
        usuarioDetallesDTO.setIdUsuario(Long.parseLong(subject));
        return usuarioDetallesDTO;
    }

}
