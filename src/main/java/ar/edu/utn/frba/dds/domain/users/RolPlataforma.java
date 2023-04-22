package ar.edu.utn.frba.dds.domain.users;

import ar.edu.utn.frba.dds.domain.common.Caracteristica;

public class RolPlataforma extends Caracteristica {
  public RolPlataforma() {
    this.id = 0;
    this.descripcion = null;
  }

  public RolPlataforma(int id, String descripcion) {
    this.id = id;
    this.descripcion = descripcion;
  }
}
