package ar.edu.utn.frba.dds.modelos.validacion;

public class EstrategiaValidacionNoEstaEnLista implements EstrategiaValidacion{
  private ObtenerListaString obtenerListaString;

  public EstrategiaValidacionNoEstaEnLista(ObtenerListaString listaEn){
    this.obtenerListaString = listaEn;
  }

  @Override
  public boolean validar(String cadena){
    return this.obtenerListaString.obtenerLista().stream().noneMatch(cadenaLista -> cadenaLista.equals(cadena));
  }
}
