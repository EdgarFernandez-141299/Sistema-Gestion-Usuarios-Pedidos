package net.edgar.microservicepedidos.repository;

import net.edgar.microservicepedidos.entity.PedidoEntity;
import net.edgar.microservicepedidos.model.dto.pedido.PedidoDTO;
import net.edgar.microservicepedidos.model.enums.EstadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    @Query("select new net.edgar.microservicepedidos.model.dto.pedido.PedidoDTO("
            + " pedido.idPedido as idPedido, "
            + " pedido.estado as estado, "
            + " pedido.total as total "
            + ") from PedidoEntity pedido "
            + " where (:criterio is null "
            + " or (lower(pedido.estado) like lower(concat('%', replace(:criterio, ' ', '%'), '%')) "
            + " or lower(str(pedido.total)) like lower(concat('%', replace(:criterio, ' ', '%'), '%')) "
            + " or lower(str(pedido.idPedido)) like lower(concat('%', replace(:criterio, ' ', '%'), '%'))))"
            + " and pedido.estado != 'ELIMINADO'")
    List<PedidoDTO> buscarPedidos(@Param("criterio") String criterio);


    Optional<PedidoEntity> findByIdPedidoAndEstadoNot(Long idPedido, EstadoEnum estadoEnum);

    @Modifying
    @Query("UPDATE PedidoEntity pe SET pe.estado = :estadoEnum WHERE pe.idPedido = :idPedido")
    int actualizarPedido(@Param("idPedido") Long idPedido, @Param("estadoEnum") EstadoEnum estadoEnum);



}
