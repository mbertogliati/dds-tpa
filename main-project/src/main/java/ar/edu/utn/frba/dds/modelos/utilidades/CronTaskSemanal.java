package ar.edu.utn.frba.dds.modelos.utilidades;

import ar.edu.utn.frba.dds.repositorios.converters.DiaDeSemanaConverter;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = "cron_task_semanal")
public class CronTaskSemanal extends CronTask {

  @Column(name = "dia_semana")
  @Convert(converter = DiaDeSemanaConverter.class)
  private DayOfWeek dia;

  @Column(name = "horario", columnDefinition = "TIME")
  private LocalTime horario;

  @Override
  public void iniciar(Runnable task) {
    LocalDateTime ahora = LocalDateTime.now();
    LocalDateTime fechaDesde = ahora.with(this.dia).with(this.horario);
    long cantSegundos = 7 * 24 * 60 * 60; // Una semana en segundos
    iniciarTimer(task, fechaDesde, cantSegundos);
  }
}
