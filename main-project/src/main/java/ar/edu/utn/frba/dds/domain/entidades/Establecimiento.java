package ar.edu.utn.frba.dds.domain.entidades;

import ar.edu.utn.frba.dds.domain.servicios.Servicio;
import ar.edu.utn.frba.dds.domain.servicios.ServicioPrestado;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
import ar.edu.utn.frba.dds.meta_datos_geo.Localidad;
import ar.edu.utn.frba.dds.meta_datos_geo.Municipio;
import ar.edu.utn.frba.dds.meta_datos_geo.Provincia;
import lombok.Getter;
import lombok.Setter;

//TODO: Decidir si necesitamos el atributo serviciosPrestados y evitar la lógica de listas
public class Establecimiento {
  @Getter @Setter
  private int id;
  @Getter
  private Denominacion denominacion;
  @Getter @Setter
  private String nombre;
  @Getter
  private List<ServicioPrestado> serviciosPrestados = new ArrayList<>();
  @Getter @Setter
  private Ubicacion ubicacion;
  @Getter @Setter
  private Entidad entidad;

  public Establecimiento(String nombre, String denominacion){
    this.nombre = nombre;
    this.denominacion = new Denominacion(denominacion);
  }

  public Establecimiento(String nombre, Denominacion denominacion){
    this.nombre = nombre;
    this.denominacion = denominacion;
  }

  public ServicioPrestado agregarServicio(Servicio servicio){
    ServicioPrestado servicioPrestado = new ServicioPrestado(servicio);
    servicioPrestado.setEstablecimiento(this);
    this.serviciosPrestados.add(servicioPrestado);
    return servicioPrestado;
  }

  public void eliminarServicioPrestado(ServicioPrestado servicioPrestado){
    this.serviciosPrestados.removeIf(servicioPres -> servicioPres.getId() == servicioPrestado.getId());
  }

  public void eliminarServicio(Servicio servicio){
    this.serviciosPrestados.removeIf(servicioPres -> servicioPres.getServicio().getId() == servicio.getId());
  }

  public List<ServicioPrestado> serviciosPrestadosDelServicio(Servicio servicio){
    return this.serviciosPrestados.stream().filter(servicioPrestado -> servicioPrestado.getServicio().getId() == servicio.getId()).toList();
  }


}