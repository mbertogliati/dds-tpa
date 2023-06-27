package ar.edu.utn.frba.dds.meta_datos_geo.geo_ref.api_models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ParametroDepartamento {
  @SerializedName("campos")
  public List<String> campos;
  @SerializedName("max")
  public int max;
  @SerializedName("provincia")
  public List<String> provincia;
}
