package ar.edu.utn.frba.dds.geoRef;

import java.util.List;

public class ResponseUbicacion {
  public Parametro parametros;
  public UbicacionGeoRef ubicacionGeoRef;

  public class UbicacionGeoRef {
    public Departamento departamento;
    public float lat;
    public float lon;
    public Municipio municipio;
    public Provincia provincia;
  }

  private class Parametro {
    public List<String> campos;
    public int max;
    public List<String> provincia;
  }
}
