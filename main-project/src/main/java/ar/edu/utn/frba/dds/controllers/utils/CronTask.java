package ar.edu.utn.frba.dds.controllers.utils;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

public class CronTask {
  private Timer timer = new Timer();

  public void inicializar(Runnable task, LocalDateTime fechaDesde, long cantSegundos) {
    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        try {
          task.run();
        } catch (Exception ex) {
          System.out.println("[ERROR]: Una excepción ocurrió cuando se ejecutaba la tarea programada.");
          System.out.println(ex.getMessage());
          ex.printStackTrace();
        }
      }
    };

    // Obtener la fecha actual
    LocalDateTime ahora = LocalDateTime.now();

    // Calcular el retraso inicial en milisegundos
    long retrasoInicial = calcularRetrasoInicial(ahora, fechaDesde, cantSegundos);

    // Programar la tarea
    timer.schedule(timerTask, retrasoInicial, cantSegundos * 1000);
  }
  public void inicializarSemanalmente(Runnable task, DayOfWeek dia, LocalTime hora){
    LocalDateTime ahora = LocalDateTime.now();
    LocalDateTime fechaDesde = ahora.with(dia).with(hora);
    long cantSegundos = 7 * 24 * 60 * 60; // Una semana en segundos

    inicializar(task, fechaDesde, cantSegundos);
  }
  public void inicializarPorPeriodoEnMinutos(Runnable task, Long cantMinutos) {
    Long cantSegundos = cantMinutos * 60;
    inicializar(task,LocalDateTime.now(),cantSegundos);
  }

  private long calcularRetrasoInicial(LocalDateTime ahora, LocalDateTime fechaDesde, long cantSegundos) {
    long retrasoInicial = 0;

    if (ahora.isBefore(fechaDesde)) {
      retrasoInicial = ahora.until(fechaDesde, java.time.temporal.ChronoUnit.SECONDS) * 1000;
    } else {
      // Si la fechaDesde es en el pasado, calcular el retraso hasta la próxima ejecución
      retrasoInicial = (cantSegundos - ahora.until(fechaDesde, java.time.temporal.ChronoUnit.SECONDS)) * 1000;
    }

    return retrasoInicial;
  }

  public void detener() {
    this.timer.cancel();
  }
}