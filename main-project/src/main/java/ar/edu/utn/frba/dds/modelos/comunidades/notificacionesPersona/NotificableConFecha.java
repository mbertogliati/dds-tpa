package ar.edu.utn.frba.dds.modelos.comunidades.notificacionesPersona;

import ar.edu.utn.frba.dds.modelos.notificaciones.Notificable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notificableConFecha")
public class NotificableConFecha {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "fecha", columnDefinition = "TIMESTAMP")
  private LocalDateTime fecha;

  @Transient
  private Notificable notificable;
}
