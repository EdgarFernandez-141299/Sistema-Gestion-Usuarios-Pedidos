package net.edgar.microserviceusuarios.gateway.pedido;

import net.edgar.microserviceusuarios.model.dto.GlobalSuccessResponseDTO;
import net.edgar.microserviceusuarios.model.dto.pedido.PedidoDTO;
import net.edgar.microserviceusuarios.model.dto.pedido.request.PedidoCreateRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;;

@FeignClient(name = "${application.integration.pedidos.name}", value = "${application.integration.pedidos.name}", url = "${application.integration.pedidos.url}")
public interface PedidoGateway {

    @PostMapping(value = "/gestion-pedidos/insertar-pedido", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GlobalSuccessResponseDTO<PedidoDTO>> insertarPedido(@RequestHeader("Authorization") String authorization,
                                                                       @RequestHeader("Trace-Id") String traceId,
                                                                       @RequestBody PedidoCreateRequestDTO pedidoCreateRequestDTO);

}
