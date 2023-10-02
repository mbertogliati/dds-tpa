package ar.edu.utn.frba.dds.controllers.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensajeVista {
  private String texto;
  private String tipo;

  public MensajeVista(TipoMensaje tipo, String texto){
    this.texto=texto;
    switch (tipo){
      case ERROR:
        this.tipo = "danger";
        break;

      case WARNING:
        this.tipo = "warning";
        break;

      case SUCCESS:
        this.tipo = "success";
        break;

    }
  }

  public enum TipoMensaje{
    ERROR,
    WARNING,
    SUCCESS
  }
}
