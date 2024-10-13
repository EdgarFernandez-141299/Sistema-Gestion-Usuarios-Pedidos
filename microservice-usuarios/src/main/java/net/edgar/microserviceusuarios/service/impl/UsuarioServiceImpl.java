package net.edgar.microserviceusuarios.service.impl;

import lombok.RequiredArgsConstructor;
import net.edgar.microserviceusuarios.entity.UsuarioEntity;
import net.edgar.microserviceusuarios.exception.UpdateDatabaseException;
import net.edgar.microserviceusuarios.model.dto.usuario.UsuarioDTO;
import net.edgar.microserviceusuarios.model.dto.usuario.request.UsuarioRequestDTO;
import net.edgar.microserviceusuarios.repository.UsuarioRepository;
import net.edgar.microserviceusuarios.service.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public UsuarioDTO insertarUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioEntity usuarioEntity = this.usuarioRepository.save(new UsuarioEntity(usuarioRequestDTO.getNombre(), usuarioRequestDTO.getCorreoElectronico(), Boolean.TRUE));
        return new UsuarioDTO(usuarioEntity.getIdUsuario(), usuarioEntity.getNombre(), usuarioEntity.getCorreoElectronico());
    }

    @Override
    public List<UsuarioDTO> buscarUsuarios(String pCriterio) {
        List<UsuarioDTO> lstUsuarios = this.usuarioRepository.buscarUsuarios(pCriterio);

        if (lstUsuarios.isEmpty()) {
            throw new NoSuchElementException("No se encontraron registros");
        }

        return lstUsuarios;
    }

    @Override
    public UsuarioDTO seleccionarUsuario(Long idUsuario) {
        UsuarioEntity usuarioEntity = this.usuarioRepository.findByIdUsuarioAndActivoTrue(idUsuario)
                .orElseThrow(() -> new NoSuchElementException(String.format("No se encontro el usuario con id %d", idUsuario)));
        return new UsuarioDTO(usuarioEntity.getNombre(), usuarioEntity.getCorreoElectronico());
    }

    @Override
    @Transactional
    public UsuarioDTO actualizarUsuario(Long idUsuario, UsuarioRequestDTO usuarioRequestDTO) throws UpdateDatabaseException {

        if (this.usuarioRepository.actualizarUsuario(idUsuario, usuarioRequestDTO.getCorreoElectronico()) == 0) {
            throw new UpdateDatabaseException(String.format("No fue posible actualizar el usuario con id: %s", idUsuario));
        }

        UsuarioEntity usuarioEntity = this.usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new NoSuchElementException(String.format("El usuario con id: %s no fue encontrado", idUsuario)));

        return new UsuarioDTO(usuarioEntity.getCorreoElectronico());
    }

    @Override
    @Transactional
    public String eliminarUsuario(Long idUsuario) throws UpdateDatabaseException {

        if(this.usuarioRepository.eliminarUsuario(idUsuario, Boolean.FALSE) == 0) {
            throw new UpdateDatabaseException(String.format("No fue posible eliminar el usuario con id: %s", idUsuario));
        }

        return "Se ha eliminado de forma exitosa el usuario";
    }
}
