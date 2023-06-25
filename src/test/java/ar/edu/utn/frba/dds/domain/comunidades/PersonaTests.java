package ar.edu.utn.frba.dds.domain.comunidades;

import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PersonaTests {
  @Test
  @DisplayName("Se puede asignar una ubicacion actual a una persona")
  public void setUbicacionActualAPersona(){
    //arrange
    Persona persona = new Persona("Nombre", "Apellido", new Usuario("username", "password"));
    Ubicacion ubicacion = new Ubicacion(1.5f, 1.5f);

    //act
    persona.setUbicacionActual(ubicacion);

    //assert
    Assertions.assertEquals(1.5f, persona.getUbicacionActual().getLatitud());
    Assertions.assertEquals(1.5f, persona.getUbicacionActual().getLongitud());
  }
}
