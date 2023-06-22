package ar.edu.utn.frba.dds.geoRef;

import java.util.List;

public class ResponseUbicacion {
  public Parametro parametros;
  public UbicacionGeoRef ubicacion;

  public class UbicacionGeoRef {
    public LocalidadGeoref localidadGeoref;
    public float lat;
    public float lon;
    public MunicipioGeoref municipioGeoref;
    public ProvinciaGeoref provinciaGeoref;
  }

  private class Parametro {
    public List<String> campos;
    public int max;
    public List<String> provincia;
  }
}
