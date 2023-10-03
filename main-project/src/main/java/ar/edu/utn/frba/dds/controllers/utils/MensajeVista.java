package ar.edu.utn.frba.dds.controllers.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensajeVista {
  private String texto;
  private String tipo;

  public MensajeVista(String tipo, String texto){
    this.tipo=tipo;
    this.texto=texto;
  }
}
