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
            + " pe.idPedido as idPedido, "
            + " pe.estado as estado, "
            + " pe.total as total "
            + ") from PedidoEntity pe "
            + " where (:criterio is null "
            + " or (lower(pe.estado) like lower(concat('%', replace(:criterio, ' ', '%'), '%')) "
            + " or lower(str(pe.total)) like lower(concat('%', replace(:criterio, ' ', '%'), '%')) "
            + " or lower(str(pe.idPedido)) like lower(concat('%', replace(:criterio, ' ', '%'), '%'))))"
            + " and pe.estado != 'ELIMINADO'")
    List<PedidoDTO> buscarPedidos(@Param("criterio") String criterio);


    Optional<PedidoEntity> findByIdPedidoAndEstadoNot(Long idPedido, EstadoEnum estadoEnum);

    @Modifying
    @Query("UPDATE PedidoEntity pe SET pe.estado = :estadoEnum WHERE pe.idPedido = :idPedido")
    int actualizarPedido(@Param("idPedido") Long idPedido, @Param("estadoEnum") EstadoEnum estadoEnum);

    @Modifying
    @Query("UPDATE PedidoEntity pe "
            + "SET pe.estado = 'ELIMINADO' "
            + "WHERE pe.idPedido = :#{#idPedido}")
    int eliminarPedido(@Param("idPedido") Long idPedido);


}
