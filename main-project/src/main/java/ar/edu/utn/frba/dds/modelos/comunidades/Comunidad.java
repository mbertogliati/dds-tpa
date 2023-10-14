package ar.edu.utn.frba.dds.modelos.comunidades;

import ar.edu.utn.frba.dds.modelos.base.ModelBase;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.UltimoIntentoFusionComunidad;
import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.modelos.incidentes.IncidenteAbierto;
import ar.edu.utn.frba.dds.modelos.incidentes.IncidenteCerrado;
import ar.edu.utn.frba.dds.modelos.incidentes.IncidentePorComunidad;
import ar.edu.utn.frba.dds.modelos.notificaciones.Notificable;
import ar.edu.utn.frba.dds.modelos.servicios.Servicio;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Comunidad extends ModelBase {
  @Column(name = "nombre")
  private String nombre;

  @Column(name = "detalle")
  private String detalle;

  @ManyToMany
  private List<Servicio> servicios = new ArrayList<>();

  @OneToMany(mappedBy = "comunidad", cascade = { CascadeType.ALL })
  private List<Membresia> membresias = new ArrayList<>();

  @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
  @JoinColumn(name = "comunidad_id", referencedColumnName = "id")
  private List<IncidentePorComunidad> incidentes = new ArrayList<>();

  @Column(name = "gradoConfianza")
  private Double gradoConfianza;

  @OneToMany(cascade = { CascadeType.ALL })
  @JoinColumn(name = "comunidad1_id", referencedColumnName = "id")
  private List<UltimoIntentoFusionComunidad> intentosFusion = new ArrayList<>();

  public Comunidad(){}

  public Comunidad(String detalle){
    this.detalle = detalle;
  }

  public Membresia getMembresia(Persona persona){
    if(this.membresias.stream().filter(m -> m.getPersona().getId() == persona.getId()).toList().isEmpty()){
      return null;
    }else{
      return this.membresias.stream().filter(m -> m.getPersona().getId() == persona.getId()).toList().get(0);
    }
  }

  public void agregarMembresiasDe(Comunidad comunidad1, Comunidad comunidad2){
    comunidad1.getMembresias().forEach(m -> this.agregarPersona(m.getPersona(), m.getRolComunidad()));
    List<Integer> miembros = this.membresias.stream().map(m -> m.getPersona().getId()).toList();
    comunidad2.getMembresias().stream().filter(m -> !miembros.contains(m.getPersona().getId())).forEach(m -> this.agregarPersona(m.getPersona(), m.getRolComunidad()));
  }

  public void agregarIncidentesDe(Comunidad comunidad1, Comunidad comunidad2){
    Set<IncidentePorComunidad> incidentesPorComunidad = new HashSet<>();
    incidentesPorComunidad.addAll(comunidad1.getIncidentes());
    incidentesPorComunidad.addAll(comunidad2.getIncidentes());
    this.incidentes = incidentesPorComunidad.stream().toList();
  }

  public void agregarServiciosDe(Comunidad comunidad1, Comunidad comunidad2){
    Set<Servicio> servicios = new HashSet<>();
    servicios.addAll(comunidad1.getServicios());
    servicios.addAll(comunidad2.getServicios());
    this.servicios = servicios.stream().toList();
  }

  public void notificarMiembros(Notificable notificable){
    this.membresias.stream().map(m -> m.getPersona()).forEach(
        p -> p.enviarNotificacion(notificable));
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

  public void agregarPersona(Persona persona, Rol rol){
    Membresia membresia = new Membresia(this, persona, rol);
    this.membresias.add(membresia);
    //persona.agregarMembresiaDirecto(membresia);
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
      if(membresia.getPersona().getId() == persona.getId()){
        this.membresias.remove(membresia);
        membresia.getPersona().eliminarMembresiaDirecto(membresia);
      }
    }
  }

  public boolean tieneIncidente(Incidente incidente) {
    return this.incidentes.stream().map(ipc -> ipc.getIncidente()).anyMatch(i -> i.equals(incidente));
  }

  public IncidentePorComunidad obtenerIncidenteComunidad(Incidente incidente){
    if(this.tieneIncidente(incidente)){
      return this.incidentes.stream().filter(ipc -> ipc.getIncidente().getId() == incidente.getId()).toList().get(0);
    }
    return null;
  }

  public void agregarIntentoFusion(LocalDateTime fecha, Comunidad comunidad2) {
    this.intentosFusion.add(new UltimoIntentoFusionComunidad(comunidad2, fecha));
  }
}
