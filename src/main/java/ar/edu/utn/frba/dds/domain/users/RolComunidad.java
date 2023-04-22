package ar.edu.utn.frba.dds.domain.users;

import ar.edu.utn.frba.dds.domain.common.Caracteristica;

public class RolComunidad extends Caracteristica {
  public RolComunidad() {
    this.id = 0;
    this.descripcion = null;
  }

  public RolComunidad(int id, String descripcion) {
    this.id = id;
    this.descripcion = descripcion;
  }
}
