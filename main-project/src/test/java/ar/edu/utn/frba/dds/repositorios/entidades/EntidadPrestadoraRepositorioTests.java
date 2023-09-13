package ar.edu.utn.frba.dds.repositorios.entidades;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.entidades.EntidadPrestadora;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EntidadPrestadoraRepositorioTests  implements WithSimplePersistenceUnit {
  private EntidadPrestadoraRepositorio repositorio;

  @BeforeEach
  public void init() throws IOException {
    EntityManager entityManager = entityManager();
    entityManager.setFlushMode(FlushModeType.COMMIT);
    this.repositorio = new EntidadPrestadoraRepositorio(entityManager);
  }

  @Test
  @DisplayName("Se puede guardar una entidad prestadora correctamente")
  public void ejecutaGuardarCorrectamente_ConEntidadPrestadoraSimple(){
    //arrange
    EntidadPrestadora entidadPrestadoraNueva = new EntidadPrestadora();
    entidadPrestadoraNueva.setNombre("Test Entidad Prestadora");

    //act
    this.repositorio.guardar(entidadPrestadoraNueva);

    EntidadPrestadora entidadPrestadoraRecuperada = this.repositorio.buscarPorId(entidadPrestadoraNueva.getId());

    //assert
    Assertions.assertTrue(entidadPrestadoraNueva.getId() > 0, "EntidadPrestadora: genera id correctamente");
    Assertions.assertNotNull(entidadPrestadoraRecuperada, "EntidadPrestadora: se recupera instancia de la db");
    Assertions.assertEquals("Test Entidad Prestadora", entidadPrestadoraRecuperada.getNombre(), "EntidadPrestadora: nombre guardado correctamente");
  }

  @Test
  @DisplayName("Se puede guardar una entidad prestadora correctamente con sus entidades")
  public void ejecutaGuardarCorrectamente_ConEntidadPrestadoraConEntidad(){
    //arrange
    EntidadPrestadora entidadPrestadoraNueva = new EntidadPrestadora();
    entidadPrestadoraNueva.setNombre("Test Entidad Prestadora");

    Entidad entidadNueva = new Entidad();
    entidadNueva.setNombre("Test Entidad");

    entidadPrestadoraNueva.getEntidades().add(entidadNueva);

    //act
    this.repositorio.guardar(entidadPrestadoraNueva);

    EntidadPrestadora entidadPrestadoraRecuperada = this.repositorio.buscarPorId(entidadPrestadoraNueva.getId());
    Entidad entidadRecuperada = entidadPrestadoraRecuperada.getEntidades().stream().findFirst().get();

    //assert
    Assertions.assertTrue(entidadPrestadoraNueva.getId() > 0, "EntidadPrestadora: genera id correctamente");
    Assertions.assertNotNull(entidadPrestadoraRecuperada, "EntidadPrestadora: se recupera instancia de la db");
    Assertions.assertEquals("Test Entidad Prestadora", entidadPrestadoraRecuperada.getNombre(), "EntidadPrestadora: nombre guardado correctamente");

    Assertions.assertTrue(entidadNueva.getId() > 0, "Entidad: genera id correctamente");
    Assertions.assertNotNull(entidadRecuperada, "Entidad: se recupera instancia de la db");
    Assertions.assertEquals("Test Entidad", entidadRecuperada.getNombre(), "EntidadPrestadora: nombre guardado correctamente");

  }

  @Test
  @DisplayName("Se puede actualizar una entidad prestadora correctamente")
  public void ejecutaActualizarCorrectamente_ConEntidadPrestadoraSimple(){
    //arrange
    EntidadPrestadora entidadPrestadoraNueva = new EntidadPrestadora();
    entidadPrestadoraNueva.setNombre("Test Entidad Prestadora");

    //act
    this.repositorio.guardar(entidadPrestadoraNueva);
    EntidadPrestadora entidadPrestadoraAModificar = this.repositorio.buscarPorId(entidadPrestadoraNueva.getId());
    entidadPrestadoraAModificar.setNombre("Test Entidad Prestadora Actualizada");
    this.repositorio.actualizar(entidadPrestadoraAModificar);
    EntidadPrestadora entidadPrestadoraRecuperada = this.repositorio.buscarPorId(entidadPrestadoraAModificar.getId());

    //assert
    Assertions.assertEquals("Test Entidad Prestadora Actualizada", entidadPrestadoraRecuperada.getNombre(), "EntidadPrestadora: nombre guardado correctamente");
  }


  @Test
  @DisplayName("Se puede eliminar una entidad prestadora correctamente")
  public void ejecutaEliminarCorrectamente_ConEntidadPrestadoraSimple(){
    //arrange
    EntidadPrestadora entidadPrestadoraNueva = new EntidadPrestadora();
    entidadPrestadoraNueva.setNombre("Test Entidad Prestadora");

    //act
    this.repositorio.guardar(entidadPrestadoraNueva);
    this.repositorio.eliminar(entidadPrestadoraNueva);

    EntidadPrestadora entidadPrestadoraRecuperada = this.repositorio.buscarPorId(entidadPrestadoraNueva.getId());
    List<EntidadPrestadora> entidadesPrestadoras = this.repositorio.buscarTodos();

    //assert
    Assertions.assertNull(entidadPrestadoraRecuperada, "EntidadPrestadora: la entidad prestadora es eliminada");
  }
}
