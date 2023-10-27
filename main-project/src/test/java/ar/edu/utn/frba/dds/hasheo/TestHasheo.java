package ar.edu.utn.frba.dds.hasheo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.frba.dds.modelos.hasheo.EstrategiaHash;
import ar.edu.utn.frba.dds.modelos.hasheo.HashPBKDF2;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestHasheo {
  private EstrategiaHash hasheador = new HashPBKDF2();

  @BeforeEach
  public void init(){
  }

  @Test
  @DisplayName("Dos cadenas iguales resultan en el mismo hash")
  public void dosCadenasIgualesResultanEnMismoHash() throws IOException {

    String clave1 = "1234";
    String clave2 = "1234";
    assertEquals(hasheador.hashear(clave1), hasheador.hashear(clave2));

  }

}
