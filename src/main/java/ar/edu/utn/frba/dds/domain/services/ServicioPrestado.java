package ar.edu.utn.frba.dds.domain.services;

import lombok.Setter;
import lombok.Getter;

public class ServicioPrestado {
  @Setter @Getter
  private int id;
  @Setter
  private Servicio servicio;
  @Setter
  private boolean disponibilidad;

  public ServicioPrestado(int id, Servicio servicio, boolean disponibilidad) {
    this.id = id;
    this.servicio = servicio;
    this.disponibilidad = disponibilidad;
  }

  public boolean getAccesibilidad(){
    return this.disponibilidad;
  }
  public void noEsAccesible(){
    this.disponibilidad = false;
  }
  public void esAccesible(){
    this.disponibilidad = true;
  }
}
