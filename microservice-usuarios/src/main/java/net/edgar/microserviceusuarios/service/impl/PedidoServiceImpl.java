package net.edgar.microserviceusuarios.service.impl;

import lombok.RequiredArgsConstructor;
import net.edgar.microserviceusuarios.gateway.pedido.PedidoGateway;
import net.edgar.microserviceusuarios.model.dto.GlobalSuccessResponseDTO;
import net.edgar.microserviceusuarios.model.dto.pedido.PedidoDTO;
import net.edgar.microserviceusuarios.model.dto.pedido.request.PedidoCreateRequestDTO;
import net.edgar.microserviceusuarios.model.dto.pedido.request.PedidoUsuarioRequestDTO;
import net.edgar.microserviceusuarios.service.PedidoService;
import net.edgar.microserviceusuarios.utility.SecurityUtils;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static net.edgar.microserviceusuarios.constant.MicroserviceUsuariosConstant.TraceabilityConstant.TRACE_ID_KEY;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoGateway pedidoGateway;

    @Override
    public PedidoDTO crearPedido(String authorization, PedidoUsuarioRequestDTO pedidoUsuarioRequestDTO) {
        ResponseEntity<GlobalSuccessResponseDTO<PedidoDTO>> successResponseDTOResponseEntity =
                this.pedidoGateway.insertarPedido(authorization, MDC.get(TRACE_ID_KEY),
                        new PedidoCreateRequestDTO(pedidoUsuarioRequestDTO.getTotal(),
                                SecurityUtils.obtenerDetallesUsuarioAutenticado().getIdUsuario()));

        return Optional.ofNullable(successResponseDTOResponseEntity.getBody())
                .map(GlobalSuccessResponseDTO::getRespuesta)
                .orElseThrow(() -> new IllegalStateException("La respuesta del es inválida."));
    }

}
