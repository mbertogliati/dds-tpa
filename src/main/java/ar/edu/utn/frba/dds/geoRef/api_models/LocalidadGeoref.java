package ar.edu.utn.frba.dds.geoRef.api_models;

public class LocalidadGeoref {
  public String id;
  public String nombre;

  public LocalidadGeoref() {
    this.id = "";
    this.nombre = "";
  }

  public LocalidadGeoref(String id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }
}