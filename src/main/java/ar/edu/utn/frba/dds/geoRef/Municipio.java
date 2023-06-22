package ar.edu.utn.frba.dds.geoRef;

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