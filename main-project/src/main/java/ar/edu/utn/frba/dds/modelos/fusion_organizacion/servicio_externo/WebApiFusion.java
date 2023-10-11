package ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_externo;

import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_externo.api_models.RequestOrganizacion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_externo.api_models.RequestSolicitudFusion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_externo.api_models.ResponseFusionCompletada;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_externo.api_models.ResponsePropuestaFusion;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WebApiFusion {
  @POST("/propuestasDeFusion/")
  Call<List<ResponsePropuestaFusion>> obtenerPropuestas(@Body List<RequestOrganizacion> organizaciones);

  @POST("/aceptarFusion/")
  Call<ResponseFusionCompletada> aceptarPropuesta(@Body RequestSolicitudFusion solicitudFusion);

  @POST("/rechazarFusion/")
  Call<String> rechazarPropuesta(@Body String parametro);
}
