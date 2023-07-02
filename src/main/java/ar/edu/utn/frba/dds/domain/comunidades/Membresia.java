package ar.edu.utn.frba.dds.domain.comunidades;

import lombok.Getter;
import lombok.Setter;

public class Membresia {
  @Getter
  private Persona persona;
  @Getter
  private Comunidad comunidad;
  @Getter @Setter
  private Rol rolComunidad;
  @Getter @Setter
  private boolean afectado;

  public Membresia(Comunidad comunidad, Persona persona){
    this.persona = persona;
    this.comunidad = comunidad;
  }
}
