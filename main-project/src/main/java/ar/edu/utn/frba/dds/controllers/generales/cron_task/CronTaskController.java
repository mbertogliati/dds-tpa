package ar.edu.utn.frba.dds.controllers.generales.cron_task;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.server.EntityManagerContext;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.server.EntityManagerContext;
import ar.edu.utn.frba.dds.dtos.CrearCronTaskDTO;
import ar.edu.utn.frba.dds.dtos.CronTaskDTO;
import ar.edu.utn.frba.dds.dtos.EditarCronTaskDTO;
import ar.edu.utn.frba.dds.dtos.OpcionGenericaDTO;
import ar.edu.utn.frba.dds.modelos.utilidades.CronTask;
import ar.edu.utn.frba.dds.modelos.utilidades.CronTaskPorMinuto;
import ar.edu.utn.frba.dds.modelos.utilidades.CronTaskPorSegundo;
import ar.edu.utn.frba.dds.modelos.utilidades.CronTaskSemanal;
import ar.edu.utn.frba.dds.repositorios.utilidades.CronTaskRepositorio;
import ar.edu.utn.frba.dds.server.EntityManagerContext;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.server.EntityManagerContext;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import javax.persistence.EntityManager;
import java.util.List;

public class CronTaskController implements ICrudViewsHandler {
  private CronTaskRepositorio repositorio;
  private Map<String, String> dicComandos;
  private Map<String, String> dicTipos;
  private DayOfWeek[] dias;
  private Locale locale;

  public CronTaskController() {
    this.repositorio = new CronTaskRepositorio();

    this.dicComandos = new Hashtable<String, String>();
    this.dicComandos.put("generar_ranking_ultima_semana", "Generar ranking de ultima semana");
    this.dicComandos.put("calcular_grados_confianza", "Calcular grados de confianza");
    this.dicComandos.put("notificar_usuarios_pendientes", "Notificar usuarios pendientes");
    this.dicComandos.put("notificar_usuarios_al_momento", "Notificar usuarios al momento");

    this.dicTipos = new Hashtable<String, String>();
    this.dicTipos.put("CronTaskPorMinuto", "Por minuto");
    this.dicTipos.put("CronTaskPorSegundo", "Por segundo");
    this.dicTipos.put("CronTaskSemanal", "Semanal");

    this.dias = DayOfWeek.values();
    this.locale = new Locale.Builder().setLanguage("es").setRegion("AR").build(); //locale argentina
  }

