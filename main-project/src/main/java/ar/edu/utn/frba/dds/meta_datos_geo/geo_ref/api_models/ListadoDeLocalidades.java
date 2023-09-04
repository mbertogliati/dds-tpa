package ar.edu.utn.frba.dds.meta_datos_geo.geo_ref.api_models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListadoDeLocalidades {
  @SerializedName("cantidad")
  public int cantidad;
  @SerializedName("inicio")
  public int inicio;
  @SerializedName("localidades")
  public List<EntidadGeoRef> localidades;
  @SerializedName("parametros")
  public ParametroLocalidad parametros;
  @SerializedName("total")
  public int total;
}
