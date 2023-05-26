package ar.edu.utn.frba.dds.domain.entidades;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.utilidades.InformacionAdapter;
import lombok.Getter;
import lombok.Setter;

public abstract class Entidad {
  private String nombre;
  private Denominacion denominacion;
  private Persona informado;
  private InformacionAdapter generadorInformacion;
  private int id;

  public abstract void enviarInformacion();
}
