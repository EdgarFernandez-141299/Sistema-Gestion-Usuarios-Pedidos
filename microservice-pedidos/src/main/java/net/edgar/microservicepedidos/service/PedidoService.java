package net.edgar.microservicepedidos.service;

import net.edgar.microservicepedidos.exception.NotFoundException;
import net.edgar.microservicepedidos.model.dto.pedido.PedidoDTO;
import net.edgar.microservicepedidos.model.dto.pedido.request.PedidoCreateRequestDTO;

import java.util.List;

public interface PedidoService {

    PedidoDTO insertarPedido(PedidoCreateRequestDTO pedidoCreateRequestDTO);

    List<PedidoDTO> buscarPedidos(String criterio) throws NotFoundException;

    PedidoDTO seleccionarPedido(Long idPedido) throws NotFoundException;

}
