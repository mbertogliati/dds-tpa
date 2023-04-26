package ar.edu.utn.frba.dds.users;

import ar.edu.utn.frba.dds.domain.services.Estacion;
import ar.edu.utn.frba.dds.domain.services.SubtipoServicio;
import ar.edu.utn.frba.dds.domain.users.Comunidad;
import ar.edu.utn.frba.dds.domain.users.Miembro;
import ar.edu.utn.frba.dds.domain.users.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestUsuario {
  private Usuario userMatias;
  private Comunidad comunidadX;

  @BeforeEach
  public void Init() {
    userMatias = new Usuario("userMatias", "Matias", "passwordMatias1-");
    Usuario userJoaquin = new Usuario("userJoaquin", "Joaquin", "passwordJoaquin1-");

    List<Miembro> miembrosVacia = new ArrayList<Miembro>();

    comunidadX = new Comunidad(1,"ComunidadX", miembrosVacia);

    comunidadX.agregarMiembro(userJoaquin);
  }

  @Test
  @DisplayName("Con una password correcta, se puede iniciar sesión")
  public void inicioSesionCorrectamente() {
    //TODO: NO PASA EL TEST
    //arrange

    //act
    boolean resultado = userMatias.iniciarSesion("passwordMatias1-");

    //assert
    Assertions.assertEquals(true, resultado);
    Assertions.assertEquals(true, userMatias.isSesionIniciada());
  }

  @Test
  @DisplayName("Con una password incorrecta, no se puede iniciar sesión")
  public void noInicioSesionConClaveIncorrecta() {
    //TODO: NO PASA EL TEST
    //arrange

    //act
    boolean resultado = userMatias.iniciarSesion("passwordMatiasIncorrecta");

    //assert
    Assertions.assertEquals(false, resultado);
    Assertions.assertEquals(false, userMatias.isSesionIniciada());
  }

  @Test
  @DisplayName("Se puede cambiar la contraseña")
  public void sePuedeCambiarPasswordCorrectamente() {
    //arrange

    //act
    //TODO: NO SE ENTIENDE PARA QUE SE USA EL STRING USUARIO COMO TERCER PARAMETRO
    userMatias.cambiarPassword("passwordMatias1-","passwordMatias2-", "Matias");

    //assert
    Assertions.assertEquals(true, false);
  }

  @Test
  @DisplayName("Se puede cerrar la sesion con el mensaje cerrarSesion")
  public void cerrarSesionFuncionaCorrectamente() {
    //arrange
    userMatias.iniciarSesion("passwordMatias1-");

    //act
    userMatias.cerrarSesion();

    //assert
    Assertions.assertEquals(false, userMatias.isSesionIniciada());
  }

  @Test
  @DisplayName("Se puede suscribir un usuario a una comunidad")
  public void sePuedeSuscribirUsuarioAComunidad() {
    //arrange
    userMatias.iniciarSesion("passwordMatias1-");

    //act
    userMatias.suscribirAComunidad(comunidadX);

    //assert
    Assertions.assertEquals(true, comunidadX.esMiembro(userMatias));
  }

  @Test
  @DisplayName("Se puede desuscribir un usuario de una comunidad")
  public void sePuedeDesuscribirUsuarioDeComunidad() {
    //arrange

    //act
    userMatias.desuscribirAComunidad(comunidadX);

    //assert
    Assertions.assertEquals(false, comunidadX.esMiembro(userMatias));
  }

}
