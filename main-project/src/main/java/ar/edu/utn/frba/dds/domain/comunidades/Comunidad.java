package ar.edu.utn.frba.dds.domain.comunidades;

import ar.edu.utn.frba.dds.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.domain.incidentes.IncidenteAbierto;
import ar.edu.utn.frba.dds.domain.incidentes.IncidenteCerrado;
import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;
import ar.edu.utn.frba.dds.domain.servicios.Servicio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.dds.domain.notificaciones.Notificable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comunidades")
@Getter
@Setter
public class Comunidad {
  @Id
  @GeneratedValue
  private int id;

  @Column(name = "detalle")
  private String detalle;

  @ManyToMany
  private List<Servicio> servicios = new ArrayList<>();

  @OneToMany(mappedBy = "comunidad")
  private List<Membresia> membresias = new ArrayList<>();

  @OneToMany
  @JoinColumn(name = "comunidad_id", referencedColumnName = "id")
  private List<IncidentePorComunidad> incidentes = new ArrayList<>();

  public Comunidad(){}

  public Comunidad(String detalle){
    this.detalle = detalle;
  }

  public void notificarMiembros(Notificable notificable){
    this.membresias.stream().map(m -> m.getPersona()).forEach(p -> p.enviarNotificacion(notificable));
  }

  public void cerrarIncidente(Incidente incidente, Persona persona){
    if(this.incidentes.stream().map(ipc -> ipc.getIncidente()).anyMatch(i -> i.equals(incidente))){
      this.incidentes.stream().filter(ipc -> ipc.getIncidente().equals(incidente)).forEach(ipc -> {
        ipc.setEstaCerrado(true);
        ipc.setAutorCierre(persona);
        ipc.setFechaHoraCierre(LocalDateTime.now());
      });
      notificarMiembros(new IncidenteCerrado(incidente));
    }
  }

  public void agregarIncidente(Incidente incidente){
    this.incidentes.add(new IncidentePorComunidad(incidente));
    notificarMiembros(new IncidenteAbierto(incidente));
  }

  public void agregarMembresiaDirecto(Membresia membresia){
    this.membresias.add(membresia);
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

  public void agregarPersona(Persona persona){
    Membresia membresia = new Membresia(this, persona);
    this.membresias.add(membresia);
    persona.agregarMembresiaDirecto(membresia);
  }

  public void agregarMembresia(Membresia membresia){
    this.membresias.add(membresia);
    membresia.getPersona().agregarMembresiaDirecto(membresia);
  }

  public void eliminarMembresiaDirecto(Membresia membresia){
    this.membresias.remove(membresia);
  }

  public void eliminarMembresia(Membresia membresia){
    this.membresias.remove(membresia);
    membresia.getPersona().eliminarMembresiaDirecto(membresia);
  }

  public void eliminarMembresiaPorPersona(Persona persona){
    for (Membresia membresia : membresias){
      if(membresia.getPersona().getUsuario().getId() == persona.getUsuario().getId()){
        this.membresias.remove(membresia);
        membresia.getPersona().eliminarMembresiaDirecto(membresia);
      }
    }
  }

  public boolean tieneIncidente(Incidente incidente) {
    return this.incidentes.stream().map(ipc -> ipc.getIncidente()).anyMatch(i -> i.equals(incidente));
  }
}
