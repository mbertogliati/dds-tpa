package ar.edu.utn.frba.dds.validacion;

public class EstrategiaValidacionRegExp implements EstrategiaValidacion{
  private String patron;

  public EstrategiaValidacionRegExp(String patron){
    this.patron = patron;
  }

  @Override
  public boolean validar(String cadena){
    //TODO: CHEQUEAR QUE LA CADENA NO CUMPLA CON ESA REGEX QUE ESTÁ EN PATRON
    return true;
  }
}
