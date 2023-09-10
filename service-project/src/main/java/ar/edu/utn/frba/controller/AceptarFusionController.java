package ar.edu.utn.frba.controller;

import ar.edu.utn.frba.domain.FusionadorOrganizaciones;
import ar.edu.utn.frba.domain.entidades.OrganizacionesRelacionadas;
import ar.edu.utn.frba.domain.entidades.PropuestaFusion;
import ar.edu.utn.frba.domain.calculadorGradoConfianza.CalculadorGradoConfianza;
import ar.edu.utn.frba.domain.calculadorGradoConfianza.GradoCofianzaPromedioPonderado;
import ar.edu.utn.frba.domain.criterios.CriterioAnd;
import ar.edu.utn.frba.domain.criterios.CriterioFusion;
import ar.edu.utn.frba.domain.criterios.CriterioIgualGradoConfianza;
import ar.edu.utn.frba.domain.criterios.CriterioMinCantMeses;
import ar.edu.utn.frba.domain.criterios.CriterioMinPorcentajeEstablecimientos;
import ar.edu.utn.frba.domain.criterios.CriterioMinPorcentajeServicios;
import ar.edu.utn.frba.domain.criterios.CriterioMinPorcentajeUsuarios;
import ar.edu.utn.frba.exceptions.ErrorDeCriteriosException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class AceptarFusionController implements Handler {
  private ObjectMapper objectMapper;
  private JsonMapper jsonMapper;
  private final CriterioFusion criterioFusion;
  private final CalculadorGradoConfianza calculadorGradoConfianza;
  private FusionadorOrganizaciones fusionadorOrganizaciones;

  public AceptarFusionController(){
    super();

    this.objectMapper = new ObjectMapper();
    this.objectMapper.registerModule(new JavaTimeModule());

    this.jsonMapper = new JsonMapper();
    this.jsonMapper.registerModule(new JavaTimeModule());

    CriterioAnd criterioAnd = new CriterioAnd();
    criterioAnd.agregarCriterios(
        new CriterioMinCantMeses(6L),
        new CriterioMinPorcentajeUsuarios(0.05),
        new CriterioMinPorcentajeEstablecimientos(0.75),
        new CriterioMinPorcentajeServicios(0.75),
        new CriterioIgualGradoConfianza()
    );

    this.criterioFusion = criterioAnd;
    this.calculadorGradoConfianza = new GradoCofianzaPromedioPonderado();

    this.fusionadorOrganizaciones = new FusionadorOrganizaciones();
  }

  @Override
  public void handle(Context context) throws Exception {
    try{

      OrganizacionesRelacionadas relacion = this.objectMapper.readValue(context.body(), OrganizacionesRelacionadas.class);

      PropuestaFusion propuesta = this.fusionadorOrganizaciones.fusionar(relacion, criterioFusion, calculadorGradoConfianza);

      String json = this.jsonMapper.writeValueAsString(propuesta);

      context.json(json);

    } catch(UnrecognizedPropertyException upe) {
      System.out.println("Exception en processing");
      System.out.println(upe.getMessage());
      System.out.println(upe.getPropertyName());
      throw new BadRequestResponse("Propiedad no reconocida: " + upe.getPropertyName());

    } catch(JsonMappingException jme) {
      System.out.println("Exception en mapping");
      System.out.println(jme.getMessage());
      throw new BadRequestResponse("Formato de JSON inválido.");

    } catch(JsonProcessingException jpe) {
      System.out.println("Exception en processing");
      System.out.println(jpe.getMessage());
      throw new BadRequestResponse("Error general al procesar formato JSON.");

    } catch(ErrorDeCriteriosException e){

      context.json(e.getError());
    }
  }
}
