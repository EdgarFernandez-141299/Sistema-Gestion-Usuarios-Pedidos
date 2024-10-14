package net.edgar.microserviceusuarios.model.dto.security.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequestDTO {

    @NotNull(message = "usuario no puede ser nulo")
    @NotEmpty(message = "usuario no puede estar vacio")
    private String usuario;

    @NotNull(message = "claveAcceso no puede ser nulo")
    @NotEmpty(message = "claveAcceso no puede estar vacio")
    private String claveAcceso;
}
