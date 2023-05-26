package ar.edu.utn.frba.dds.domain.entidades;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.utilidades.InformacionAdapter;
import lombok.Getter;
import lombok.Setter;

public abstract class Entidad {
  @Getter
  private int id;
  @Getter
  private String nombre;
  @Getter
  private Denominacion denominacion;
  @Getter @Setter
  private Persona informado = null;
  private InformacionAdapter generadorInformacion;

  protected Entidad(String nombre, Denominacion denominacion) {
    this.nombre = nombre;
    this.denominacion = denominacion;
  }

  public abstract void enviarInformacion();
}
