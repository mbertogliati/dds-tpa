package ar.edu.utn.frba.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorCriterio {
  private String tipoError;
  private String mensaje;
}
