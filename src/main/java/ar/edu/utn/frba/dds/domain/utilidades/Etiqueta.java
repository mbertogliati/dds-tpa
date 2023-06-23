package ar.edu.utn.frba.dds.domain.utilidades;

import lombok.Getter;
import lombok.Setter;

public class Etiqueta {
  @Getter @Setter
  private int id;
  @Getter
  private String tipo;
  @Getter
  private String valor;

  public Etiqueta(String tipo, String valor){
    this.tipo = tipo;
    this.valor = valor;
  }
}
