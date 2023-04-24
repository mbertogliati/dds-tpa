package ar.edu.utn.frba.dds.domain.common;

import lombok.Getter;
import lombok.Setter;

public abstract class Caracteristica {
  @Setter @Getter
  protected int id;
  @Setter
  protected String descripcion;
}
