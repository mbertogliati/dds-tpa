package ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.servicio_externo;

import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.AdapterCalculoGradoConfianza;
import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.ParametroConfianza;
import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.ResultadoConfianza;
import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.servicio_externo.api_models.RequestGradoConfianza;
import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.servicio_externo.api_models.ResponseGradoConfianza;
import ar.edu.utn.frba.dds.modelos.utilidades.HttpStatusCode;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioCalculoGradoConfianza implements AdapterCalculoGradoConfianza {
  @Getter
  @Setter
  private String urlApi = "http://190.49.3.55:25564";
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
      RequestGradoConfianza request = MapperCalculoGradoConfianza.mapParametroARequest(parametro);
      Call<ResponseGradoConfianza> call = this.webApiGradoConfianza.calcularGradoConfianza(request);
      Response<ResponseGradoConfianza> response = call.execute();

      if(response.code() == HttpStatusCode.OK)
        return MapperCalculoGradoConfianza.mapResponseAResultado(response.body());
      else if (response.code() == HttpStatusCode.ERROR_SERVER_CANT_PROCESS_REQUEST) {
        System.out.println("El servidor no pudo procesar el pedido: debe haber algun error en el payload.");
      } else {
        System.out.println("Error no determinado:\n\tcodigo = " + response.code());
        System.out.println("\tmensaje = " + response.message());
      }

    } catch (IOException exception) {
      System.out.println(exception);
    }
    return null;
  }

}