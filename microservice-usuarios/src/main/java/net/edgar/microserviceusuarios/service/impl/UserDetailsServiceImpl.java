package net.edgar.microserviceusuarios.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.edgar.microserviceusuarios.entity.UsuarioEntity;
import net.edgar.microserviceusuarios.model.dto.security.UsuarioDetallesDTO;
import net.edgar.microserviceusuarios.repository.UsuarioRepository;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static net.edgar.microserviceusuarios.constant.MicroserviceUsuariosConstant.SecurityConstant.USER_INVALID_MENSAJE_BASE;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {

        UsuarioEntity usuario = this.usuarioRepository.findByNombre(nombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("%s %s", USER_INVALID_MENSAJE_BASE, nombreUsuario)));

        if (!usuario.isActivo()) {
            throw new InternalAuthenticationServiceException("La cuenta del usuario se encuentra inactiva");
        }

        return UsuarioDetallesDTO
                .username(usuario.getNombre())
                .password(usuario.getClaveAcceso())
                .idUsuario(usuario.getIdUsuario()).build();

    }


}
