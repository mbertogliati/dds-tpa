package ar.edu.utn.frba.controller;

import ar.edu.utn.frba.domain.criterios.CriterioOr;
import ar.edu.utn.frba.domain.entidades.Organizacion;
import ar.edu.utn.frba.domain.entidades.OrganizacionesRelacionadas;
import ar.edu.utn.frba.domain.RelacionadorOrganizaciones;
import ar.edu.utn.frba.domain.calculadorGradoConfianza.CalculadorGradoConfianza;
import ar.edu.utn.frba.domain.calculadorGradoConfianza.GradoCofianzaPromedioPonderado;
import ar.edu.utn.frba.domain.criterios.CriterioAnd;
import ar.edu.utn.frba.domain.criterios.CriterioFusion;
import ar.edu.utn.frba.domain.criterios.CriterioIgualGradoConfianza;
import ar.edu.utn.frba.domain.criterios.CriterioMinCantMeses;
import ar.edu.utn.frba.domain.criterios.CriterioMinPorcentajeEstablecimientos;
import ar.edu.utn.frba.domain.criterios.CriterioMinPorcentajeServicios;
import ar.edu.utn.frba.domain.criterios.CriterioMinPorcentajeUsuarios;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.*;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.List;

public class OrganizacionesRelacionadasController implements Handler {
  private ObjectMapper objectMapper;
  private JsonMapper jsonMapper;
  private final CriterioFusion criterioFusion;
  private final CalculadorGradoConfianza calculadorGradoConfianza;
  private RelacionadorOrganizaciones relacionadorOrganizaciones;

  public OrganizacionesRelacionadasController(){
    super();

    this.objectMapper = new ObjectMapper();
    this.objectMapper.registerModule(new JavaTimeModule());

    this.jsonMapper = new JsonMapper();
    this.jsonMapper.registerModule(new JavaTimeModule());

    CriterioAnd criterioAnd = new CriterioAnd();
    criterioAnd.agregarCriterios(
        new CriterioMinPorcentajeUsuarios(0.05),
        new CriterioMinPorcentajeEstablecimientos(0.75),
        new CriterioMinPorcentajeServicios(0.75),
        new CriterioIgualGradoConfianza(),
        new CriterioMinCantMeses(6L)
    );

    this.criterioFusion = criterioAnd;
    this.calculadorGradoConfianza = new GradoCofianzaPromedioPonderado();

    this.relacionadorOrganizaciones = new RelacionadorOrganizaciones();
  }

  @Override
  public void handle(Context context) throws Exception {
    try {
      List<Organizacion> listado = this.objectMapper.readValue(context.body(), new TypeReference<List<Organizacion>>() {
      });
      List<OrganizacionesRelacionadas> propuestas = this.relacionadorOrganizaciones.getPosiblesFusiones(listado, criterioFusion);
      String json = this.jsonMapper.writeValueAsString(propuestas);
      context.result(json);
    } catch(UnrecognizedPropertyException upe) {
      System.out.println("Exception en processing");
      System.out.println(upe.getMessage());
      System.out.println(upe.getPropertyName());
      throw new BadRequestResponse("Propiedad no reconocida:" + upe.getPropertyName());
    } catch(JsonMappingException jme) {
      System.out.println("Exception en mapping");
      System.out.println(jme.getMessage());
      throw new BadRequestResponse("Formato de JSON inválido.");
    } catch(JsonProcessingException jpe) {
      System.out.println("Exception en processing");
      System.out.println(jpe.getMessage());
      throw new BadRequestResponse("Error general al procesar formato JSON.");
    }
  }
}
