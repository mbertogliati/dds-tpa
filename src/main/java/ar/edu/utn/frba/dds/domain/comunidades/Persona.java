package ar.edu.utn.frba.dds.domain.comunidades;

import ar.edu.utn.frba.dds.domain.comunidades.notificacionesPersona.TiempoNotificacion;
import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.servicios.Servicio;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
import ar.edu.utn.frba.dds.notificaciones.Notificable;
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
  @Getter @Setter
  private Interes interes;
  @Getter @Setter
  private String metodoNotificacion;
  @Getter @Setter
  private TiempoNotificacion tiempoNotificacion;
  @Getter
  private List<Membresia> membresias = new ArrayList<>();
  @Getter @Setter
  private Ubicacion ubicacionActual;

  public Persona(String nombre, String apellido, Usuario usuario) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.usuario = usuario;
    this.email = "";
    this.ubicacionActual = new Ubicacion(0.0f, 0.0f);
  }

  public void enviarNotificacion(Notificable notificable) {
    this.tiempoNotificacion.notificar(notificable, this);
  }

  public void agregarServicioDeInteres(Servicio servicio) {
    this.interes.agregarServicio(servicio);
  }

  public void agregarEntidadDeInteres(Entidad entidad) {
    this.interes.agregarEntidad(entidad);
  }

  public void eliminarServicioDeInteres(Servicio servicio) {
    this.interes.eliminarServicio(servicio);
  }

  public void eliminarEntidadDeInteres(Entidad entidad) {
    this.interes.eliminarEntidad(entidad);
  }

  public void agregarMembresia(Membresia membresia) {
    this.membresias.add(membresia);
    membresia.getComunidad().agregarMembresiaDirecto(membresia);
  }

  public void agregarMembresiaDirecto(Membresia membresia) {
    this.membresias.add(membresia);
  }

  public void eliminarMembresiaDirecto(Membresia membresia) {
    this.membresias.remove(membresia);
  }

  public void eliminarMembresia(Membresia membresia) {
    this.membresias.remove(membresia);
    membresia.getComunidad().eliminarMembresiaDirecto(membresia);
  }

  public void eliminarMembresiaPorComunidad(Comunidad comunidad) {
    for (Membresia membresia : membresias) {
      if (membresia.getComunidad().getId() == comunidad.getId()) {
        this.membresias.remove(membresia);
        membresia.getComunidad().eliminarMembresiaDirecto(membresia);
      }
    }
  }

}
