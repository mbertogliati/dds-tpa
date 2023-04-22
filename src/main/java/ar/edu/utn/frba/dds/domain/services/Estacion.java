package ar.edu.utn.frba.dds.domain.services;

import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class Estacion {
  @Setter
  private String nombre;
  @Setter
  private float[] ubicacion;
  @Setter
  private List<ServicioPrestado> serviciosPrestados;

  public Estacion() {
    this.nombre = null;
    this.ubicacion = null;
    this.serviciosPrestados = new ArrayList<ServicioPrestado>();
  }

  public Estacion(String nombre, float[] ubicacion, List<ServicioPrestado> serviciosPrestados) {
    this.nombre = nombre;
    this.ubicacion = ubicacion;
    this.serviciosPrestados = serviciosPrestados;
  }

  public void agregarServicioPrestado(ServicioPrestado nuevoServicioPrestado) {
    this.serviciosPrestados.add(nuevoServicioPrestado);
  }

  public void eliminarServicioPrestado(ServicioPrestado servicioPrestadoAEliminar) {
    this.serviciosPrestados.remove(servicioPrestadoAEliminar);
  }
}
