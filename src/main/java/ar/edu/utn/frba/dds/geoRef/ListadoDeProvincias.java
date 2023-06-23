package ar.edu.utn.frba.dds.geoRef;

import java.util.List;
import java.util.Optional;

public class ListadoDeProvincias {
  public int cantidad;
  public int total;
  public int inicio;
  public Parametro parametros;
  public List<ProvinciaGeoref> provinciaGeorefs;

  public Optional<ProvinciaGeoref> provinciaDeId(String id){
    return this.provinciaGeorefs.stream()
        .filter(p -> p.id == id)
        .findFirst();
  }

  private class Parametro {
    public List<String> campos;
  }
}