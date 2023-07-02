package ar.edu.utn.frba.dds.domain.servicios;

import ar.edu.utn.frba.dds.domain.entidades.Establecimiento;
import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
import lombok.Getter;
import lombok.Setter;

public class ServicioPrestado {
  @Getter @Setter
  private int id;
  @Getter
  private Servicio servicio;
  @Getter @Setter
  private Establecimiento establecimiento;

  public ServicioPrestado(Servicio servicio) {
    this.servicio = servicio;
  }
  public Ubicacion getUbicacion(){
    return establecimiento.getUbicacion();
  }
}
