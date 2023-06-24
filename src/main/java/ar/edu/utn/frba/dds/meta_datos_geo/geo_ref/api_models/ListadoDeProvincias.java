package ar.edu.utn.frba.dds.meta_datos_geo.geo_ref.api_models;

import java.util.List;
import java.util.Optional;

public class ListadoDeProvincias {
  public int cantidad;
  public int total;
  public int inicio;
  public ParametroProvincia parametros;
  public List<ProvinciaGeoref> provincias;

  public Optional<ProvinciaGeoref> provinciaDeId(String id) {
    return this.provincias.stream()
        .filter(p -> p.id == id)
        .findFirst();
  }
}