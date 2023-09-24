package ar.edu.utn.frba.dds.modelos.meta_datos_geo.geo_ref.api_models;

import com.google.gson.annotations.SerializedName;

public class ResponseUbicacion {
  @SerializedName("parametros")
  public ParametroUbicacion parametros;

  @SerializedName("ubicacion")
  public UbicacionGeoRef ubicacion;
}
