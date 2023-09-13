package ar.edu.utn.frba.dds.repositorios.incidentes;

import ar.edu.utn.frba.dds.domain.incidentes.Incidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IncidenteRepositorioTests implements WithSimplePersistenceUnit {

  private IncidenteRepositorio repositorioIncidente;

  @BeforeEach
  public void init() throws IOException {
    this.repositorioIncidente = new IncidenteRepositorio(entityManager());
  }

  @Test
  @DisplayName("Se puede guardar un incidente correctamente")
  public void ejecutaGuardarCorrectamente_ConIncidenteSimple(){
    //arrange
    Incidente incidenteNuevo = new Incidente();
    incidenteNuevo.setObservaciones("Test Incidente");

    //act
    this.repositorioIncidente.guardar(incidenteNuevo);

    Incidente incidenteRecuperado = this.repositorioIncidente.buscarPorId(incidenteNuevo.getId());

    //assert
    Assertions.assertTrue(incidenteNuevo.getId() > 0, "Incidente: genera id correctamente");
    Assertions.assertNotNull(incidenteRecuperado, "Incidente: se recupera instancia de la db");
    Assertions.assertEquals("Test Incidente", incidenteRecuperado.getObservaciones(), "Incidente: nombre guardado correctamente");
  }

  @Test
  @DisplayName("Se puede actualizar un incidente correctamente")
  public void ejecutaActualizarCorrectamente_ConIncidenteSimple(){
    //arrange
    Incidente incidenteNuevo = new Incidente();
    incidenteNuevo.setObservaciones("Test Incidente");

    //act
    this.repositorioIncidente.guardar(incidenteNuevo);
    Incidente entidadAModificar = this.repositorioIncidente.buscarPorId(incidenteNuevo.getId());
    entidadAModificar.setObservaciones("Test Incidente Actualizada");
    this.repositorioIncidente.actualizar(entidadAModificar);
    Incidente incidenteRecuperado = this.repositorioIncidente.buscarPorId(entidadAModificar.getId());

    //assert
    Assertions.assertEquals("Test Incidente Actualizada", incidenteRecuperado.getObservaciones(), "Incidente: nombre guardado correctamente");
  }


  @Test
  @DisplayName("Se puede eliminar un incidente correctamente")
  public void ejecutaEliminarCorrectamente_ConIncidenteSimple(){
    //arrange
    Incidente incidenteNuevo = new Incidente();
    incidenteNuevo.setObservaciones("Test Incidente");

    //act
    this.repositorioIncidente.guardar(incidenteNuevo);
    this.repositorioIncidente.eliminar(incidenteNuevo);

    Incidente incidenteRecuperado = this.repositorioIncidente.buscarPorId(incidenteNuevo.getId());

    //assert
    Assertions.assertNull(incidenteRecuperado, "Incidente: la entidad es eliminada");
  }

}
