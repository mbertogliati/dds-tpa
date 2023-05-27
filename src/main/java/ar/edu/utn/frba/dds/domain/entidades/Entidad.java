package ar.edu.utn.frba.dds.domain.entidades;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.utilidades.InformacionAdapter;
import ar.edu.utn.frba.dds.domain.utilidades.Localizacion;
import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public abstract class Entidad {
  @Getter @Setter
  private int id;
  @Getter
  private String nombre;
  @Getter
  private Denominacion denominacion;
  @Getter @Setter
  private Persona informado = null;
  private InformacionAdapter generadorInformacion;

  public abstract List<Localizacion> getLocalizaciones();

  protected Entidad(String nombre, Denominacion denominacion) {
    this.nombre = nombre;
    this.denominacion = denominacion;
  }

  public abstract void enviarInformacion();
}
