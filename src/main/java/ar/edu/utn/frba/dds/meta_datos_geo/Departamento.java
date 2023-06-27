package ar.edu.utn.frba.dds.meta_datos_geo;

import lombok.Getter;

public class Departamento {
  @Getter
  private int id;
  @Getter
  private String nombre;

  public Departamento(int id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }
}
