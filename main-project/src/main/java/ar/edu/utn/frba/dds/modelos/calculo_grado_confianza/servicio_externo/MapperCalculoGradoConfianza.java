package ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.servicio_externo;

import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.ParametroConfianza;
import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.ResultadoConfianza;
import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.UsuarioAEvaluar;
import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.UsuarioEvaluado;
import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.servicio_externo.api_models.RequestGradoConfianza;
import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.servicio_externo.api_models.RequestIncidente;
import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.servicio_externo.api_models.RequestUsuario;
import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.servicio_externo.api_models.ResponseGradoConfianza;
import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.servicio_externo.api_models.ResponseUsuario;
import ar.edu.utn.frba.dds.modelos.incidentes.IncidentePorComunidad;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MapperCalculoGradoConfianza {

  public static RequestGradoConfianza mapParametroARequest(ParametroConfianza parametro) {
    RequestGradoConfianza request = new RequestGradoConfianza();
    request.setUsuarios(parametro.getUsuarios().stream().map(MapperCalculoGradoConfianza::mapUsuarioAEvaluarARequestUsuario).toList());
    request.setIncidentes(parametro.getIncidentes().stream().map(MapperCalculoGradoConfianza::mapIncidentePorComunidadARequestIncidente).toList());
    return request;
  }

  public static RequestUsuario mapUsuarioAEvaluarARequestUsuario(UsuarioAEvaluar usuarioAEvaluar) {
    RequestUsuario requestUsuario = new RequestUsuario();
    requestUsuario.id = usuarioAEvaluar.usuario.getId();
    requestUsuario.puntajeInicial = usuarioAEvaluar.puntajeInicial;
    return requestUsuario;
  }

  public static RequestIncidente mapIncidentePorComunidadARequestIncidente(IncidentePorComunidad incidentePorComunidad) {
    RequestIncidente requestIncidente = new RequestIncidente();
    requestIncidente.id = incidentePorComunidad.getId();
    //TODO: cómo mapear varios servicios prestados afectados con sus establecimientos y servicio afectados al request
    requestIncidente.idEstablecimiento = Integer.toString(incidentePorComunidad.getIncidente().getServiciosAfectados().get(0).getEstablecimiento().getId());
    requestIncidente.idServicioAfectado = Integer.toString(incidentePorComunidad.getIncidente().getServiciosAfectados().get(0).getId());
    requestIncidente.fechaApertura = mapLocalDateTimeAString(incidentePorComunidad.getIncidente().getFechaHoraApertura());
    requestIncidente.fechaCierre = mapLocalDateTimeAString(incidentePorComunidad.getFechaHoraCierre());
    requestIncidente.idUsuarioApertura = Integer.toString(incidentePorComunidad.getIncidente().getAutorApertura().getId());
    requestIncidente.idUsuarioCierre = Integer.toString(incidentePorComunidad.getAutorCierre().getId());
    return requestIncidente;
  }

  public static ResultadoConfianza mapResponseAResultado(ResponseGradoConfianza response) {
    ResultadoConfianza resultadoConfianza = new ResultadoConfianza();
    resultadoConfianza.nivel = response.nivel;
    resultadoConfianza.usuarios = response.usuarios.stream().map(MapperCalculoGradoConfianza::mapResponseUsuarioAUsuarioEvaluado).toList();
    resultadoConfianza.nivel = response.nivel;
    return resultadoConfianza;
  }

  public static UsuarioEvaluado mapResponseUsuarioAUsuarioEvaluado(ResponseUsuario responseUsuario) {
    UsuarioEvaluado usuarioEvaluado = new UsuarioEvaluado();
    usuarioEvaluado.id = responseUsuario.getId();
    usuarioEvaluado.puntajeInicial = responseUsuario.getPuntajeInicial();
    usuarioEvaluado.puntajeFinal = responseUsuario.getPuntajeFinal();
    usuarioEvaluado.nivelConfianza = responseUsuario.getNivelConfianza();
    usuarioEvaluado.recomendacion = responseUsuario.getRecomendacion();
    return usuarioEvaluado;
  }

  public static String mapLocalDateTimeAString(LocalDateTime localDateTime) {
    //Ejemplo formato: "2018-03-14T13:00"
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    String resultado = localDateTime.format(formatter);
    return resultado;
  }
}
