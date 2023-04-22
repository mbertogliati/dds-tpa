package ar.edu.utn.frba.dds.domain.common;

import lombok.Setter;

public abstract class Caracteristica {
  @Setter
  protected int id;
  @Setter
  protected String descripcion;
}
