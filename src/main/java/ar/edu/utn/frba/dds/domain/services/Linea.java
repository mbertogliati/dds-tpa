package ar.edu.utn.frba.dds.domain.services;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class Linea {
  @Setter
  private TipoTransporte tipo;
  @Setter
  private String nombre;
  @Setter
  private Estacion estacionOrigen;
  @Setter
  private Estacion estacionDestino;
  @Setter
  @Getter
  private List<Estacion> estaciones;

  public Linea() {
    this.tipo = null;
    this.nombre = null;
    this.estacionOrigen = null;
    this.estacionDestino = null;
    this.estaciones = new ArrayList<Estacion>();
  }

  public Linea(TipoTransporte tipo, String nombre, Estacion estacionOrigen,
               Estacion estacionDestino, List<Estacion> estaciones) {
    this.tipo = tipo;
    this.nombre = nombre;
    this.estacionOrigen = estacionOrigen;
    this.estacionDestino = estacionDestino;
    this.estaciones = estaciones;
  }

  public void agregarEstacion(Estacion nuevaEstacion) {
    this.estaciones.add(nuevaEstacion);
  }

  public void eliminarEstacion(Estacion estacion) {
    for (Estacion estacionActual : this.estaciones) {
      if (estacionActual.getId() == estacion.getId()) {
        this.estaciones.remove(estacionActual);
      }
    }
  }
  public void eliminarEstacion(int idBuscada) {
    for (Estacion estacionActual : this.estaciones) {
      if (estacionActual.getId() == idBuscada) {
        this.estaciones.remove(estacionActual);
      }
    }
  }
}
