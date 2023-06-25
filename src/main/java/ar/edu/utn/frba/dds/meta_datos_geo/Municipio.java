package ar.edu.utn.frba.dds.meta_datos_geo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Municipio {
  @Getter
  private int id;
  @Getter
  public String nombre;
  @Getter @Setter
  List<Localidad> localidades;

  public Municipio(int id, String nombre){
    this.id = id;
    this.nombre = nombre;
  }
}