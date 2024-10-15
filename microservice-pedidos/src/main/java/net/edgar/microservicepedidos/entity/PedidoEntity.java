package net.edgar.microservicepedidos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.edgar.microservicepedidos.model.enums.EstadoEnum;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pedido")
@EntityListeners(AuditingEntityListener.class)
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;

    @CreatedDate
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    @Column(name = "estado")
    private EstadoEnum estado;

    @Basic(optional = false)
    @Column(name = "total")
    private Double total;

    @Basic(optional = false)
    @Column(name = "id_usuario")
    private Long idUsuario;

    public PedidoEntity(EstadoEnum estado, Double total, Long idUsuario) {
        this.estado = estado;
        this.total = total;
        this.idUsuario = idUsuario;
    }
}
