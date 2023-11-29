package ar.edu.utn.frba.dds.repositorios.comunidad;

import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.comunidades.Membresia;
import ar.edu.utn.frba.dds.repositorios.comunidades.ComunidadRepositorio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ComunidadRepositorioTests implements WithSimplePersistenceUnit {
  private ComunidadRepositorio repositorioComunidad;

  @BeforeEach
  public void init() throws IOException {
    this.repositorioComunidad = new ComunidadRepositorio();
  }

  @Test
  @DisplayName("Se puede guardar una comunidad correctamente")
  public void ejecutaGuardarCorrectamente_ConComunidadSimple(){
    //arrange
    Comunidad comunidadNueva = new Comunidad();
    comunidadNueva.setDetalle("Test Comunidad");

    //act
    this.repositorioComunidad.guardar(comunidadNueva);

    Comunidad comunidadRecuperada = this.repositorioComunidad.obtenerPorId(comunidadNueva.getId());

    //assert
    Assertions.assertTrue(comunidadNueva.getId() > 0, "Comunidad: genera id correctamente");
    Assertions.assertNotNull(comunidadRecuperada, "Comunidad: se recupera instancia de la db");
    Assertions.assertEquals("Test Comunidad", comunidadRecuperada.getDetalle(), "Comunidad: detalle guardado correctamente");
  }

  @Test
  @DisplayName("Se puede guardar una comunidad con sus membresías")
  public void ejecutaGuardarCorrectamente_ConComunidadConMembresia(){
    //arrange
    Comunidad comunidadNueva = new Comunidad();
    comunidadNueva.setDetalle("Test Comunidad");

    Membresia membresiaNueva = new Membresia();

    comunidadNueva.getMembresias().add(membresiaNueva);

    //act
    this.repositorioComunidad.guardar(comunidadNueva);

    Comunidad comunidadRecuperada = this.repositorioComunidad.obtenerPorId(comunidadNueva.getId());
    Membresia membresiaRecuperada = comunidadRecuperada.getMembresias().stream().findFirst().get();

    //assert
    Assertions.assertTrue(comunidadNueva.getId() > 0, "Comunidad: genera id correctamente");
    Assertions.assertNotNull(comunidadRecuperada, "Comunidad: se recupera instancia de la db");
    Assertions.assertEquals("Test Comunidad", comunidadRecuperada.getDetalle(), "Comunidad: detalle guardado correctamente");

    Assertions.assertTrue(membresiaNueva.getId() > 0, "Membresia: genera id correctamente");
    Assertions.assertNotNull(membresiaRecuperada, "Membresia: se recupera instancia de la db");
  }

  @Test
  @DisplayName("Se puede actualizar una comunidad correctamente")
  public void ejecutaActualizarCorrectamente_ConComunidadSimple(){
    //arrange
    Comunidad comunidadNueva = new Comunidad();
    comunidadNueva.setDetalle("Test Comunidad");

    //act
    this.repositorioComunidad.guardar(comunidadNueva);
    Comunidad comunidadAModificar = this.repositorioComunidad.obtenerPorId(comunidadNueva.getId());
    comunidadAModificar.setDetalle("Test Comunidad Actualizada");
    this.repositorioComunidad.actualizar(comunidadAModificar);
    Comunidad comunidadRecuperada = this.repositorioComunidad.obtenerPorId(comunidadAModificar.getId());

    //assert
    Assertions.assertEquals("Test Comunidad Actualizada", comunidadRecuperada.getDetalle(), "Comunidad: detalle guardado correctamente");
  }


  @Test
  @DisplayName("Se puede eliminar una comunidad correctamente")
  public void ejecutaEliminarCorrectamente_ConComunidadSimple(){
    //arrange
    Comunidad comunidadNueva = new Comunidad();
    comunidadNueva.setDetalle("Test Comunidad");

    //act
    this.repositorioComunidad.guardar(comunidadNueva);
    this.repositorioComunidad.eliminar(comunidadNueva);

    Comunidad comunidadRecuperada = this.repositorioComunidad.obtenerPorId(comunidadNueva.getId());

    //assert
    Assertions.assertNull(comunidadRecuperada, "Comunidad: la entidad es eliminada");
  }
}
