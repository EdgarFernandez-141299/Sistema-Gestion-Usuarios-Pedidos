package net.edgar.microservicepedidos.service.impl;

import lombok.RequiredArgsConstructor;
import net.edgar.microservicepedidos.entity.PedidoEntity;
import net.edgar.microservicepedidos.exception.NotFoundException;
import net.edgar.microservicepedidos.exception.UpdateDatabaseException;
import net.edgar.microservicepedidos.model.dto.pedido.PedidoDTO;
import net.edgar.microservicepedidos.model.dto.pedido.request.PedidoCreateRequestDTO;
import net.edgar.microservicepedidos.model.dto.pedido.request.PedidoUpdateRequestDTO;
import net.edgar.microservicepedidos.model.enums.EstadoEnum;
import net.edgar.microservicepedidos.repository.PedidoRepository;
import net.edgar.microservicepedidos.service.PedidoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        return new PedidoDTO(pedidoEntity.getIdPedido(), pedidoEntity.getEstado(), pedidoEntity.getTotal());
    }

    @Override
    @Transactional
    public PedidoDTO actualizarPedido(Long idPedido, PedidoUpdateRequestDTO pedidoUpdateRequestDTO) throws UpdateDatabaseException, NotFoundException {

        if (this.pedidoRepository.actualizarPedido(idPedido, pedidoUpdateRequestDTO.getEstado()) == 0) {
            throw new UpdateDatabaseException(String.format("No fue posible actualizar el pedido con id: %s", idPedido));
        }

        PedidoEntity pedidoEntity = this.pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new NotFoundException(String.format("El pedido con id: %s no fue encontrado", idPedido)));

        return new PedidoDTO(pedidoEntity.getIdPedido(), pedidoEntity.getEstado(), pedidoEntity.getTotal());
    }

    @Override
    @Transactional
    public String eliminarPedido(Long idPedido) throws UpdateDatabaseException {

        if (this.pedidoRepository.eliminarPedido(idPedido) == 0) {
            throw new UpdateDatabaseException(String.format("No fue posible eliminar el pedido con id: %s", idPedido));
        }

        return "Se ha eliminado de forma exitosa el pedido";
    }

}
