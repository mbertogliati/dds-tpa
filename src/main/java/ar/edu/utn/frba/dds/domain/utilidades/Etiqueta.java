package ar.edu.utn.frba.dds.domain.utilidades;

import lombok.Getter;

public class Etiqueta {
  @Getter
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
