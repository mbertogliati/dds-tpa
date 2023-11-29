package ar.edu.utn.frba.dds.modelos.utilidades;

import ar.edu.utn.frba.dds.server.EntityManagerContext;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = "cron_task_por_minuto")
public class CronTaskPorMinuto extends CronTask {

  @Column(name = "cantidad_minutos")
  private Long cantMinutos;

  @Override
  public void iniciar(EntityManagerFactory entityManagerFactory) {
    Long cantSegundos = this.cantMinutos * 60;
    iniciarTimer(LocalDateTime.now(), cantSegundos, entityManagerFactory);
  }
}
