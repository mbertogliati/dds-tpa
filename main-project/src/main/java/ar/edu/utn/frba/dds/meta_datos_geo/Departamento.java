package ar.edu.utn.frba.dds.meta_datos_geo;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class Departamento extends EntidadGeografica {
  @Getter @Setter
  private List<Localidad> localidades;

  public Departamento(String id, String nombre) {
    super(id, nombre);
    this.localidades = new ArrayList<Localidad>();
  }
}
