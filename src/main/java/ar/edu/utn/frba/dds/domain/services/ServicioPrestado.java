package ar.edu.utn.frba.dds.domain.services;

import lombok.Setter;

public class ServicioPrestado {
  @Setter
  private int id;
  @Setter
  private Servicio servicio;
  @Setter
  private boolean disponibilidad;

  public ServicioPrestado() {
    this.id = 0;
    this.servicio = null;
    this.disponibilidad = false;
  }

  public ServicioPrestado(int id, Servicio servicio, boolean disponibilidad) {
    this.id = id;
    this.servicio = servicio;
    this.disponibilidad = disponibilidad;
  }
}
