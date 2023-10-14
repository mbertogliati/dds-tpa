package ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_fusion_g19;

import ar.edu.utn.frba.dds.modelos.fusion_organizacion.ErrorFusionException;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_fusion_g19.api_models.RequestOrganizacion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_fusion_g19.api_models.RequestSolicitudFusion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_fusion_g19.api_models.ResponseFusionCompletada;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_fusion_g19.api_models.ResponsePropuestaFusion;
import ar.edu.utn.frba.dds.modelos.utilidades.HttpStatusCode;
import java.io.IOException;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioFusion implements AdapterFusion {
  @Getter
  @Setter
  private String urlApi = "http://127.0.0.1:8080";

  private WebApiFusion webApiFusion;

  public ServicioFusion() {
    Retrofit retrofitBuilder = new Retrofit.Builder()
        .baseUrl(urlApi)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    this.webApiFusion = retrofitBuilder.create(WebApiFusion.class);
  }

  @Override
  public List<PropuestaFusion> obtenerPropuestas(List<Organizacion> organizaciones) {
    try {
      List<RequestOrganizacion> requests = organizaciones.stream().map(MapperFusion::mapOrganizacionAFusionarARequestOrganizacion).toList();
      Call<List<ResponsePropuestaFusion>> call = this.webApiFusion.obtenerPropuestas(requests);
      Response<List<ResponsePropuestaFusion>> response = call.execute();
      if(response.code() == HttpStatusCode.OK)
        return response.body().stream().map(MapperFusion::mapResponsePropuestaFusionAPropuestaFusion).toList();
      else
        System.out.println("Error al obtener las propuestas de fusión: código=" + response.code() + " - mensaje= " + response.message());
    } catch(IOException exception) {
      System.out.println(exception);
    }
    return null;
  }

  @Override
  public FusionCompletada aceptarFusion(SolicitudFusion solicitud) {
    try {
      RequestSolicitudFusion request = MapperFusion.mapSolicitudFusionARequestSolicitudFusion(solicitud);
      Call<ResponseFusionCompletada> call = this.webApiFusion.aceptarPropuesta(request);
      Response<ResponseFusionCompletada> response = call.execute();

      if(response.code() == HttpStatusCode.OK) {
        return MapperFusion.mapResponseFusionCompletadaAFusionCompletada(response.body());
      }else {
        System.out.println("Error al aceptar la propuesta de fusión: código=" + response.code() + " - mensaje= " + response.message());
        throw new ErrorFusionException();
      }
    } catch(IOException exception) {
      System.out.println(exception);
      throw new ErrorFusionException();
    }
  }

  @Override
  public String rechazarFusion(String parametro) {
    return null;
  }
}
