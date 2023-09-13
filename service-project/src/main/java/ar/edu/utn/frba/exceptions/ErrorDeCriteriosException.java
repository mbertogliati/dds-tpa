package ar.edu.utn.frba.exceptions;

import lombok.Getter;
import lombok.Setter;

public class ErrorDeCriteriosException extends RuntimeException {
  @Getter @Setter
  private Error error;

  public ErrorDeCriteriosException(String tipoError, String mensaje){
    this.error = new Error();
    this.error.setTipoError(tipoError);
    this.error.setMensaje(mensaje);
  }
}
