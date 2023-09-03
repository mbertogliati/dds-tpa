package ar.edu.utn.frba.dds.meta_datos_geo;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

public abstract class EntidadGeografica {
  @Getter @Setter
  protected String id;

  @Getter @Setter
  protected  String nombre;

  protected EntidadGeografica() {
    this.id = "";
    this.nombre = "";
  }

  protected EntidadGeografica(String id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  public boolean esIgual(EntidadGeografica entidad) {
    if (entidad == null) {
      return false;
    }
    return Objects.equals(this.id, entidad.getId());
  }
}
