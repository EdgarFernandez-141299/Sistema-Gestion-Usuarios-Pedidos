package net.edgar.microserviceusuarios.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class PasswordMismatchException extends Exception {

    public PasswordMismatchException(String message) {
        super(message);
    }
}
