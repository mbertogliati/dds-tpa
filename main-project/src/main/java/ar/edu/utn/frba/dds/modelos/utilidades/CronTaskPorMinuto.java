package ar.edu.utn.frba.dds.modelos.utilidades;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
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
  public void iniciar(Runnable task) {
    Long cantSegundos = this.cantMinutos * 60;
    iniciarTimer(task, LocalDateTime.now(), cantSegundos);
  }
}