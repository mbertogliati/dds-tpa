package ar.edu.utn.frba.dds.users;

import ar.edu.utn.frba.dds.domain.users.Comunidad;
import ar.edu.utn.frba.dds.domain.users.Miembro;
import ar.edu.utn.frba.dds.domain.users.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class TestUsuario {
  @Mock
  Usuario userMatias;
  private Comunidad comunidadX;



  @BeforeEach
  public void Init() {
    userMatias = new Usuario("userMatias", "passwordMatias1-");
    Usuario userJoaquin = new Usuario("userJoaquin", "passwordJoaquin1-");

    List<Miembro> miembrosVacia = new ArrayList<>();

    comunidadX = new Comunidad(1,"ComunidadX", miembrosVacia);

    comunidadX.agregarMiembro(userJoaquin);
  }

  @Test
  @DisplayName("Con una password correcta, se puede iniciar sesión")
  public void inicioSesionCorrectamente() {
    //arrange

    //act
    boolean resultado = userMatias.iniciarSesion("passwordMatias1-");

    //assert
    Assertions.assertTrue(resultado);
    Assertions.assertTrue(userMatias.isSesionIniciada());
  }

  @Test
  @DisplayName("Con una password incorrecta, no se puede iniciar sesión")
  public void noInicioSesionConClaveIncorrecta() {
    //arrange

    //act
    boolean resultado = userMatias.iniciarSesion("passwordMatiasIncorrecta");

    //assert
    Assertions.assertFalse(resultado);
    Assertions.assertFalse( userMatias.isSesionIniciada());
  }
  @Test
  @DisplayName("No se puede iniciar sesión inmediatamente despues de haber puesto mal la contraseña")
  public void noSePuedeIniciarSesionInmediatamenteSiHuboIntentoFallido() {
    //arrangefactorSegundos;


    //assert
    Assertions.assertFalse(userMatias.iniciarSesion("contraseñaIncorrecta"));
    Assertions.assertFalse(userMatias.iniciarSesion("passwordMatias1-"));
  }

  @Test
  @DisplayName("Se puede iniciar sesión nuevamente luego de esperar el tiempo requerido")
  public void sePuedeIniciarSesionLuegoDeEsperarDelay() throws InterruptedException {

    //assert
    Assertions.assertFalse(userMatias.iniciarSesion("passwordIncorrecta"));
    Thread.sleep(userMatias.getDelaySeg()* 1000L);
    Assertions.assertTrue(userMatias.iniciarSesion("passwordMatias1-"));
  }
  @Test
  @DisplayName("Al cambiar la contraseña, solo se puede iniciar sesión con la nueva contraseña")
  public void sePuedeCambiarPasswordCorrectamente() throws InterruptedException {
    //arrange

    //act
    String viejaPass = "passwordMatias1-";
    String nuevaPass = "passwordMatias2-";
    userMatias.cambiarPassword(viejaPass,nuevaPass);

    //assert
    Assertions.assertFalse(userMatias.iniciarSesion(viejaPass));
    Thread.sleep(userMatias.getDelaySeg()* 1000L);
    Assertions.assertTrue(userMatias.iniciarSesion(nuevaPass));
  }



  @Test
  @DisplayName("Se puede cerrar la sesion con el mensaje cerrarSesion")
  public void cerrarSesionFuncionaCorrectamente() {
    //arrange
    userMatias.iniciarSesion("passwordMatias1-");

    //act
    userMatias.cerrarSesion();

    //assert
    Assertions.assertFalse(userMatias.isSesionIniciada());
  }

  @Test
  @DisplayName("Se puede suscribir un usuario a una comunidad")
  public void sePuedeSuscribirUsuarioAComunidad() {
    //arrange
    userMatias.iniciarSesion("passwordMatias1-");

    //act
    userMatias.suscribirAComunidad(comunidadX);

    //assert
    Assertions.assertTrue(comunidadX.esMiembro(userMatias));
  }

  @Test
  @DisplayName("Se puede desuscribir un usuario de una comunidad")
  public void sePuedeDesuscribirUsuarioDeComunidad() {
    //arrange

    //act
    userMatias.desuscribirAComunidad(comunidadX);

    //assert
    Assertions.assertFalse(comunidadX.esMiembro(userMatias));
  }

}
