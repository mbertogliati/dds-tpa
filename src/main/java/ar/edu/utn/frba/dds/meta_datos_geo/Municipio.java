package ar.edu.utn.frba.dds.meta_datos_geo;

import lombok.Getter;

public class Municipio {
  @Getter
  private int id;
  @Getter
  public String nombre;

  public Municipio(int id, String nombre){
    this.id = id;
    this.nombre = nombre;
  }
}