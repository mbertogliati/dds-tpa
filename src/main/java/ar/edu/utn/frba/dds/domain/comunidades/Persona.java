package ar.edu.utn.frba.dds.domain.comunidades;

import ar.edu.utn.frba.dds.domain.comunidades.notificacionesPersona.EstrategiaMomentoNotificacion;
import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.domain.servicios.Servicio;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.dds.domain.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
import ar.edu.utn.frba.dds.notificaciones.Notificable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "personas")
@Getter
@Setter
public class Persona {
  @Id
  @GeneratedValue
  private int id;

  @Transient
  private Usuario usuario;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "apellido")
  private String apellido;

  @Column(name = "email")
  private String email;

  @Column(name = "whatsapp")
  private int whatsapp;

  @Transient
  private Interes interes;

  @Column(name = "metodoNotificacion")
  private String metodoNotificacion;

  @Transient
  private EstrategiaMomentoNotificacion estrategiaMomentoNotificacion;

  @OneToMany(mappedBy = "persona")
  private List<Membresia> membresias = new ArrayList<>();

  @Transient
  private Ubicacion ubicacionActual;

  public Persona(){}

  public Persona(String nombre, String apellido) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.email = "";
    this.whatsapp = 0;
    this.ubicacionActual = new Ubicacion(0.0f, 0.0f);
    this.interes = new Interes();
  }

  public void agregarEntidadInteres(Entidad entidad){
    this.interes.agregarEntidad(entidad);
  }
  public void eliminarEntidadInteres(Entidad entidad){
    this.interes.eliminarEntidad(entidad);
  }
  public void agregarServicioInteres(Servicio servicio){
    this.interes.agregarServicio(servicio);
  }
  public void eliminarServicioInteres(Servicio servicio){
    this.interes.eliminarServicio(servicio);
  }

  public boolean servicioPrestadoEsDeInteres(ServicioPrestado servicioPrestado){
    return this.interes.servicioPrestadoEsDeInteres(servicioPrestado);
  }

  public void enviarNotificacion(Notificable notificable) {
    this.estrategiaMomentoNotificacion.notificar(notificable, this);
  }

  public void cerrarIncidente(Incidente incidente){
    this.membresias.stream().map(m -> m.getComunidad()).filter(c -> c.tieneIncidente(incidente)).forEach(c -> c.cerrarIncidente(incidente, this));
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

  public void setUbicacion(float lat, float lon){
    this.ubicacionActual = new Ubicacion(lat, lon);
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
