package ar.edu.utn.frba.dds.repositorios.comunidad;

import ar.edu.utn.frba.dds.modelos.comunidades.Membresia;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.repositorios.comunidades.PersonaRepositorio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PersonaRepositorioTests implements WithSimplePersistenceUnit {
  private PersonaRepositorio repositorioPersona;

  @BeforeEach
  public void init() throws IOException {
    this.repositorioPersona = new PersonaRepositorio(entityManager());
  }

  @Test
  @DisplayName("Se puede guardar una persona correctamente")
  public void ejecutaGuardarCorrectamente_ConPersonaSimple(){
    //arrange
    Persona personaNueva = new Persona();
    personaNueva.setNombre("Test Persona");
    personaNueva.setEmail("email@email.com");
    personaNueva.setWhatsapp(12340);
    personaNueva.setApellido("Test");

    //act
    this.repositorioPersona.guardar(personaNueva);

    Persona personaRecuperada = this.repositorioPersona.buscarPorId(personaNueva.getId());

    //assert
    Assertions.assertTrue(personaNueva.getId() > 0, "Persona: genera id correctamente");
    Assertions.assertNotNull(personaRecuperada, "Persona: se recupera instancia de la db");
    Assertions.assertEquals("Test Persona", personaRecuperada.getNombre(), "Persona: nombre guardado correctamente");
  }

  @Test
  @DisplayName("Se puede guardar una persona correctamente con sus membresias")
  public void ejecutaGuardarCorrectamente_ConPersonaConMembresia(){
    //arrange
    Persona personaNueva = new Persona();
    personaNueva.setNombre("Test Persona");
    personaNueva.setEmail("email@email.com");
    personaNueva.setWhatsapp(12340);
    personaNueva.setApellido("Test");

    Membresia membresiaNueva = new Membresia();
    membresiaNueva.setPersona(personaNueva);
    personaNueva.getMembresias().add(membresiaNueva);

    //act
    this.repositorioPersona.guardar(personaNueva);

    Persona personaRecuperada = this.repositorioPersona.buscarPorId(personaNueva.getId());
    Membresia membresiaRecuperada = personaRecuperada.getMembresias().stream().findFirst().get();

    //assert
    Assertions.assertTrue(personaNueva.getId() > 0, "Persona: genera id correctamente");
    Assertions.assertNotNull(personaRecuperada, "Persona: se recupera instancia de la db");
    Assertions.assertEquals("Test Persona", personaRecuperada.getNombre(), "Persona: nombre guardado correctamente");

    Assertions.assertTrue(membresiaNueva.getId() > 0, "Membresia: genera id correctamente");
    Assertions.assertNotNull(membresiaRecuperada, "Membresia: se recupera instancia de la db");
  }

  @Test
  @DisplayName("Se puede actualizar una persona correctamente")
  public void ejecutaActualizarCorrectamente_ConPersonaSimple(){
    //arrange
    Persona personaNueva = new Persona();
    personaNueva.setNombre("Test Persona");

    //act
    this.repositorioPersona.guardar(personaNueva);
    Persona entidadAModificar = this.repositorioPersona.buscarPorId(personaNueva.getId());
    entidadAModificar.setNombre("Test Persona Actualizada");
    this.repositorioPersona.actualizar(entidadAModificar);
    Persona personaRecuperada = this.repositorioPersona.buscarPorId(entidadAModificar.getId());

    //assert
    Assertions.assertEquals("Test Persona Actualizada", personaRecuperada.getNombre(), "Persona: nombre guardado correctamente");
  }


  @Test
  @DisplayName("Se puede eliminar una persona correctamente")
  public void ejecutaEliminarCorrectamente_ConPersonaSimple(){
    //arrange
    Persona personaNueva = new Persona();
    personaNueva.setNombre("Test Persona");

    //act
    this.repositorioPersona.guardar(personaNueva);
    this.repositorioPersona.eliminar(personaNueva);

    Persona personaRecuperada = this.repositorioPersona.buscarPorId(personaNueva.getId());

    //assert
    Assertions.assertNull(personaRecuperada, "Persona: la entidad es eliminada");
  }
}
