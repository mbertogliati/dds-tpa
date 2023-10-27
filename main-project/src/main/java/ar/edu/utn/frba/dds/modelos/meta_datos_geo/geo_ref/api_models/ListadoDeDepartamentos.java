package ar.edu.utn.frba.dds.modelos.meta_datos_geo.geo_ref.api_models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListadoDeDepartamentos {
  @SerializedName("cantidad")
  public int cantidad;
  @SerializedName("total")
  public int total;
  @SerializedName("inicio")
  public int inicio;
  @SerializedName("parametros")
  public ParametroMunicipio parametros;
  @SerializedName("departamentos")
  public List<EntidadGeoRef> departamentos;
}
