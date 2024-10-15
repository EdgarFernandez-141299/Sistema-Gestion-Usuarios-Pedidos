package net.edgar.microservicepedidos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.edgar.microservicepedidos.exception.NotFoundException;
import net.edgar.microservicepedidos.exception.UpdateDatabaseException;
import net.edgar.microservicepedidos.model.dto.GlobalSuccessResponseDTO;
import net.edgar.microservicepedidos.model.dto.pedido.request.PedidoCreateRequestDTO;
import net.edgar.microservicepedidos.model.dto.pedido.request.PedidoUpdateRequestDTO;
import net.edgar.microservicepedidos.service.PedidoService;
import net.edgar.microservicepedidos.utility.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@RestController
@RequestMapping("/gestion-pedidos")
@Validated
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping("/insertar-pedido")
    public ResponseEntity<GlobalSuccessResponseDTO<Object>> insertarPedido(@RequestBody @Valid PedidoCreateRequestDTO pedidoCreateRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseUtils.generateSuccessResponse(this.pedidoService.insertarPedido(pedidoCreateRequestDTO)));
    }

    @GetMapping("/buscar-pedidos/{criterio}")
    public ResponseEntity<GlobalSuccessResponseDTO<Object>> buscarPedidos(@PathVariable("criterio") String criterio) throws NotFoundException {
        return ResponseEntity.ok(ResponseUtils.generateSuccessResponse(this.pedidoService.buscarPedidos(URLDecoder.decode(criterio, StandardCharsets.UTF_8))));
    }

    @GetMapping("/seleccionar-pedido")
    public ResponseEntity<GlobalSuccessResponseDTO<Object>> seleccionarPedido(@RequestParam("idPedido") Long idPedido) throws NotFoundException {
        return ResponseEntity.ok(ResponseUtils.generateSuccessResponse(this.pedidoService.seleccionarPedido(idPedido)));
    }

    @PatchMapping("/actualizar-pedido/{idPedido}")
    public ResponseEntity<GlobalSuccessResponseDTO<Object>> actualizarPedido(@PathVariable("idPedido") Long idUsuario, @RequestBody @Valid PedidoUpdateRequestDTO pedidoUpdateRequestDTO) throws UpdateDatabaseException, NotFoundException {
        return ResponseEntity.ok(ResponseUtils.generateSuccessResponse(this.pedidoService.actualizarPedido(idUsuario, pedidoUpdateRequestDTO)));
    }


}
