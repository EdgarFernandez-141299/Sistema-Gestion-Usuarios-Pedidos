package net.edgar.microserviceusuarios.model.dto.pedido.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoCreateRequestDTO {


    @NotNull(message = "total es requerido")
    private Double total;

    @NotNull(message = "idUsuario es requerido")
    private Long idUsuario;
}
