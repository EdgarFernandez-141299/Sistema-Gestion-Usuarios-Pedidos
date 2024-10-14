package net.edgar.microserviceusuarios.service;

import net.edgar.microserviceusuarios.exception.ExistingUserException;
import net.edgar.microserviceusuarios.exception.NotFoundException;
import net.edgar.microserviceusuarios.exception.UpdateDatabaseException;
import net.edgar.microserviceusuarios.model.dto.usuario.UsuarioDTO;
import net.edgar.microserviceusuarios.model.dto.usuario.request.UsuarioCreateRequestDTO;
import net.edgar.microserviceusuarios.model.dto.usuario.request.UsuarioUpdateRequestDTO;

import java.util.List;

public interface UsuarioService {

    UsuarioDTO insertarUsuario(UsuarioCreateRequestDTO usuarioCreateRequestDTO) throws ExistingUserException;

    List<UsuarioDTO> buscarUsuarios(String pCriterio) throws NotFoundException;

    UsuarioDTO seleccionarUsuario(Long idUsuario) throws NotFoundException;

    UsuarioDTO actualizarUsuario(Long idUsuario, UsuarioUpdateRequestDTO usuarioUpdateRequestDTO) throws UpdateDatabaseException, NotFoundException;

    String eliminarUsuario(Long idUsuario) throws UpdateDatabaseException;


}
