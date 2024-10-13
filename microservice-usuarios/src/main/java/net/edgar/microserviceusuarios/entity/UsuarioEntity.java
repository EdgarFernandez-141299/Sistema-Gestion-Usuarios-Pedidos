package net.edgar.microserviceusuarios.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usuario")
@EntityListeners(AuditingEntityListener.class)
public class UsuarioEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    @Basic(optional = false)
    @Column(name = "correo_electronico")
    private String correoElectronico;

    @Basic(optional = false)
    @Column(name = "activo")
    private Boolean activo;

    @CreatedDate
    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;


    public UsuarioEntity(String nombre, String correoElectronico, Boolean activo) {
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.activo = activo;
    }
}
