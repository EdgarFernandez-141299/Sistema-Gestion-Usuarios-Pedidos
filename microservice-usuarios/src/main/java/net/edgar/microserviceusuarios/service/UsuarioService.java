package net.edgar.microserviceusuarios.service;

import net.edgar.microserviceusuarios.exception.UpdateDatabaseException;
import net.edgar.microserviceusuarios.model.dto.usuario.UsuarioDTO;
import net.edgar.microserviceusuarios.model.dto.usuario.request.UsuarioRequestDTO;

import java.util.List;

public interface UsuarioService {

    UsuarioDTO insertarUsuario(UsuarioRequestDTO usuarioRequestDTO);

    List<UsuarioDTO> buscarUsuarios(String pCriterio);

    UsuarioDTO seleccionarUsuario(Long idUsuario);

    UsuarioDTO actualizarUsuario(Long idUsuario, UsuarioRequestDTO usuarioRequestDTO) throws UpdateDatabaseException;

    String eliminarUsuario(Long idUsuario) throws UpdateDatabaseException;


}
