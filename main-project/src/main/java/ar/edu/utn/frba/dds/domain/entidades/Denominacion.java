package ar.edu.utn.frba.dds.domain.entidades;

import lombok.Getter;
import lombok.Setter;

public class Denominacion {
  @Getter
  private int id;
  @Getter @Setter
  private String descripcion;

  public Denominacion(String descripcion){
    this.descripcion = descripcion;
  }

}
