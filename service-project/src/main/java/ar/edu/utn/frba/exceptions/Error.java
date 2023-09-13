package ar.edu.utn.frba.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error {
  private String tipoError;
  private String mensaje;
  public Error(){}
  public Error(String tipoError, String mensaje){
    this.tipoError = tipoError;
    this.mensaje = mensaje;
  }
}
