package ar.edu.utn.frba.dds.domain.services;

import ar.edu.utn.frba.dds.domain.common.Caracteristica;

public class SubtipoServicio extends Caracteristica {
  public SubtipoServicio() {
    this.id = 0;
    this.descripcion = null;
  }

  public SubtipoServicio(int id, String descripcion) {
    this.id = id;
    this.descripcion = descripcion;
  }
}
