package net.edgar.microserviceusuarios.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import net.edgar.microserviceusuarios.exception.ExistingUserException;
import net.edgar.microserviceusuarios.exception.NotFoundException;
import net.edgar.microserviceusuarios.exception.UpdateDatabaseException;
import net.edgar.microserviceusuarios.model.dto.GlobalSuccessResponseDTO;
import net.edgar.microserviceusuarios.model.dto.usuario.request.UsuarioCreateRequestDTO;
import net.edgar.microserviceusuarios.model.dto.usuario.request.UsuarioUpdateRequestDTO;
import net.edgar.microserviceusuarios.service.UsuarioService;
import net.edgar.microserviceusuarios.utility.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@RestController
@RequestMapping("/gestion-usuarios")
@Validated
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/insertar-usuario")
    public ResponseEntity<GlobalSuccessResponseDTO<Object>> insertarUsuario(@RequestBody @Valid UsuarioCreateRequestDTO usuarioCreateRequestDTO) throws ExistingUserException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseUtils.generateSuccessResponse(this.usuarioService.insertarUsuario(usuarioCreateRequestDTO)));

    }

    @GetMapping("/buscar-usuarios/{criterio}")
    public ResponseEntity<GlobalSuccessResponseDTO<Object>> buscarUsuarios(@PathVariable("criterio") String criterio) throws NotFoundException {
        return ResponseEntity.ok(ResponseUtils.generateSuccessResponse(this.usuarioService.buscarUsuarios(URLDecoder.decode(criterio, StandardCharsets.UTF_8))));
    }

    @GetMapping("/seleccionar-usuario")
    public ResponseEntity<GlobalSuccessResponseDTO<Object>> seleccionarUsuario(@RequestParam("idUsuario") Long idUsuario) throws NotFoundException {
        return ResponseEntity.ok(ResponseUtils.generateSuccessResponse(this.usuarioService.seleccionarUsuario(idUsuario)));
    }

    @PatchMapping("/actualizar-usuario/{idUsuario}")
    public ResponseEntity<GlobalSuccessResponseDTO<Object>> actualizarUsuario(@PathVariable("idUsuario") Long idUsuario, @RequestBody @Valid UsuarioUpdateRequestDTO usuarioUpdateRequestDTO) throws UpdateDatabaseException, NotFoundException {
        return ResponseEntity.ok(ResponseUtils.generateSuccessResponse(this.usuarioService.actualizarUsuario(idUsuario, usuarioUpdateRequestDTO)));
    }

    @DeleteMapping("/eliminar-usuario/{idUsuario}")
    public ResponseEntity<GlobalSuccessResponseDTO<Object>> eliminarUsuario(@PathVariable("idUsuario") Long idUsuario) throws UpdateDatabaseException {
        return ResponseEntity.ok(ResponseUtils.generateSuccessResponse(this.usuarioService.eliminarUsuario(idUsuario)));
    }

}
