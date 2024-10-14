package net.edgar.microserviceusuarios.service.impl;

import lombok.RequiredArgsConstructor;
import net.edgar.microserviceusuarios.entity.UsuarioEntity;
import net.edgar.microserviceusuarios.exception.ExistingUserException;
import net.edgar.microserviceusuarios.exception.NotFoundException;
import net.edgar.microserviceusuarios.exception.UpdateDatabaseException;
import net.edgar.microserviceusuarios.model.dto.usuario.UsuarioDTO;
import net.edgar.microserviceusuarios.model.dto.usuario.request.UsuarioCreateRequestDTO;
import net.edgar.microserviceusuarios.model.dto.usuario.request.UsuarioUpdateRequestDTO;
import net.edgar.microserviceusuarios.repository.UsuarioRepository;
import net.edgar.microserviceusuarios.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static net.edgar.microserviceusuarios.constant.MicroserviceUsuariosConstant.ResponseConstant.EXISTING_USER_MENSAJE;

@RequiredArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public UsuarioDTO insertarUsuario(UsuarioCreateRequestDTO usuarioCreateRequestDTO) throws ExistingUserException {

        if (this.usuarioRepository.existsByNombreUsuario(usuarioCreateRequestDTO.getNombre())) {
            throw new ExistingUserException(String.format(EXISTING_USER_MENSAJE, usuarioCreateRequestDTO.getNombre()));
        }
        usuarioCreateRequestDTO.setClaveAcceso(this.passwordEncoder.encode(usuarioCreateRequestDTO.getClaveAcceso()));
        UsuarioEntity usuarioEntity = this.usuarioRepository.save(new UsuarioEntity(usuarioCreateRequestDTO.getNombre(), usuarioCreateRequestDTO.getClaveAcceso(), usuarioCreateRequestDTO.getCorreoElectronico(), Boolean.TRUE));
        return new UsuarioDTO(usuarioEntity.getIdUsuario(), usuarioEntity.getNombre(), usuarioEntity.getCorreoElectronico());
    }

    @Override
    public List<UsuarioDTO> buscarUsuarios(String pCriterio) throws NotFoundException {
        List<UsuarioDTO> lstUsuarios = this.usuarioRepository.buscarUsuarios(pCriterio);

        if (lstUsuarios.isEmpty()) {
            throw new NotFoundException("No se encontraron registros");
        }

        return lstUsuarios;
    }

    @Override
    public UsuarioDTO seleccionarUsuario(Long idUsuario) throws NotFoundException {
        UsuarioEntity usuarioEntity = this.usuarioRepository.findByIdUsuarioAndActivoTrue(idUsuario)
                .orElseThrow(() -> new NotFoundException(String.format("No se encontro el usuario con id %d", idUsuario)));
        return new UsuarioDTO(usuarioEntity.getNombre(), usuarioEntity.getCorreoElectronico());
    }

    @Override
    @Transactional
    public UsuarioDTO actualizarUsuario(Long idUsuario, UsuarioUpdateRequestDTO usuarioUpdateRequestDTO) throws UpdateDatabaseException, NotFoundException {

        if (this.usuarioRepository.actualizarUsuario(idUsuario, usuarioUpdateRequestDTO.getCorreoElectronico(), this.passwordEncoder.encode(usuarioUpdateRequestDTO.getClaveAcceso())) == 0) {
            throw new UpdateDatabaseException(String.format("No fue posible actualizar el usuario con id: %s", idUsuario));
        }

        UsuarioEntity usuarioEntity = this.usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new NotFoundException(String.format("El usuario con id: %s no fue encontrado", idUsuario)));

        return new UsuarioDTO(usuarioEntity.getCorreoElectronico());
    }

    @Override
    @Transactional
    public String eliminarUsuario(Long idUsuario) throws UpdateDatabaseException {

        if (this.usuarioRepository.eliminarUsuario(idUsuario, Boolean.FALSE) == 0) {
            throw new UpdateDatabaseException(String.format("No fue posible eliminar el usuario con id: %s", idUsuario));
        }

        return "Se ha eliminado de forma exitosa el usuario";
    }
}
