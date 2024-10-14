package net.edgar.microserviceusuarios.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class ExistingUserException extends Exception {

    public ExistingUserException(String message) {
        super(message);
    }

}
