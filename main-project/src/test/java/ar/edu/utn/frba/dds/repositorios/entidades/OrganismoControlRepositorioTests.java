package ar.edu.utn.frba.dds.repositorios.entidades;

import ar.edu.utn.frba.dds.domain.entidades.EntidadPrestadora;
import ar.edu.utn.frba.dds.domain.entidades.OrganismoControl;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrganismoControlRepositorioTests implements WithSimplePersistenceUnit {

  private OrganismoControlRepositorio repositorio;

  @BeforeEach
  public void init() throws IOException {
    this.repositorio = new OrganismoControlRepositorio(entityManager());
  }

  @Test
  @DisplayName("Se puede guardar un organismo de control correctamente")
  public void ejecutaGuardarCorrectamente_ConOrganismoControlSimple(){
    //arrange
    OrganismoControl organismoControlNuevo = new OrganismoControl();
    organismoControlNuevo.setNombre("Test Organismo Control");

    //act
    this.repositorio.guardar(organismoControlNuevo);

    OrganismoControl organismoControlRecuperado = this.repositorio.buscarPorId(organismoControlNuevo.getId());

    //assert
    Assertions.assertTrue(organismoControlNuevo.getId() > 0, "OrganismoControl: genera id correctamente");
    Assertions.assertNotNull(organismoControlRecuperado, "OrganismoControl: se recupera instancia de la db");
    Assertions.assertEquals("Test Organismo Control", organismoControlRecuperado.getNombre(), "OrganismoControl: nombre guardado correctamente");
  }

  @Test
  @DisplayName("Se puede guardar un organismo de control correctamente con sus entidades prestadoras")
  public void ejecutaGuardarCorrectamente_ConOrganismoControlConEntidadPrestadora(){
    //arrange
    OrganismoControl organismoControlNuevo = new OrganismoControl();
    organismoControlNuevo.setNombre("Test Organismo Control");

    EntidadPrestadora entidadPrestadoraNueva = new EntidadPrestadora();
    entidadPrestadoraNueva.setNombre("Test Entidad Prestadora");

    organismoControlNuevo.getEntidadesPrestadoras().add(entidadPrestadoraNueva);

    //act
    this.repositorio.guardar(organismoControlNuevo);

    OrganismoControl organismoControlRecuperado = this.repositorio.buscarPorId(organismoControlNuevo.getId());
    EntidadPrestadora entidadPrestadoraRecuperada = organismoControlRecuperado.getEntidadesPrestadoras().stream().findFirst().get();

    //assert
    Assertions.assertTrue(organismoControlNuevo.getId() > 0, "OrganismoControl: genera id correctamente");
    Assertions.assertNotNull(organismoControlRecuperado, "OrganismoControl: se recupera instancia de la db");
    Assertions.assertEquals("Test Organismo Control", organismoControlRecuperado.getNombre(), "OrganismoControl: nombre guardado correctamente");

    Assertions.assertTrue(entidadPrestadoraNueva.getId() > 0, "EntidadPrestadora: genera id correctamente");
    Assertions.assertNotNull(entidadPrestadoraRecuperada, "EntidadPrestadora: se recupera instancia de la db");
    Assertions.assertEquals("Test Entidad Prestadora", entidadPrestadoraRecuperada.getNombre(), "EntidadPrestadora: nombre guardado correctamente");

  }

  @Test
  @DisplayName("Se puede actualizar un organismo de control correctamente")
  public void ejecutaActualizarCorrectamente_ConOrganismoControlSimple(){
    //arrange
    OrganismoControl organismoControlNuevo = new OrganismoControl();
    organismoControlNuevo.setNombre("Test Organismo Control");

    //act
    this.repositorio.guardar(organismoControlNuevo);
    OrganismoControl organismoControlAModificar = this.repositorio.buscarPorId(organismoControlNuevo.getId());
    organismoControlAModificar.setNombre("Test Organismo Control Actualizado");
    this.repositorio.actualizar(organismoControlAModificar);
    OrganismoControl organismoControlRecuperado = this.repositorio.buscarPorId(organismoControlAModificar.getId());

    //assert
    Assertions.assertEquals("Test Organismo Control Actualizado", organismoControlRecuperado.getNombre(), "OrganismoControl: nombre guardado correctamente");
  }


  @Test
  @DisplayName("Se puede eliminar un organismo de control correctamente")
  public void ejecutaEliminarCorrectamente_ConOrganismoControlSimple(){
    //arrange
    OrganismoControl organismoControlNuevo = new OrganismoControl();
    organismoControlNuevo.setNombre("Test Organismo Control");

    //act
    this.repositorio.guardar(organismoControlNuevo);
    this.repositorio.eliminar(organismoControlNuevo);

    OrganismoControl organismoControlRecuperado = this.repositorio.buscarPorId(organismoControlNuevo.getId());

    //assert
    Assertions.assertNull(organismoControlRecuperado, "OrganismoControl: el organismo de control es eliminado");
  }
}
