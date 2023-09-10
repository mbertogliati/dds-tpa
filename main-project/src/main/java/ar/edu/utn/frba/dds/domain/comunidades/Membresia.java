package ar.edu.utn.frba.dds.domain.comunidades;

import ar.edu.utn.frba.dds.domain.servicios.Servicio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "membresias")
@Getter
@Setter
public class Membresia {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "persona_id", referencedColumnName = "id")
  private Persona persona;

  @ManyToOne
  @JoinColumn(name = "comunidad_id", referencedColumnName = "id")
  private Comunidad comunidad;

  @ManyToOne
  @JoinColumn(name = "rolComunidad_id", referencedColumnName = "id")
  private Rol rolComunidad;

  @ManyToMany
  private List<Servicio> serviciosObservados;

  public Membresia(){}

  public Membresia(Comunidad comunidad, Persona persona){
    this.persona = persona;
    this.comunidad = comunidad;
    serviciosObservados = new ArrayList<>();
  }

  public void agregarServicioObservado(Servicio servicio){
    this.serviciosObservados.add(servicio);
  }

  public void agregarServicioAfectado(Servicio servicio){
    if(this.serviciosObservados.contains(servicio)){
      this.serviciosObservados.remove(servicio);
    }
  }

  public boolean estaAfectado(Servicio servicio){
    return !serviciosObservados.contains(servicio);
  }

}
