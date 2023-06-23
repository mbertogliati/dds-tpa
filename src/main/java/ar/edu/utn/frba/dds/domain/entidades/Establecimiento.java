package ar.edu.utn.frba.dds.domain.entidades;

import ar.edu.utn.frba.dds.domain.servicios.Servicio;
import ar.edu.utn.frba.dds.domain.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.dds.geoRef.Localidad;
import ar.edu.utn.frba.dds.geoRef.Municipio;
import ar.edu.utn.frba.dds.geoRef.Provincia;
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

  public Provincia getProvincia(){
    return this.ubicacion.getProvincia();
  }

  public Municipio getMunicipio(){
    return this.ubicacion.getMunicipio();
  }

  public Localidad getLocalidad(){
    return this.ubicacion.getLocalidad();
  }



  public List<Servicio> serviciosConDisponibilidad(boolean disponibilidad){
    return this.serviciosPrestados.stream().map(serv->serv.getServicio()).toList();
    //TODO
            //.stream().filter(serv -> serv.isDisponibilidad() == disponibilidad).map(serv -> serv.getServicio()).toList();
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
    //this.serviciosPrestadosConServicio(servicio).forEach(servicioPrestado -> servicioPrestado.setDisponibilidad(valor));
    //TODO
  }

  public List<ServicioPrestado> serviciosPrestadosConServicio(Servicio servicio){
    return this.serviciosPrestados.stream().filter(servicioPrestado -> servicioPrestado.getServicio().getId() == servicio.getId()).toList();
  }


}
