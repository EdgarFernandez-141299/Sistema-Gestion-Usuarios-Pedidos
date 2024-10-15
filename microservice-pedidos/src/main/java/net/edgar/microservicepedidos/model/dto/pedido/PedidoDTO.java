package net.edgar.microservicepedidos.model.dto.pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.edgar.microservicepedidos.model.enums.EstadoEnum;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoDTO {

    private Long idPedido;

    private EstadoEnum estado;

    private Double total;
}
