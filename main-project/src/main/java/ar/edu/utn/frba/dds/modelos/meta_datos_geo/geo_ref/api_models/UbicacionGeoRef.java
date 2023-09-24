package ar.edu.utn.frba.dds.modelos.meta_datos_geo.geo_ref.api_models;

import com.google.gson.annotations.SerializedName;

public class UbicacionGeoRef {
  @SerializedName("lat")
  public float lat;
  @SerializedName("lon")
  public float lon;
  @SerializedName("provincia")
  public EntidadGeoRef provincia;
  @SerializedName("municipio")
  public EntidadGeoRef municipio;
  @SerializedName("departamento")
  public EntidadGeoRef departamento;
  @SerializedName("localidad")
  public EntidadGeoRef localidad;
}
