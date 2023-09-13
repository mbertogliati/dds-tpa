package ar.edu.utn.frba.dds.repositorios.entidades;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.entidades.Establecimiento;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EntidadRepositorioTests implements WithSimplePersistenceUnit {
  private EntidadRepositorio repositorio;
  @BeforeEach
  public void init() throws IOException {
    this.repositorio = new EntidadRepositorio(entityManager());
  }

  @Test
  @DisplayName("Se puede guardar una entidad correctamente")
  public void ejecutaGuardarCorrectamente_ConEntidadSimple(){
    //arrange
    Entidad entidadNueva = new Entidad();
    entidadNueva.setNombre("Test Entidad");

    //act
    this.repositorio.guardar(entidadNueva);

    Entidad entidadRecuperada = this.repositorio.buscarPorId(entidadNueva.getId());

    //assert
    Assertions.assertTrue(entidadNueva.getId() > 0, "Entidad: genera id correctamente");
    Assertions.assertNotNull(entidadRecuperada, "Entidad: se recupera instancia de la db");
    Assertions.assertEquals("Test Entidad", entidadRecuperada.getNombre(), "Entidad: nombre guardado correctamente");
  }

  @Test
  @DisplayName("Se puede guardar una entidad correctamente con sus establecimientos")
  public void ejecutaGuardarCorrectamente_ConEntidadConEstablecimiento(){
    //arrange
    Entidad entidadNueva = new Entidad();
    entidadNueva.setNombre("Test Entidad");

    Establecimiento establecimientoNuevo = new Establecimiento();
    establecimientoNuevo.setNombre("Test Establecimiento");

    entidadNueva.getEstablecimientos().add(establecimientoNuevo);

    //act
    this.repositorio.guardar(entidadNueva);

    Entidad entidadRecuperada = this.repositorio.buscarPorId(entidadNueva.getId());
    Establecimiento establecimientoRecuperado = entidadRecuperada.getEstablecimientos().stream().findFirst().get();

    //assert
    Assertions.assertTrue(entidadNueva.getId() > 0, "Entidad: genera id correctamente");
    Assertions.assertNotNull(entidadRecuperada, "Entidad: se recupera instancia de la db");
    Assertions.assertEquals("Test Entidad", entidadRecuperada.getNombre(), "Entidad: nombre guardado correctamente");

    Assertions.assertTrue(establecimientoNuevo.getId() > 0, "Establecimiento: genera id correctamente");
    Assertions.assertNotNull(establecimientoRecuperado, "Establecimiento: se recupera instancia de la db");
    Assertions.assertEquals("Test Establecimiento", establecimientoRecuperado.getNombre(), "Establecimiento: nombre guardado correctamente");
  }

  @Test
  @DisplayName("Se puede actualizar una entidad correctamente")
  public void ejecutaActualizarCorrectamente_ConEntidadSimple(){
    //arrange
    Entidad entidadNueva = new Entidad();
    entidadNueva.setNombre("Test Entidad");

    //act
    this.repositorio.guardar(entidadNueva);
    Entidad entidadAModificar = this.repositorio.buscarPorId(entidadNueva.getId());
    entidadAModificar.setNombre("Test Entidad Actualizada");
    this.repositorio.actualizar(entidadAModificar);
    Entidad entidadRecuperada = this.repositorio.buscarPorId(entidadAModificar.getId());

    //assert
    Assertions.assertEquals("Test Entidad Actualizada", entidadRecuperada.getNombre(), "Entidad: nombre guardado correctamente");
  }


  @Test
  @DisplayName("Se puede eliminar una entidad correctamente")
  public void ejecutaEliminarCorrectamente_ConOrganismoControlSimple(){
    //arrange
    Entidad entidadNueva = new Entidad();
    entidadNueva.setNombre("Test Entidad");

    //act
    this.repositorio.guardar(entidadNueva);
    this.repositorio.eliminar(entidadNueva);

    Entidad entidadRecuperada = this.repositorio.buscarPorId(entidadNueva.getId());

    //assert
    Assertions.assertNull(entidadRecuperada, "Entidad: la entidad es eliminada");
  }
}
