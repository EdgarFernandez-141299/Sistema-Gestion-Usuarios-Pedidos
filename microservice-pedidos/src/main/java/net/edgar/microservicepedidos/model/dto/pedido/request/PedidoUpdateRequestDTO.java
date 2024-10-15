package net.edgar.microservicepedidos.model.dto.pedido.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.edgar.microservicepedidos.model.enums.EstadoEnum;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoUpdateRequestDTO {

    @NotNull(message = "estado es requerido")
    private EstadoEnum estado;

}
