package ar.edu.utn.frba.controller;

import ar.edu.utn.frba.domain.entidades.OrganizacionesRelacionadas;
import ar.edu.utn.frba.domain.entidades.PropuestaFusion;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.openapi.HttpMethod;
import io.javalin.openapi.OpenApi;
import io.javalin.openapi.OpenApiContent;
import io.javalin.openapi.OpenApiRequestBody;
import io.javalin.openapi.OpenApiResponse;

public class RechazarFusionController implements Handler {

  public RechazarFusionController(){
    super();
  }

  @OpenApi(
      summary = "Rechaza una propuesta de fusión",
      operationId = "rechazarFusion",
      path = "/rechazarFusion",
      methods = HttpMethod.POST,
      tags = { "fusion" }
      //requestBody = @OpenApiRequestBody(content = {@OpenApiContent(from = String.class)}),
      /*responses = {
          @OpenApiResponse(status = "200", content = {@OpenApiContent(from = String.class)})
      }*/
  )
  @Override
  public void handle(Context context) throws Exception {
    context.json("Fusión rechazada correctamente.");
  }
}
