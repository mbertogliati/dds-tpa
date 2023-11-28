package ar.edu.utn.frba.dds.controllers.generales.user;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.notificacionesPersona.NotificablesParaCronTaskAlMomento;
import ar.edu.utn.frba.dds.modelos.notificaciones.Notificador;
import ar.edu.utn.frba.dds.repositorios.comunidades.PersonaRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.notificacionesPersona.NotificablesParaCronTaskAlMomentoRepositorio;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;

public class NotificacionController {
  private PersonaRepositorio repoPersona;
  private NotificablesParaCronTaskAlMomentoRepositorio repoAlMomento;
  private Notificador notificador;

  public NotificacionController(EntityManager entityManager){
    repoPersona = new PersonaRepositorio(entityManager);
    repoAlMomento = new NotificablesParaCronTaskAlMomentoRepositorio(entityManager);
  }

  public void notificarUsuariosPendientes() {
    System.out.println("[INFO]: Notificando usuarios pendientes...");

    LocalDateTime horaInicioProceso = LocalDateTime.now();
    List<Persona> personas = repoPersona.buscarTodas();

    System.out.println("[INFO]: Cantidad de personas encontradas:" + personas.size());

    for(Persona persona : personas){
      if(persona.getFechas() != null && persona.getFechas().stream().anyMatch(fecha -> fecha.sePuedeNotificar(horaInicioProceso))){
        Notificador.notificar(persona.getNotificablesSinNotificar(),persona);
        repoPersona.actualizar(persona);
      }
    }
    System.out.println("[INFO]: Usuarios pendientes notificados correctamente.");
  }

  public void notificarUsuariosAlMomento() {
    System.out.println("[INFO]: Notificando usuarios al momento...");
    List<NotificablesParaCronTaskAlMomento> notificablesPendientes = repoAlMomento.obtenerTodos();

    System.out.println("[INFO]: Cantidad de notificables pendientes encontrados:" + notificablesPendientes.size());

    notificablesPendientes.forEach(n -> {
        Notificador.notificar(n.getMensaje(), n.getPersona());
        repoAlMomento.eliminar(n);
    });

    System.out.println("[INFO]: Usuarios notificados al momento correctamente.");
  }
}

