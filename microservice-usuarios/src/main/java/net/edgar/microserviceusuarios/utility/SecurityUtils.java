package net.edgar.microserviceusuarios.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.edgar.microserviceusuarios.model.dto.security.UsuarioDetallesDTO;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

    public static UsuarioDetallesDTO obtenerDetallesUsuarioAutenticado() {
        return (UsuarioDetallesDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
