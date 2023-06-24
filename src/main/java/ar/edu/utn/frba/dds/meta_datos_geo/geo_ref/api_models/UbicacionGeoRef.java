package ar.edu.utn.frba.dds.meta_datos_geo.geo_ref.api_models;

import com.google.gson.annotations.SerializedName;

public class UbicacionGeoRef {
  @SerializedName("lat")
  public float lat;
  @SerializedName("lon")
  public float lon;
  @SerializedName("municipio")
  public MunicipioGeoref municipio;
  @SerializedName("provincia")
  public ProvinciaGeoref provincia;
  @SerializedName("localidad")
  public LocalidadGeoref localidad;
}
