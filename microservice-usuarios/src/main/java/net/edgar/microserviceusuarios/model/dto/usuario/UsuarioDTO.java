package net.edgar.microserviceusuarios.model.dto.usuario;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDTO {

    private Long idUsuario;

    private String nombre;

    private String correoElectronico;

    public UsuarioDTO(String nombre, String correoElectronico) {
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
    }

    public UsuarioDTO(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
}
