package net.edgar.microserviceusuarios.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.edgar.microserviceusuarios.exception.ExistingUserException;
import net.edgar.microserviceusuarios.model.dto.GlobalSuccessResponseDTO;
import net.edgar.microserviceusuarios.model.dto.pedido.request.PedidoUsuarioRequestDTO;
import net.edgar.microserviceusuarios.service.PedidoService;
import net.edgar.microserviceusuarios.utility.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api-pedidos")
@Validated
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping("/crear-pedido")
    public ResponseEntity<GlobalSuccessResponseDTO<Object>> crearPedido(@RequestHeader("Authorization") String authorization, @RequestBody @Valid PedidoUsuarioRequestDTO pedidoUsuarioRequestDTO) throws ExistingUserException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseUtils.generateSuccessResponse(this.pedidoService.crearPedido(authorization, pedidoUsuarioRequestDTO)));

    }
}
