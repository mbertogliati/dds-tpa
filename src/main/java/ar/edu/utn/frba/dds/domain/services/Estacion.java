package ar.edu.utn.frba.dds.domain.services;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class Estacion {
  @Setter @Getter
  private int id;
  @Setter
  private String nombre;
  @Setter
  private float[] ubicacion;
  @Setter @Getter
  private List<ServicioPrestado> serviciosPrestados;

  public Estacion() {
    this.nombre = null;
    this.ubicacion = null;
    this.serviciosPrestados = new ArrayList<ServicioPrestado>();
  }

  public Estacion(int id, String nombre, float[] ubicacion, List<ServicioPrestado> serviciosPrestados) {
    this.id = id;
    this.nombre = nombre;
    this.ubicacion = ubicacion;
    this.serviciosPrestados = serviciosPrestados;
  }

  public void agregarServicioPrestado(ServicioPrestado nuevoServicioPrestado) {
    this.serviciosPrestados.add(nuevoServicioPrestado);
  }

  public void eliminarServicioPrestado(ServicioPrestado servicio) {
    this.eliminarServicioPrestado(servicio.getId());
  }
  public void eliminarServicioPrestado(int idBuscada) {
    for (ServicioPrestado servicioPrestadoActual : this.serviciosPrestados) {
      if (servicioPrestadoActual.getId() == idBuscada) {
        this.serviciosPrestados.remove(servicioPrestadoActual);
      }
    }
  }
}
