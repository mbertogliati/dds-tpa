package ar.edu.utn.frba.dds.controllers.generales.user;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.dtos.RespuestaGenericaDTO;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.utilidades.CalculadoraDistanciaEnMetros;
import ar.edu.utn.frba.dds.modelos.utilidades.Coordenada;
import ar.edu.utn.frba.dds.modelos.utilidades.EvaluadorSolicitudRevision;
import ar.edu.utn.frba.dds.repositorios.comunidades.PersonaRepositorio;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class LocalizacionUsuarioController implements Handler {
  private ObjectMapper objectMapper;
  private JsonMapper jsonMapper;
  private EvaluadorSolicitudRevision evaluador;
  private PersonaRepositorio repositorio;
  public LocalizacionUsuarioController(EntityManager entityManager) {
    this.objectMapper = new ObjectMapper();
    this.jsonMapper = new JsonMapper();
    this.evaluador = new EvaluadorSolicitudRevision(new CalculadoraDistanciaEnMetros());
    this.repositorio = new PersonaRepositorio(entityManager);
  }

  private void responder(Context context, boolean exito, String mensaje) throws JsonProcessingException {
    RespuestaGenericaDTO respuesta = new RespuestaGenericaDTO();
    respuesta.setExito(exito);
    respuesta.setMensaje(mensaje);
    String json = this.jsonMapper.writeValueAsString(respuesta);
    context.json(json);
  }

  @Override
  public void handle(@NotNull Context context) throws Exception {

    try {

      Coordenada coordenada = this.objectMapper.readValue(context.body(), Coordenada.class);
      Usuario usuario = context.sessionAttribute("usuario");

      if(usuario == null) {
        this.responder(context, false, "Usuario no encontrado en sesión");
        return;
      }

      Persona personaAsociada = usuario.getPersonaAsociada();
      if(personaAsociada == null) {
        this.responder(context, false, "Usuario no tiene persona asociada");
        return;
      }

      Persona persona = this.repositorio.buscarPorId(personaAsociada.getId());

      if (persona != null) {
        persona.getUltimaUbicacion().getCoordenada().setLatitudLongitud(coordenada.getLatitud(), coordenada.getLongitud());

        //1. actualizo coordenadas de persona
        this.repositorio.actualizar(persona);

        //2. detecto incidentes cercanos con su nueva ubicación
        evaluador.evaluarSolicitudRevision(persona);
      }

      this.responder(context, true, "Localización actualizada");
    } catch(UnrecognizedPropertyException upe) {
      System.out.println("Excepción: propiedad no reconocida");
      System.out.println(upe.getMessage());
      System.out.println(upe.getPropertyName());
      this.responder(context, false, "Propiedad no reconocida");
    } catch(JsonMappingException jme) {
      System.out.println("Excepción en mapping");
      System.out.println(jme.getMessage());
      this.responder(context, false, "Error al mappear");
    } catch(JsonProcessingException jpe) {
      System.out.println("Excepción en processing");
      System.out.println(jpe.getMessage());
      this.responder(context, false, "Error al procesar");
    } catch(Exception ex) {
      System.out.println("Excepción general");
      ex.printStackTrace();
      this.responder(context, false, "Error general");
    }
  }
}