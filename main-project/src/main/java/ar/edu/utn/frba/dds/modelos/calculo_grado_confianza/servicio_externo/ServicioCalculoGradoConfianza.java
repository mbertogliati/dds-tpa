package ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.servicio_externo;

import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.AdapterCalculoGradoConfianza;
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
import ar.edu.utn.frba.dds.modelos.utilidades.HttpStatusCode;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioCalculoGradoConfianza implements AdapterCalculoGradoConfianza {
  @Getter
  @Setter
  private String urlApi = "http://127.0.0.1:8000";
  private WebApiGradoConfianza webApiGradoConfianza;

  public ServicioCalculoGradoConfianza() {
    Retrofit retrofitBuilder = new Retrofit.Builder()
        .baseUrl(urlApi)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    this.webApiGradoConfianza = retrofitBuilder.create(WebApiGradoConfianza.class);
  }
  @Override
  public ResultadoConfianza calcular(ParametroConfianza parametro) {
    try {
      RequestGradoConfianza request = this.mapParametroARequest(parametro);
      Call<ResponseGradoConfianza> call = this.webApiGradoConfianza.calcularGradoConfianza(request);
      Response<ResponseGradoConfianza> response = call.execute();
      if(response.code() == HttpStatusCode.OK)
        return this.mapResponseAResultado(response.body());
      else if (response.code() == HttpStatusCode.ERROR_SERVER_CANT_PROCESS_REQUEST) {
        System.out.println("El servidor no pudo procesar el pedido: debe haber algun error en el payload.");
        return null; //TODO: evaluar si devolver null está bien
      } else {
        System.out.println("Error no determinado:\n\tcodigo = " + response.code());
        System.out.println("\tmensaje = " + response.message());
        return null;
      }

    } catch (IOException exception) {
      System.out.println(exception);
    }
    return null; //TODO: evaluar si devolver null está bien
  }

  private RequestGradoConfianza mapParametroARequest(ParametroConfianza parametro) {
    RequestGradoConfianza request = new RequestGradoConfianza();
    request.setUsuarios(parametro.getUsuarios().stream().map(ServicioCalculoGradoConfianza::mapUsuarioAEvaluarARequestUsuario).toList());
    request.setIncidentes(parametro.getIncidentes().stream().map(ServicioCalculoGradoConfianza::mapIncidentePorComunidadARequestIncidente).toList());
    return request;
  }

  private static RequestUsuario mapUsuarioAEvaluarARequestUsuario(UsuarioAEvaluar usuarioAEvaluar) {
    RequestUsuario requestUsuario = new RequestUsuario();
    requestUsuario.id = usuarioAEvaluar.usuario.getId();
    requestUsuario.puntajeInicial = usuarioAEvaluar.puntajeInicial;
    return requestUsuario;
  }

  private static RequestIncidente mapIncidentePorComunidadARequestIncidente(IncidentePorComunidad incidentePorComunidad) {
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

  private ResultadoConfianza mapResponseAResultado(ResponseGradoConfianza response) {
    ResultadoConfianza resultadoConfianza = new ResultadoConfianza();
    resultadoConfianza.nivel = response.nivel;
    resultadoConfianza.usuarios = response.usuarios.stream().map(ServicioCalculoGradoConfianza::mapResponseUsuarioAUsuarioEvaluado).toList();
    resultadoConfianza.nivel = response.nivel;
    return resultadoConfianza;
  }

  private static UsuarioEvaluado mapResponseUsuarioAUsuarioEvaluado(ResponseUsuario responseUsuario) {
    UsuarioEvaluado usuarioEvaluado = new UsuarioEvaluado();
    usuarioEvaluado.id = responseUsuario.getId();
    usuarioEvaluado.puntajeInicial = responseUsuario.getPuntajeInicial();
    usuarioEvaluado.puntajeFinal = responseUsuario.getPuntajeFinal();
    usuarioEvaluado.nivelConfianza = responseUsuario.getNivelConfianza();
    usuarioEvaluado.recomendacion = responseUsuario.getRecomendacion();
    return usuarioEvaluado;
  }

  private static String mapLocalDateTimeAString(LocalDateTime localDateTime) {
    //Ejemplo formato: "2018-03-14T13:00"
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    String resultado = localDateTime.format(formatter);
    return resultado;
  }
}