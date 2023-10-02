package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.comunidades.notificacionesPersona.NotificacionAlMomento;
import ar.edu.utn.frba.dds.modelos.notificaciones.Notificador;
import ar.edu.utn.frba.dds.repositorios.comunidades.PersonaRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.UsuarioRepositorio;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import net.bytebuddy.asm.Advice;

public class NotificacionController {
  private PersonaRepositorio repoPersona;
  private Notificador notificador;

  public NotificacionController(EntityManager entityManager){
    repoPersona = new PersonaRepositorio(entityManager);
  }

  public void notificarUsuariosPendientes(){
    LocalDateTime horaInicioProceso = LocalDateTime.now();
    List<Persona> personas = repoPersona.buscarTodas();

    for(Persona persona : personas){
      if(persona.getFechas().stream().anyMatch(fecha -> fecha.sePuedeNotificar(horaInicioProceso))){
        Notificador.notificar(persona.getNotificablesSinNotificar(),persona);
        repoPersona.actualizar(persona);
      }
    }

  }
}

