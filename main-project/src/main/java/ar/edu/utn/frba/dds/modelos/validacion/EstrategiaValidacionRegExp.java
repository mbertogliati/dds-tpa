package ar.edu.utn.frba.dds.modelos.validacion;

import java.util.regex.Pattern;

public class EstrategiaValidacionRegExp implements EstrategiaValidacion{
  private Pattern patron;

  public EstrategiaValidacionRegExp(String patron){
    this.patron = Pattern.compile(patron);
  }

  @Override
  public boolean validar(String cadena){
    return patron.matcher(cadena).find();
  }
}
