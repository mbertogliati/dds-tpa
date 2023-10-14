package ar.edu.utn.frba.dds.modelos.comunidades;

import ar.edu.utn.frba.dds.modelos.base.ModelBase;
import ar.edu.utn.frba.dds.modelos.comunidades.notificacionesPersona.NotificablesParaCronTaskAlMomento;
import ar.edu.utn.frba.dds.modelos.comunidades.notificacionesPersona.NotificacionAlMomento;
import ar.edu.utn.frba.dds.repositorios.converters.EstrategiaMomentoNotificacionConverter;
import ar.edu.utn.frba.dds.modelos.comunidades.notificacionesPersona.EstrategiaMomentoNotificacion;
import ar.edu.utn.frba.dds.modelos.comunidades.notificacionesPersona.ListadoNotificables;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.modelos.notificaciones.Notificable;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.modelos.utilidades.FechasDeSemana;
import ar.edu.utn.frba.dds.modelos.utilidades.Ubicacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "personas")

@Getter
@Setter
public class Persona extends ModelBase {

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "apellido")
  private String apellido;

  @Column(name = "email")
  private String email;

  @Column(name = "whatsapp")
  private int whatsapp;

  @Embedded
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  private Interes interes;

  @Column(name = "metodoNotificacion")
  private String metodoNotificacion = "MAIL";

  @Convert(converter = EstrategiaMomentoNotificacionConverter.class)
  @Column(name = "momentoNotificacion")
  private EstrategiaMomentoNotificacion estrategiaMomentoNotificacion = new NotificacionAlMomento();

  @OneToMany(mappedBy = "persona", cascade = { CascadeType.ALL })
  private List<Membresia> membresias = new ArrayList<>();

  @Embedded
  private Ubicacion ultimaUbicacion;

  @OneToOne
  @JoinColumn(name = "listado_id")
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  private ListadoNotificables notificablesSinNotificar = new ListadoNotificables();

  @OneToMany(mappedBy = "persona", cascade = {CascadeType.ALL})
  private List<NotificablesParaCronTaskAlMomento> notificablesAlMomento = new ArrayList<>();

  @OneToMany
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  @JoinColumn(name = "persona_id", referencedColumnName = "id")
  private List<FechasDeSemana> fechas;

  public Persona(){}

  public ListadoNotificables getNotificablesSinNotificar(){
    if(this.notificablesSinNotificar == null){
      this.notificablesSinNotificar = new ListadoNotificables();
    }
    return this.notificablesSinNotificar;
  }

  public Persona(String nombre, String apellido) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.email = "";
    this.whatsapp = 0;
    this.ultimaUbicacion = new Ubicacion(0.0f, 0.0f);
    this.interes = new Interes();
    this.notificablesSinNotificar = new ListadoNotificables();
    this.fechas = new ArrayList<>();
  }

  @Override
  public void setActivo(Boolean valor){
    super.setActivo(valor);
    this.membresias.forEach(m -> m.setActivo(valor));
  }

  public void agregarFechaDeNotificacion(FechasDeSemana fecha){
    this.fechas.add(fecha);
  }

  public void eliminarFechaDeNotificacion(FechasDeSemana fecha){
    this.fechas.remove(fecha);
  }

  public void agregarEntidadInteres(Entidad entidad){
    this.interes.agregarEntidad(entidad);
  }
  public void eliminarEntidadInteres(Entidad entidad){
    this.interes.eliminarEntidad(entidad);
  }
  public void agregarServicioInteres(ServicioPrestado servicio){
    this.interes.agregarServicio(servicio);
  }
  public void eliminarServicioInteres(ServicioPrestado servicio){
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

  public void agregarServicioDeInteres(ServicioPrestado servicio) {
    this.interes.agregarServicio(servicio);
  }

  public void agregarEntidadDeInteres(Entidad entidad) {
    this.interes.agregarEntidad(entidad);
  }

  public void eliminarServicioDeInteres(ServicioPrestado servicio) {
    this.interes.eliminarServicio(servicio);
  }

  public void eliminarEntidadDeInteres(Entidad entidad) {
    this.interes.eliminarEntidad(entidad);
  }

  public void setUbicacion(float lat, float lon){
    this.ultimaUbicacion = new Ubicacion(lat, lon);
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
