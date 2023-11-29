package ar.edu.utn.frba.dds.modelos.utilidades;

import ar.edu.utn.frba.dds.controllers.generales.comunidades.CalcularGradoConfianzaController;
import ar.edu.utn.frba.dds.controllers.generales.incidentes.GenerarRankingController;
import ar.edu.utn.frba.dds.controllers.generales.user.NotificacionController;
import ar.edu.utn.frba.dds.server.EntityManagerContext;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cron_task")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class CronTask {
  @Transient
  private Timer timer = new Timer();

  @Transient
  private Runnable runnableTask = null;

  @Column(name = "id")
  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "nombre")
  private String nombre;

  @Column(name ="comando")
  private String comando;

  @Column(name = "fecha_creacion")
  private LocalDateTime fechaCreacion;

  @Column(name = "fecha_modificacion")
  private LocalDateTime fechaModificacion;

  @Column(name = "habilitado")
  private Boolean habilitado;

  protected void iniciarTimer(LocalDateTime fechaDesde, long cantSegundos, EntityManagerFactory entityManagerFactory) {
    System.out.println("[INFO]: iniciando timer para :" + this.toString());
    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        try {
          //esto sería un hilo nuevo?
          EntityManager entityManagerParaEsteHilo = entityManagerFactory.createEntityManager();
          EntityManagerContext.setEntityManager(entityManagerParaEsteHilo);

          if(runnableTask == null) runnableTask = obtenerSubrutina(comando);
          if(runnableTask != null) runnableTask.run();

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
    System.out.println("[INFO]: timer iniciado correctamente para :" + this.toString());
  }

  protected long calcularRetrasoInicial(LocalDateTime ahora, LocalDateTime fechaDesde, long cantSegundos) {
    long retrasoInicial = 0;

    if (ahora.isBefore(fechaDesde)) {
      retrasoInicial = ahora.until(fechaDesde, java.time.temporal.ChronoUnit.SECONDS) * 1000;
    } else {
      // Si la fechaDesde es en el pasado, calcular el retraso hasta la próxima ejecución
      retrasoInicial = (cantSegundos - ahora.until(fechaDesde, java.time.temporal.ChronoUnit.SECONDS)) * 1000;
    }

    return retrasoInicial;
  }

  public abstract void iniciar(EntityManagerFactory entityManagerFactory);

  public void detener() {
    this.timer.cancel();
  }

  @Override
  public String toString() {
    return "[id:" + this.id +
            "; nombre:" + this.nombre +
            "; comando:" + this.comando +
            "; fechaCreacion:" + this.fechaCreacion +
            "; fechaModificacion:" + this.fechaModificacion +
            ";]";
  }

  private Runnable obtenerSubrutina(String comandoCronTask) {
    switch (comandoCronTask) {
      case "generar_ranking_ultima_semana" -> {
        return new GenerarRankingController()::generarRankingUltimaSemana;
      }
      case "calcular_grados_confianza" -> {
        return new CalcularGradoConfianzaController()::calcularGradosDeConfianza;
      }
      case "notificar_usuarios_pendientes" -> {
        return new NotificacionController()::notificarUsuariosPendientes;
      }

      case "notificar_usuarios_al_momento" -> {
        return new NotificacionController()::notificarUsuariosAlMomento;
      }
      default -> { return null; }
    }
  }
}