  @Override
  public void index(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);
    List<CronTask> cronTasks = repositorio.obtener();
    List<CronTaskDTO> cronTaskDTOS = cronTasks.stream().map(this::generarCronTaskDTO).toList();
    model.put("cronTasksDTO", cronTaskDTOS);
    context.render("cron_task/listar.hbs", model);
  }

  @Override
  public void show(Context context) {

  }

  @Override
  public void create(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    CrearCronTaskDTO crearCronTaskDTO = new CrearCronTaskDTO();

    this.dicComandos.forEach((key, value) -> {
      crearCronTaskDTO.getComandos().add(new OpcionGenericaDTO(key, value));
    });

    this.dicTipos.forEach((key, value) -> {
      crearCronTaskDTO.getTipos().add(new OpcionGenericaDTO(key, value));
    });

    Arrays.stream(this.dias).toList().stream().forEach(v -> {
      crearCronTaskDTO.getDias().add(
          new OpcionGenericaDTO(
              String.valueOf(v.ordinal()),
              v.getDisplayName(TextStyle.FULL, this.locale))
          );
    });

    model.put("crearCronTaskDTO", crearCronTaskDTO);

    context.render("cron_task/crear.hbs", model);
  }

  @Override
  public void save(Context context) {
    String tipoCronTask = context.formParam("tipo");
    CronTask cronTask = this.construirCronTaskPorTipo(tipoCronTask, context);
    repositorio.guardar(cronTask);
    context.redirect("/cron-task");
  }

  @Override
  public void edit(Context context) {
    Long id = Long.parseLong(context.pathParam("id"));
    CronTask cronTask = repositorio.obtenerPorId(id);
    Map<String, Object> model = GeneradorModel.model(context);
    EditarCronTaskDTO editarCronTaskDTO = this.generarEditarCronTaskDTO(cronTask);
    model.put("editarCronTaskDTO", editarCronTaskDTO);
    context.render("cron_task/editar.hbs", model);
  }

  @Override
  public void update(Context context) {
    Long id = Long.parseLong(context.pathParam("id"));
    String tipoCronTask = context.formParam("tipo");
    CronTask cronTask = repositorio.obtenerPorId(id);
    String className = cronTask.getClass().getSimpleName();
    if(className.equals(tipoCronTask)) {
      this.actualizarPorClassName(cronTask, context);
    } else {
      this.reinstanciarPorCambioDeTipo(tipoCronTask, cronTask, context);
    }
    context.redirect("/cron-task");
  }

  private void actualizarPorClassName(CronTask cronTask, Context context) {
    this.asignarPropiedadesComunes(cronTask, context, false);
    this.asignarPropiedadesSegunTipo(cronTask, context);
    repositorio.actualizar(cronTask);
  }

  private void reinstanciarPorCambioDeTipo(String tipoCronTask, CronTask cronTask, Context context) {
    //instanciar nueva cron task con el nuevo tipo y sus atributos
    CronTask nuevaCronTask = this.construirCronTaskPorTipo(tipoCronTask, context);
    repositorio.guardar(nuevaCronTask);
    repositorio.borrar(cronTask); //eliminar vieja cron task
  }

  @Override
  public void delete(Context context) {
    Long id = Long.parseLong(context.pathParam("id"));
    CronTask cronTask = repositorio.obtenerPorId(id);
    repositorio.borrar(cronTask);
    context.redirect("/cron-task");
  }

  public void habilitar(Context context) {
    Long id = Long.parseLong(context.pathParam("id"));
    CronTask cronTask = repositorio.obtenerPorId(id);
    cronTask.setHabilitado(!cronTask.getHabilitado());
    cronTask.setFechaModificacion(LocalDateTime.now());
    repositorio.actualizar(cronTask);
    context.redirect("/cron-task");
  }

  private String obtenerDescripcionTipo(CronTask cronTask) {
    String className = cronTask.getClass().getSimpleName();
    if(className.equals("CronTaskPorMinuto")) return "Por minuto";
    if(className.equals("CronTaskPorSegundo")) return "Por segundo";
    if(className.equals("CronTaskSemanal")) return "Semanal";
    return "";
  }

  private CronTaskDTO generarCronTaskDTO(CronTask cronTask) {
    if(cronTask == null) return null;
    CronTaskDTO dto = new CronTaskDTO();
    dto.setId(cronTask.getId());
    dto.setNombre(cronTask.getNombre());
    dto.setComando(this.dicComandos.get(cronTask.getComando()));
    dto.setTipo(this.obtenerDescripcionTipo(cronTask));
    dto.setHabilitado(cronTask.getHabilitado());
    dto.setFechaCreacion(cronTask.getFechaCreacion());
    dto.setFechaModificacion(cronTask.getFechaModificacion());
    return dto;
  }

  private EditarCronTaskDTO generarEditarCronTaskDTO(CronTask cronTask) {
    if(cronTask == null) return null;
    EditarCronTaskDTO dto = new EditarCronTaskDTO();
    dto.setId(cronTask.getId());
    dto.setNombre(cronTask.getNombre());
    dto.setHabilitado(cronTask.getHabilitado());
    this.dicComandos.forEach((key, value) -> {
      dto.getComandos().add(new OpcionGenericaDTO(key, value));
    });
    dto.getComandos().stream()
        .filter(c -> c.getId().equals(cronTask.getComando()))
        .findFirst()
        .get()
        .setSeleccionado(true);

    this.dicTipos.forEach((key, value) -> {
      dto.getTipos().add(new OpcionGenericaDTO(key, value));
    });
    dto.getTipos().stream()
        .filter(t -> t.getId().equals(cronTask.getClass().getSimpleName()))
        .findFirst()
        .get()
        .setSeleccionado(true);

    Arrays.stream(this.dias).toList().stream().forEach(v -> {
      dto.getDias().add(
          new OpcionGenericaDTO(
              String.valueOf(v.ordinal()),
              v.getDisplayName(TextStyle.FULL, this.locale))
      );
    });

    String className = cronTask.getClass().getSimpleName();
    if(className.equals("CronTaskPorSegundo")) {
      CronTaskPorSegundo ct = (CronTaskPorSegundo) cronTask;
      dto.setSegundos(ct.getCantSegundos());
    }else if (className.equals("CronTaskPorMinuto")) {
      CronTaskPorMinuto ct = (CronTaskPorMinuto) cronTask;
      dto.setMinutos(ct.getCantMinutos());
    }else if (className.equals("CronTaskSemanal")) {
      CronTaskSemanal ct = (CronTaskSemanal) cronTask;
      dto.setHorario(ct.getHorario().toString());
      dto.getDias().stream()
          .filter(d -> d.getId().equals(String.valueOf(ct.getDia().ordinal())))
          .findFirst()
          .get()
          .setSeleccionado(true);
    }

    return dto;
  }

  private CronTask construirCronTaskPorTipo(String tipo, Context context) {
    CronTask cronTask = null;
    if (tipo.equals("CronTaskPorMinuto")) {
      CronTaskPorMinuto cronTaskPorMinuto = new CronTaskPorMinuto();
      this.asignarPropiedadesComunes(cronTaskPorMinuto, context, true);
      this.asignarPropiedadesSegunTipo(cronTaskPorMinuto, context);
      cronTask = cronTaskPorMinuto;
    }else if (tipo.equals("CronTaskPorSegundo")) {
      CronTaskPorSegundo cronTaskPorSegundo = new CronTaskPorSegundo();
      this.asignarPropiedadesComunes(cronTaskPorSegundo, context, true);
      this.asignarPropiedadesSegunTipo(cronTaskPorSegundo, context);
      cronTask = cronTaskPorSegundo;
    }else if (tipo.equals("CronTaskSemanal")) {
      CronTaskSemanal cronTaskSemanal = new CronTaskSemanal();
      this.asignarPropiedadesComunes(cronTaskSemanal, context, true);
      this.asignarPropiedadesSegunTipo(cronTaskSemanal, context);
      cronTask = cronTaskSemanal;
    }
    return cronTask;
  }

  private CronTask asignarPropiedadesComunes(CronTask cronTask, Context context, Boolean asignarFechaCreacion) {
    cronTask.setNombre(context.formParam("nombre"));
    cronTask.setComando(context.formParam("comando"));
    if(asignarFechaCreacion)
      cronTask.setFechaCreacion(LocalDateTime.now());
    cronTask.setFechaModificacion(LocalDateTime.now());
    cronTask.setHabilitado(Boolean.parseBoolean(context.formParam("habilitado")));
    return cronTask;
  }

  private CronTask asignarPropiedadesSegunTipo(CronTask cronTask, Context context) {
    String className = cronTask.getClass().getSimpleName();
    if (className.equals("CronTaskPorMinuto")) {
      CronTaskPorMinuto cronTaskPorMinuto = (CronTaskPorMinuto) cronTask;
      cronTaskPorMinuto.setCantMinutos(Long.parseLong(context.formParam("minutos")));
    }else if (className.equals("CronTaskPorSegundo")) {
      CronTaskPorSegundo cronTaskPorSegundo = (CronTaskPorSegundo) cronTask;
      cronTaskPorSegundo.setCantSegundos(Long.parseLong(context.formParam("segundos")));
    }else if (className.equals("CronTaskSemanal")) {
      CronTaskSemanal cronTaskSemanal = (CronTaskSemanal) cronTask;
      cronTaskSemanal.setDia(this.ordinalADayOfWeek(Integer.parseInt(context.formParam("dia"))));
      cronTaskSemanal.setHorario(LocalTime.parse(context.formParam("horario")));
    }
    return cronTask;
  }

  private DayOfWeek ordinalADayOfWeek(Integer ordinal) {
    DayOfWeek valor = this.dias[ordinal];
    return valor;
  }
}
