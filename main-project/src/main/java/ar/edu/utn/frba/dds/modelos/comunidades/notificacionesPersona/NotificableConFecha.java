package ar.edu.utn.frba.dds.modelos.comunidades.notificacionesPersona;

import ar.edu.utn.frba.dds.modelos.notificaciones.Notificable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notificable_con_fecha")
public class NotificableConFecha {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "fecha", columnDefinition = "TIMESTAMP")
  private LocalDateTime fecha;

  @Column(name = "mensaje")
  private String mensaje;

  public NotificableConFecha(){
    this.fecha = LocalDateTime.now();
  }

  public NotificableConFecha(Notificable notificable){
    this.mensaje = notificable.getInfo();
    this.fecha = LocalDateTime.now();
  }
}
