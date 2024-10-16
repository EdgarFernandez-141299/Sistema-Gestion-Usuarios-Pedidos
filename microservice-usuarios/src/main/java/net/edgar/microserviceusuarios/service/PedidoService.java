package net.edgar.microserviceusuarios.service;

import net.edgar.microserviceusuarios.model.dto.pedido.PedidoDTO;
import net.edgar.microserviceusuarios.model.dto.pedido.request.PedidoUsuarioRequestDTO;

public interface PedidoService {

    PedidoDTO crearPedido(String authorization, PedidoUsuarioRequestDTO pedidoUsuarioRequestDTO);
}
