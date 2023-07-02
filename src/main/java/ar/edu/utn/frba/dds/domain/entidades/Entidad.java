package ar.edu.utn.frba.dds.domain.entidades;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
import lombok.Getter;
import lombok.Setter;

public class Entidad {
  @Getter @Setter
  private int id;
  @Getter
  private String nombre;
  @Getter
  private Denominacion denominacion;
  @Getter @Setter
  private Ubicacion ubicacion;

  public Entidad(String nombre, String denominacion) {
    this.nombre = nombre;
    this.denominacion = new Denominacion(denominacion);
  }

  public Entidad(String nombre, Denominacion denominacion) {
    this.nombre = nombre;
    this.denominacion = denominacion;
  }


}
