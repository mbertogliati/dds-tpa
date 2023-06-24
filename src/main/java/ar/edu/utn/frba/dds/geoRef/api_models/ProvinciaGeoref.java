package ar.edu.utn.frba.dds.geoRef.api_models;

public class ProvinciaGeoref {
  public String id;
  public String nombre;

  public ProvinciaGeoref() {
    this.id = "";
    this.nombre = "";
  }
  public ProvinciaGeoref(String id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }
}