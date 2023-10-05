package ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.servicio_externo;

import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.servicio_externo.api_models.RequestGradoConfianza;
import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.servicio_externo.api_models.ResponseGradoConfianza;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WebApiGradoConfianza {
  @POST("/comunidad/usuarios/")
  Call<ResponseGradoConfianza> calcularGradoConfianza(@Body RequestGradoConfianza request);
}
