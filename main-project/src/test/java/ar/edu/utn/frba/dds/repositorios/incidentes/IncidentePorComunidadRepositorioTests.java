package ar.edu.utn.frba.dds.repositorios.incidentes;

import ar.edu.utn.frba.dds.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IncidentePorComunidadRepositorioTests implements WithSimplePersistenceUnit {
  private IncidentePorComunidadRepositorio repositorioIncidentePorComunidad;
  private IncidenteRepositorio repositorioIncidente;

  @BeforeEach
  public void init() throws IOException {
    this.repositorioIncidentePorComunidad = new IncidentePorComunidadRepositorio(entityManager());
    this.repositorioIncidente = new IncidenteRepositorio(entityManager());
  }

  @Test
  @DisplayName("Se puede guardar un incidente por comunidad correctamente")
  public void ejecutaGuardarCorrectamente_ConIncidentePorComunidadSimple(){
    //arrange
    Incidente incidenteNuevo = new Incidente();
    incidenteNuevo.setObservaciones("Test Incidente");
    this.repositorioIncidente.guardar(incidenteNuevo);

    IncidentePorComunidad incidentePorComunidadNuevo = new IncidentePorComunidad();
    incidentePorComunidadNuevo.setIncidente(incidenteNuevo);

    //act

    this.repositorioIncidentePorComunidad.guardar(incidentePorComunidadNuevo);

    IncidentePorComunidad incidentePorComunidadRecuperado = this.repositorioIncidentePorComunidad.buscarPorId(incidentePorComunidadNuevo.getId());

    //assert
    Assertions.assertTrue(incidentePorComunidadNuevo.getId() > 0, "Incidente: genera id correctamente");
    Assertions.assertNotNull(incidentePorComunidadRecuperado, "Incidente: se recupera instancia de la db");
  }

  @Test
  @DisplayName("Se puede actualizar un incidente por comunidad correctamente")
  public void ejecutaActualizarCorrectamente_ConIncidentePorComunidadSimple(){
    //arrange
    IncidentePorComunidad incidentePorComunidad = new IncidentePorComunidad();
    incidentePorComunidad.setFechaHoraCierre(this.obtenerFechaYHora("1000-01-01 00:00"));

    //act
    this.repositorioIncidentePorComunidad.guardar(incidentePorComunidad);
    IncidentePorComunidad incidentePorComunidadAModificar = this.repositorioIncidentePorComunidad.buscarPorId(incidentePorComunidad.getId());
    incidentePorComunidadAModificar.setEstaCerrado(true);
    this.repositorioIncidentePorComunidad.actualizar(incidentePorComunidadAModificar);
    IncidentePorComunidad incidentePorComunidadRecuperado = this.repositorioIncidentePorComunidad.buscarPorId(incidentePorComunidadAModificar.getId());

    //assert
    Assertions.assertEquals(this.obtenerFechaYHora("1000-01-01 00:00"), incidentePorComunidadRecuperado.getFechaHoraCierre(), "Incidente: fecha hora cierre guardado correctamente");
  }


  @Test
  @DisplayName("Se puede eliminar un incidente por comunidad correctamente")
  public void ejecutaEliminarCorrectamente_ConIncidentePorComunidadSimple(){
    //arrange
    IncidentePorComunidad incidentePorComunidadNuevo = new IncidentePorComunidad();
    incidentePorComunidadNuevo.setEstaCerrado(false);

    //act
    this.repositorioIncidentePorComunidad.guardar(incidentePorComunidadNuevo);
    this.repositorioIncidentePorComunidad.eliminar(incidentePorComunidadNuevo);

    IncidentePorComunidad incidentePorComunidadRecuperado = this.repositorioIncidentePorComunidad.buscarPorId(incidentePorComunidadNuevo.getId());

    //assert
    Assertions.assertNull(incidentePorComunidadRecuperado, "Incidente: la entidad es eliminada");
  }

  private LocalDateTime obtenerFechaYHora(String expresion) {
    //String str = "1986-04-08 12:30";
    DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime fechaHora = LocalDateTime.parse(expresion, formateador);
    return fechaHora;
  }
}
