package ar.edu.utn.frba.dds.modelos.meta_datos_geo.geo_ref.api_models;

import com.google.gson.annotations.SerializedName;

public class EntidadGeoRef {
  @SerializedName("id")
  public String id;
  @SerializedName("nombre")
  public String nombre;
  public EntidadGeoRef() {
    this.id = "";
    this.nombre = "";
  }
  public EntidadGeoRef(String id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }
}
