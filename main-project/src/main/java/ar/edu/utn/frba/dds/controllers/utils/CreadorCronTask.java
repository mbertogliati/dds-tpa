package ar.edu.utn.frba.dds.controllers.utils;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDateTime;
import java.util.function.Function;

public class CreadorCronTask {
  private Timer timer = new Timer();

  public void crearCronTask(Runnable task, LocalDateTime fechaDesde, long cantSegundos) {
    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        task.run();
      }
    };

    // Obtener la fecha actual
    LocalDateTime ahora = LocalDateTime.now();

    // Calcular el retraso inicial en milisegundos
    long retrasoInicial = calcularRetrasoInicial(ahora, fechaDesde, cantSegundos);

    // Programar la tarea
    timer.schedule(timerTask, retrasoInicial, cantSegundos * 1000);
  }
  public void crearCronTaskSemanal(Runnable task, DayOfWeek dia, LocalTime hora){
    LocalDateTime ahora = LocalDateTime.now();
    LocalDateTime fechaDesde = ahora.with(dia).with(hora);
    long cantSegundos = 7 * 24 * 60 * 60; // Una semana en segundos

    crearCronTask(task, fechaDesde, cantSegundos);
  }
  public void crearCronTaskCadaMinuto(Runnable task, Long cantMinutos){
    crearCronTask(task,LocalDateTime.now(),cantMinutos);
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
}