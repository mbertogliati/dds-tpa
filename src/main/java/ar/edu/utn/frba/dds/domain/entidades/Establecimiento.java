package ar.edu.utn.frba.dds.domain.entidades;

import ar.edu.utn.frba.dds.domain.servicios.Servicio;
import ar.edu.utn.frba.dds.domain.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class Establecimiento {
  @Getter @Setter
  private int id;
  @Getter
  private Denominacion denominacion;
  @Getter
  private List<ServicioPrestado> serviciosPrestados = new ArrayList<>();
  @Getter
  private Ubicacion ubicacion;

  public Establecimiento(Ubicacion ubicacion, Denominacion denominacion){
    this.ubicacion = ubicacion;
    this.denominacion = denominacion;
  }

  public List<Servicio> serviciosConDisponibilidad(boolean disponibilidad){
    return this.serviciosPrestados.stream().filter(serv -> serv.isDisponibilidad() == disponibilidad).map(serv -> serv.getServicio()).toList();
  }

  public void agregarServicio(ServicioPrestado servicioPrestado){
    this.serviciosPrestados.add(servicioPrestado);
  }

  public void eliminarServicioPrestado(ServicioPrestado servicioPrestado){
    this.serviciosPrestados.removeIf(servicio -> servicio.getId() == servicioPrestado.getId());
  }

  public void eliminarServicio(Servicio servicio){
    this.serviciosPrestadosConServicio(servicio).forEach(servicioPrestado -> this.serviciosPrestados.remove(servicio));
  }

  public void setServicio(Servicio servicio, boolean valor){
    this.serviciosPrestadosConServicio(servicio).forEach(servicioPrestado -> servicioPrestado.setDisponibilidad(valor));
  }

  public List<ServicioPrestado> serviciosPrestadosConServicio(Servicio servicio){
    return this.serviciosPrestados.stream().filter(servicioPrestado -> servicioPrestado.getServicio().getId() == servicio.getId()).toList();
  }


}
