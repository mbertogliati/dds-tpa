package ar.edu.utn.frba.dds.domain.meta_datos_geo.geo_ref.api_models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListadoDeMunicipios {
  @SerializedName("cantidad")
  public int cantidad;
  @SerializedName("total")
  public int total;
  @SerializedName("inicio")
  public int inicio;
  @SerializedName("parametros")
  public ParametroMunicipio parametros;
  @SerializedName("municipios")
  public List<EntidadGeoRef> municipios;
}