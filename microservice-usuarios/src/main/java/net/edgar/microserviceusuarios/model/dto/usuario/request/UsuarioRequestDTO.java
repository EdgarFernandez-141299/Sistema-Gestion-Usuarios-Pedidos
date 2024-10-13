package net.edgar.microserviceusuarios.model.dto.usuario.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioRequestDTO {

    @NotEmpty(message = "nombre es requerido")
    private String nombre;

    @NotEmpty(message = "correoElectronico es requerido")
    @Email(message = "El correoElectronico no cumple con el formato adecuado")
    private String correoElectronico;

}
