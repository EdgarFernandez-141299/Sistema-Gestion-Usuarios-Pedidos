package net.edgar.microserviceusuarios.exception;


import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExternalServiceResponseFailedException extends RuntimeException {


    private final String codigo;

    private final String folio;

    private final int httpCode;

    private final List<String> detalles;


    public ExternalServiceResponseFailedException(String codigo, String folio, int httpCode,  String message, List<String> detalles ) {
        super(message);
        this.httpCode = httpCode;
        this.folio = folio;
        this.detalles = detalles;
        this.codigo = codigo;
    }

}
