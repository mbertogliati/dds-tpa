package ar.edu.utn.frba.dds.controllers.utils;

import ar.edu.utn.frba.dds.controllers.generales.comunidades.CalcularGradoConfianzaController;
import ar.edu.utn.frba.dds.controllers.generales.incidentes.GenerarRankingController;
import ar.edu.utn.frba.dds.controllers.generales.user.NotificacionController;
import ar.edu.utn.frba.dds.modelos.utilidades.CronTask;
import ar.edu.utn.frba.dds.modelos.utilidades.CronTaskPorMinuto;
import ar.edu.utn.frba.dds.repositorios.utilidades.CronTaskRepositorio;

import ar.edu.utn.frba.dds.server.EntityManagerContext;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManagerFactory;

public class InicializadorCronTask {
  CronTaskPorMinuto refrescarCronTasks = new CronTaskPorMinuto();
  List<CronTask> cronTasks = new ArrayList<>();
  CronTaskRepositorio repositorio;
  EntityManagerFactory entityManagerFactory;

  public InicializadorCronTask(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory=entityManagerFactory;
    this.repositorio = new CronTaskRepositorio();
  }

  private List<CronTask> inicializarNuevas(List<CronTask> cronTasksNuevas, EntityManagerFactory entityManagerFactory) {
    for(CronTask cronTask : cronTasksNuevas) {
      if(cronTask.getHabilitado())
      {
        this.LogCronTask("[INFO]: Inicializando cron task: ", cronTask);
        cronTask.iniciar(entityManagerFactory);
        this.LogCronTask("[INFO]: Cron task inicializada correctamente: ", cronTask);
      }
    }
    return cronTasksNuevas;
  }

  private void reinicializarExistentes(List<CronTask> cronTasksDb, List<CronTask> cronTasksARefrescar, EntityManagerFactory entityManagerFactory) {
    for(CronTask cronTaskARefrescar : cronTasksARefrescar) {

      Optional<CronTask> optional = cronTasksDb.stream().filter(ct -> ct.getId().equals(cronTaskARefrescar.getId())).findFirst();
      if(optional.isEmpty())
        continue;

      CronTask cronTaskFrescaDb =  optional.get();

      if(cronTaskFrescaDb.getFechaModificacion().isAfter(cronTaskARefrescar.getFechaModificacion())) {
        this.LogCronTask("[INFO]: Se detectaron cambios en cron task: ", cronTaskARefrescar);
        cronTaskARefrescar.detener();
        this.cronTasks.remove(cronTaskARefrescar);
        if(cronTaskFrescaDb.getHabilitado())
          cronTaskFrescaDb.iniciar(entityManagerFactory);
        this.cronTasks.add(cronTaskFrescaDb);
        this.LogCronTask("[INFO]: Se reinicializó cron task: ", cronTaskARefrescar);
      }
    }
  }

  private void eliminarCronTasksBorradas(List<CronTask> cronTasks) {
    for(CronTask cronTask: cronTasks) {
      this.LogCronTask("[INFO]: Deteniendo y eliminando cron task : ", cronTask);
      cronTask.detener();
      this.cronTasks.remove(cronTask);
      this.LogCronTask("[INFO]: Se detuvo y se eliminó la cron task : ", cronTask);
    }
  }

  public void inicializar(EntityManagerFactory entityManagerFactory) {
    try {
      System.out.println("[INFO]: Inicializando Cron Tasks ...");
      this.cronTasks = this.repositorio.obtener();
      this.cronTasks.forEach(ct -> this.repositorio.detach(ct));//TODO: checkear si esto funciona correctamente

      this.cronTasks = this.inicializarNuevas(this.cronTasks, entityManagerFactory);
      this.refrescarCronTasks = new CronTaskPorMinuto();
      this.refrescarCronTasks.setNombre("cron_task_para_refrescar_cron_tasks");
      this.refrescarCronTasks.setCantMinutos(Long.parseLong(System.getenv("CRON_TASK_PERIODO_REFRESCO_MIN")));
      this.refrescarCronTasks.setRunnableTask(() -> this.refrescar(entityManagerFactory));
      this.refrescarCronTasks.iniciar(entityManagerFactory);
      System.out.println("[INFO]: Cron Tasks inicializados correctamente.");
    } catch (Exception ex) {
      System.out.println("[ERROR]: Ocurrió un error en la inicialización de los Cron Tasks.");
      ex.printStackTrace();
    }
  }

  public void refrescar(EntityManagerFactory entityManagerFactory) {
    try {
      System.out.println("[INFO]: Refrescando Cron Tasks ...");
      List<CronTask> cronTasksDb = this.repositorio.obtener();
      cronTasksDb.forEach(ct -> this.repositorio.refresh(ct)); //refresh para obtener info fresca.
      cronTasksDb.forEach(ct -> this.repositorio.detach(ct)); //detach para desconectar las entidades de posibles cambios en DB.

      List<CronTask> cronTasksARefrescar = this.cronTasks.stream()
        .filter(existente ->
            cronTasksDb.stream().anyMatch(ctdb -> ctdb.getId().equals(existente.getId()))
        ).toList();
      List<CronTask> cronTasksNuevas = cronTasksDb.stream()
          .filter(actualizada ->
              this.cronTasks.stream().noneMatch(existente -> existente.getId().equals(actualizada.getId()))
          ).toList();
      List<CronTask> cronTasksBorradas = this.cronTasks.stream()
          .filter(existente ->
              cronTasksDb.stream().noneMatch(ctdb -> ctdb.getId().equals(existente.getId()))
          ).toList();

      this.eliminarCronTasksBorradas(cronTasksBorradas);
      this.reinicializarExistentes(cronTasksDb, cronTasksARefrescar, entityManagerFactory);
      this.cronTasks.addAll(this.inicializarNuevas(cronTasksNuevas, entityManagerFactory));


      System.out.println("[INFO]: Cron Tasks refrescadas correctamente.");
    } catch (Exception ex) {
      System.out.println("[ERROR]: Ocurrió un error refrescando los Cron Tasks.");
      ex.printStackTrace();
    }
  }

  private void LogCronTasks(String mensaje, List<CronTask> cronTasks) {
    for(CronTask ct: cronTasks) this.LogCronTask(mensaje, ct);
  }

  private void LogCronTask(String mensaje, CronTask cronTask) {
    System.out.println(mensaje + cronTask.toString());
  }
}
