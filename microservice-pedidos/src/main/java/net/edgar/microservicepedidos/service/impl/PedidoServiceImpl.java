package net.edgar.microservicepedidos.service.impl;

import lombok.RequiredArgsConstructor;
import net.edgar.microservicepedidos.entity.PedidoEntity;
import net.edgar.microservicepedidos.exception.NotFoundException;
import net.edgar.microservicepedidos.model.dto.pedido.PedidoDTO;
import net.edgar.microservicepedidos.model.dto.pedido.request.PedidoCreateRequestDTO;
import net.edgar.microservicepedidos.model.enums.EstadoEnum;
import net.edgar.microservicepedidos.repository.PedidoRepository;
import net.edgar.microservicepedidos.service.PedidoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;


    @Override
    public PedidoDTO insertarPedido(PedidoCreateRequestDTO pedidoCreateRequestDTO) {
        PedidoEntity pedidoEntity = this.pedidoRepository.save(new PedidoEntity(
                EstadoEnum.PENDIENTE,
                pedidoCreateRequestDTO.getTotal(),
                pedidoCreateRequestDTO.getIdUsuario()));

        return new PedidoDTO(pedidoEntity.getIdPedido(), pedidoEntity.getEstado(), pedidoEntity.getTotal());
    }


    @Override
    public List<PedidoDTO> buscarPedidos(String criterio) throws NotFoundException {

        List<PedidoDTO> lstPedidos = this.pedidoRepository.buscarPedidos(criterio);

        if (lstPedidos.isEmpty()) {
            throw new NotFoundException("No se encontrarón pedidos con el criterio proporcionado");
        }

        return lstPedidos;
    }

    @Override
    public PedidoDTO seleccionarPedido(Long idPedido) throws NotFoundException {

        PedidoEntity pedidoEntity = this.pedidoRepository.findByIdPedidoAndEstadoNot(idPedido, EstadoEnum.ELIMINADO)
                .orElseThrow(() -> new NotFoundException(String.format("No se encontro el pedido con id %d", idPedido)));

        return new PedidoDTO(pedidoEntity.getIdPedido(), pedidoEntity.getEstado(),pedidoEntity.getTotal());
    }
}
