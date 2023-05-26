package ar.edu.utn.frba.dds.validacion;

import java.util.regex.Pattern;

public class EstrategiaValidacionRegExp implements EstrategiaValidacion{
  private String patron;

  public EstrategiaValidacionRegExp(String patron){
    this.patron = patron;
  }

  @Override
  public boolean validar(String cadena){
    return Pattern.matches(patron, cadena);
  }
}
