package ar.edu.utn.frba.dds.domain.comunidades;

import ar.edu.utn.frba.dds.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.domain.incidentes.IncidenteAbierto;
import ar.edu.utn.frba.dds.domain.incidentes.IncidenteCerrado;
import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;
import ar.edu.utn.frba.dds.domain.servicios.Servicio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.edu.utn.frba.dds.notificaciones.Notificable;
import lombok.Getter;
import lombok.Setter;

public class Comunidad {
  @Getter @Setter
  private int id;
  @Getter @Setter
  private String detalle;
  @Getter
  private List<Servicio> servicios = new ArrayList<>();
  @Getter
  private List<Membresia> membresias = new ArrayList<>();
  @Getter
  private List<IncidentePorComunidad> incidentes = new ArrayList<>();

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
        ipc.setFechaCierre(new Date());
      });
    }
  }

  public void agregarIncidente(Incidente incidente){
    this.incidentes.add(new IncidentePorComunidad(incidente));
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
}
