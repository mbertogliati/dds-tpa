package ar.edu.utn.frba.dds.domain.utilidades;

import lombok.Getter;

public class Localizacion {
  @Getter
  private String id;
  @Getter
  private String nombre;
  @Getter
  private TipoLocalizacion tipo;

  public Localizacion(String id, String nombre, TipoLocalizacion tipo){
    this.id = id;
    this.tipo = tipo;
    this.nombre = nombre;
  }
}
