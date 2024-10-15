package net.edgar.microservicepedidos.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class NotFoundException extends Exception {

    public NotFoundException(String message) {
        super(message);
    }

}
