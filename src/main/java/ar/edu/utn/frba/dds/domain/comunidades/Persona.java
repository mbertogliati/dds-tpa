package ar.edu.utn.frba.dds.domain.comunidades;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.servicios.Servicio;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.dds.domain.utilidades.Localizacion;
import lombok.Getter;
import lombok.Setter;

public class Persona {
  @Getter
  private Usuario usuario;
  @Getter @Setter
  private String nombre;
  @Getter @Setter
  private String apellido;
  @Getter @Setter
  private String email;
  @Getter @Setter
  private int whatsapp;
  @Getter
  private Interes interes;
  @Getter @Setter
  private String metodoNotificacion;
  @Getter
  private List<Membresia> membresias = new ArrayList<>();

  public Persona(String nombre, String apellido, String email, Usuario usuario){
    this.nombre = nombre;
    this.apellido = apellido;
    this.email = email;
    this.usuario = usuario;
  }

  public Persona(String nombre, String apellido, String email, Usuario usuario, Localizacion localizacion){
    this.nombre = nombre;
    this.apellido = apellido;
    this.usuario = usuario;
    this.interes = new Interes(localizacion);
  }

  public void agregarServicioDeInteres(Servicio servicio){
    this.interes.agregarServicio(servicio);
  }

  public void agregarEntidadDeInteres(Entidad entidad){
    this.interes.agregarEntidad(entidad);
  }

  public void eliminarServicioDeInteres(Servicio servicio){
    this.interes.eliminarServicio(servicio);
  }

  public void eliminarEntidadDeInteres(Entidad entidad){
    this.interes.eliminarEntidad(entidad);
  }

  public void setLocalizacion(Localizacion localizacion){
    this.interes.setLocalizacion(localizacion);
  }

  public void agregarMembresia(Membresia membresia){
    this.membresias.add(membresia);
    membresia.getComunidad().agregarMembresiaDirecto(membresia);
  }

  public void agregarMembresiaDirecto(Membresia membresia){
    this.membresias.add(membresia);
  }

  public void eliminarMembresiaDirecto(Membresia membresia){
    this.membresias.remove(membresia);
  }

  public void eliminarMembresia(Membresia membresia){
    this.membresias.remove(membresia);
    membresia.getComunidad().eliminarMembresiaDirecto(membresia);
  }

  public void eliminarMembresiaPorComunidad(Comunidad comunidad){
    for (Membresia membresia : membresias){
      if(membresia.getComunidad().getId() == comunidad.getId()){
        this.membresias.remove(membresia);
        membresia.getComunidad().eliminarMembresiaDirecto(membresia);
      }
    }
  }

}
