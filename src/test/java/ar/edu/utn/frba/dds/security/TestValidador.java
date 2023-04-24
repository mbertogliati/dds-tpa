package ar.edu.utn.frba.dds.security;
import ar.edu.utn.frba.dds.domain.security.Validador;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestValidador {

  @BeforeEach
  public void Init() {

  }




  @Test
  @DisplayName("Las primeras 10 peores passwords coinciden con la lista dada")
  public void primeras10PeoresPasswordCoinciden() {
    ArrayList<String> listaATestear = new ArrayList<>(Arrays.asList("123456", "password", "12345678", "qwerty", "123456789", "12345", "1234", "111111", "1234567", "dragon"));

    for (int i = 0; i < listaATestear.size(); i++) {
      Assertions.assertEquals(listaATestear.get(i), Validador.getPeoresPasswords().get(i));
    }

  }
  @Test
  @DisplayName("Una misma contraseña con la misma salt resulta en el mismo hash")
  public void MismaPasswordMismaSalResultaEnMismoHasheo() {

    String pass = "hola123";
    byte[] salt = Validador.genSalt();
    System.out.println(Validador.getHash(pass,salt,1));
    Assertions.assertEquals(Validador.getHash(pass,salt,1), Validador.getHash(pass,salt,1));


  }

}
