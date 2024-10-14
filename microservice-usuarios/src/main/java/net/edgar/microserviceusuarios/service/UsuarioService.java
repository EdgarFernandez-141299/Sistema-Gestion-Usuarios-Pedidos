package net.edgar.microserviceusuarios.service;

import net.edgar.microserviceusuarios.exception.ExistingUserException;
import net.edgar.microserviceusuarios.exception.NotFoundException;
import net.edgar.microserviceusuarios.exception.UpdateDatabaseException;
import net.edgar.microserviceusuarios.model.dto.usuario.UsuarioDTO;
import net.edgar.microserviceusuarios.model.dto.usuario.request.UsuarioRequestDTO;

import java.util.List;

public interface UsuarioService {

    UsuarioDTO insertarUsuario(UsuarioRequestDTO usuarioRequestDTO) throws ExistingUserException;

    List<UsuarioDTO> buscarUsuarios(String pCriterio) throws NotFoundException;

    UsuarioDTO seleccionarUsuario(Long idUsuario) throws NotFoundException;

    UsuarioDTO actualizarUsuario(Long idUsuario, UsuarioRequestDTO usuarioRequestDTO) throws UpdateDatabaseException, NotFoundException;

    String eliminarUsuario(Long idUsuario) throws UpdateDatabaseException;


}
