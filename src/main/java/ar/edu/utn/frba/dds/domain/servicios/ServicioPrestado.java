package ar.edu.utn.frba.dds.domain.servicios;

import lombok.Getter;
import lombok.Setter;

public class ServicioPrestado {
  @Getter @Setter
  private int id;
  @Getter
  private Servicio servicio;
  @Setter @Getter
  private boolean disponibilidad;

  public ServicioPrestado(Servicio servicio){
    this.servicio = servicio;
    this.disponibilidad = true;
  }
}
