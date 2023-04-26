package ar.edu.utn.frba.dds.domain.users;

import lombok.Getter;
import lombok.Setter;

public class Miembro {
  @Setter @Getter
  private Usuario usuario;
  @Setter
  private Comunidad comunidad;
  @Setter
  private RolComunidad rol;

  public Miembro() {
    this.usuario = null;
    this.comunidad = null;
    this.rol = null;
  }

  public Miembro(Usuario usuario, Comunidad comunidad, RolComunidad rol) {
    this.usuario = usuario;
    this.comunidad = comunidad;
    this.rol = rol;
  }
}
