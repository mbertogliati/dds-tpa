package ar.edu.utn.frba.dds.domain.servicios;

import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
import lombok.Getter;
import lombok.Setter;

public class ServicioPrestado {
  @Getter @Setter
  private int id;
  @Getter
  private Servicio servicio;
  @Getter @Setter
  private Ubicacion ubicacion;

  public ServicioPrestado(Servicio servicio, Ubicacion ubicacion){
    this.servicio = servicio;
    this.ubicacion = ubicacion;
  }
}
