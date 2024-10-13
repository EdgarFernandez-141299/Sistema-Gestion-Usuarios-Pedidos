package net.edgar.microserviceusuarios.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class UpdateDatabaseException extends Exception {

    public UpdateDatabaseException(String message) {
        super(message);
    }

}
