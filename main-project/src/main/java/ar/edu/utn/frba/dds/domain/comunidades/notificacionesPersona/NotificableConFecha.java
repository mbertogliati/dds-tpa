package ar.edu.utn.frba.dds.domain.comunidades.notificacionesPersona;

import ar.edu.utn.frba.dds.domain.notificaciones.Notificable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
  @GeneratedValue
  private int id;

  @Column(name = "fecha", columnDefinition = "TIMESTAMP")
  private LocalDateTime fecha;

  @Transient
  private Notificable notificable;
}
