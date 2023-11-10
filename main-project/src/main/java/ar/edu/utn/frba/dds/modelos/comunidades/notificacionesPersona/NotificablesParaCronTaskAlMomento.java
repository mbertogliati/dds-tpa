package ar.edu.utn.frba.dds.modelos.comunidades.notificacionesPersona;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.notificaciones.Notificable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "notificables_al_momento", schema = "public")
public class NotificablesParaCronTaskAlMomento {
  @Id
  @GeneratedValue
  private int id;

  @Column(name = "mensaje")
  private String mensaje;

  @ManyToOne
  @JoinColumn(name = "persona_id", referencedColumnName = "id")
  private Persona persona;

  public NotificablesParaCronTaskAlMomento(){}

  public NotificablesParaCronTaskAlMomento(Notificable notificable, Persona persona){
    this.persona=persona;
    this.mensaje=notificable.getInfo();
  }
}
