package ar.edu.utn.frba.dds.domain.comunidades;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.entidades.Establecimiento;
import ar.edu.utn.frba.dds.domain.servicios.Servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.utn.frba.dds.domain.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
import lombok.Getter;
import lombok.Setter;

public class Interes {
  @Getter
  private List<Entidad> entidades = new ArrayList<>();
  @Getter
  private List<Servicio> servicios = new ArrayList<>();

  public boolean servicioPrestadoEsDeInteres(ServicioPrestado servicioPrestado){
    if(this.servicios.stream().map(s -> s.getId()).toList().contains(servicioPrestado.getServicio().getId())){
      //El servicio está en la lista de ineteres
      //Ahora verifico si la ubicacion del servicioPrestado coincide con alguna de las entidades de interés
      List<List<Establecimiento>> listaDeListas = this.entidades.stream().map(e -> e.getEstablecimientos()).toList();
      return listaDeListas.stream().flatMap(List::stream).collect(Collectors.toList()).stream().map(e -> e.getUbicacion()).anyMatch(ubi -> ubi.coincideCon(servicioPrestado.getUbicacion()));
    }
    return false;
  }

  public void agregarServicio(Servicio servicio){
    this.servicios.add(servicio);
  }

  public void eliminarServicio(Servicio servicio){
    this.eliminarServicioPorID(servicio.getId());
  }

  private void eliminarServicioPorID(int id){
    servicios.stream().filter(servicio -> servicio.getId() == id).toList().forEach(servicio -> servicios.remove(servicio));
  }

  public void agregarEntidad(Entidad entidad){
    this.entidades.add(entidad);
  }

  public void eliminarEntidad(Entidad entidad){
    this.eliminarEntidadPorID(entidad.getId());
  }

  private void eliminarEntidadPorID(int id){
    servicios.stream().filter(entidad -> entidad.getId() == id).toList().forEach(entidad -> entidades.remove(entidad));
  }
}
