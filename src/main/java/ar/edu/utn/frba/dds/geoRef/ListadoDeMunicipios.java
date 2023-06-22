package ar.edu.utn.frba.dds.geoRef;

import java.util.List;

public class ListadoDeMunicipios {
  public int cantidad;
  public int total;
  public int inicio;
  public Parametro parametros;
  public List<MunicipioGeoref> municipios;

  private class Parametro {
    public List<String> campos;
    public int max;
    public List<String> provincia;
  }
}