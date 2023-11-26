package ar.edu.utn.frba.dds.controllers.utils;

import ar.edu.utn.frba.dds.controllers.generales.comunidades.CalcularGradoConfianzaController;
import ar.edu.utn.frba.dds.controllers.generales.incidentes.GenerarRankingController;
import ar.edu.utn.frba.dds.controllers.generales.user.NotificacionController;
import ar.edu.utn.frba.dds.modelos.utilidades.CronTask;
import ar.edu.utn.frba.dds.modelos.utilidades.CronTaskPorMinuto;
import ar.edu.utn.frba.dds.repositorios.utilidades.CronTaskRepositorio;
import java.util.ArrayList;
import java.util.List;

public class InicializadorCronTask {
  CronTaskPorMinuto refrescarCronTasks = new CronTaskPorMinuto();
  List<CronTask> cronTasks = new ArrayList<>();
  CronTaskRepositorio repositorio;
  GenerarRankingController generarRankingController;
  NotificacionController notificacionController;
  CalcularGradoConfianzaController calcularGradoConfianzaController;
  public InicializadorCronTask() {
    this.repositorio = new CronTaskRepositorio(new CreadorEntityManager().entityManager());
    this.generarRankingController = new GenerarRankingController(new CreadorEntityManager().entityManagerCreado());
    this.notificacionController = new NotificacionController(new CreadorEntityManager().entityManagerCreado());
    this.calcularGradoConfianzaController = new CalcularGradoConfianzaController(new CreadorEntityManager().entityManagerCreado());
  }

  private Runnable obtenerSubrutina(String comandoCronTask) {
    switch (comandoCronTask) {
      case "generar_ranking_ultima_semana":
        return this.generarRankingController::generarRankingUltimaSemana;
      case "calcular_grados_confianza":
        return this.calcularGradoConfianzaController::calcularGradosDeConfianza;
      case "notificar_usuarios_pendientes":
        return this.notificacionController::notificarUsuariosPendientes;
      case "notificar_usuarios_al_momento":
        return this.notificacionController::notificarUsuariosAlMomento;
      default:
        return null;
    }
  }

  private void inicializarNuevas(List<CronTask> cronTasks) {
    for(CronTask cronTask : cronTasks) {
      if(cronTask.getHabilitado())
      {
        System.out.println("[INFO]: Inicializando Cron Task: " + cronTask.getId() + "-" + cronTask.getNombre());
        cronTask.iniciar(this.obtenerSubrutina(cronTask.getComando()));
        System.out.println("[INFO]: Cron Task inicializado correctamente: " + cronTask.getId() + "-" + cronTask.getNombre());
      }
    }
  }

  private void reinicializarExistentes(List<CronTask> cronTasksActualizadas, List<CronTask> cronTasksARefrescar) {
    for(CronTask cronTaskARefrescar : cronTasksARefrescar) {
      CronTask cronTaskActualizada =  cronTasksActualizadas.stream().filter(actualizada -> actualizada.getId().equals(cronTaskARefrescar.getId())).findFirst().get();
      if(cronTaskActualizada.getFechaModificacion().isAfter(cronTaskARefrescar.getFechaModificacion())) {
        cronTaskARefrescar.detener();
        this.cronTasks.remove(cronTaskARefrescar);
        if(cronTaskActualizada.getHabilitado())
          cronTaskActualizada.iniciar(this.obtenerSubrutina(cronTaskActualizada.getComando()));
        this.cronTasks.add(cronTaskActualizada);
      }
    }
  }

  public void inicializar() {
    try {
      System.out.println("[INFO]: Inicializando Cron Tasks ...");
      this.cronTasks = this.repositorio.obtener();
      this.inicializarNuevas(this.cronTasks);
      this.refrescarCronTasks = new CronTaskPorMinuto();
      this.refrescarCronTasks.setNombre("cron_task_para_refrescar_cron_tasks");
      this.refrescarCronTasks.setCantMinutos(Long.parseLong(System.getenv("CRON_TASK_PERIODO_REFRESCO_MIN")));
      this.refrescarCronTasks.iniciar(this::refrescar);
      System.out.println("[INFO]: Cron Tasks inicializados correctamente.");
    } catch (Exception ex) {
      System.out.println("[ERROR]: Ocurrió un error en la inicialización de los Cron Tasks.");
      ex.printStackTrace();
    }
  }

  public void refrescar() {
    try {
      System.out.println("[INFO]: Refrescando Cron Tasks ...");
      List<CronTask> cronTasksActualizadas = this.repositorio.obtener();
      List<CronTask> cronTasksARefrescar = this.cronTasks.stream().filter(existente -> cronTasksActualizadas.stream().anyMatch(actualizada -> actualizada.getId() == existente.getId())).toList();
      List<CronTask> cronTasksNuevas = cronTasksActualizadas.stream().filter(actualizada -> this.cronTasks.stream().noneMatch(existente -> existente.getId() == actualizada.getId())).toList();

      this.reinicializarExistentes(cronTasksActualizadas, cronTasksARefrescar);
      this.inicializarNuevas(cronTasksNuevas);
      this.cronTasks.addAll(cronTasksNuevas);

      System.out.println("[INFO]: Cron Tasks refrescadas correctamente.");
    } catch (Exception ex) {
      System.out.println("[ERROR]: Ocurrió un error refrescando los Cron Tasks.");
      ex.printStackTrace();
    }
  }
}
