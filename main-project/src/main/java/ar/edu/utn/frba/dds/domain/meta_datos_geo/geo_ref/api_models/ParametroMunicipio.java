package ar.edu.utn.frba.dds.domain.meta_datos_geo.geo_ref.api_models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ParametroMunicipio {
  @SerializedName("campos")
  public List<String> campos;
  @SerializedName("max")
  public int max;
  @SerializedName("provincia")
  public List<String> provincia;
}
