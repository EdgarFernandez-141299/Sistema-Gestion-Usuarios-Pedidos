package net.edgar.microserviceusuarios.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class MissingTokenException extends Exception {
  public MissingTokenException(String message) {
    super(message);
  }
}
