package ar.edu.utn.frba.dds.meta_datos_geo.geo_ref.api_models;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Optional;

public class ListadoDeProvincias {
  @SerializedName("cantidad")
  public int cantidad;
  @SerializedName("total")
  public int total;
  @SerializedName("inicio")
  public int inicio;
  @SerializedName("parametros")
  public ParametroProvincia parametros;
  @SerializedName("provincias")
  public List<EntidadGeoRef> provincias;

  public Optional<EntidadGeoRef> provinciaDeId(String id) {
    return this.provincias.stream()
        .filter(p -> p.id == id)
        .findFirst();
  }
}