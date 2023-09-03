package ar.edu.utn.frba.dds.validacion;

import ar.edu.utn.frba.dds.domain.comunidades.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ValidacionTests {
    private ValidadorUsuarioConcreto validador = new ValidadorUsuarioConcreto();
    private Usuario usuario;

    @Test
    @DisplayName("Una cadena que está en las peores 10.000, no es válida")
    public void noValidaEnPeores10000() {
        this.usuario = new Usuario("username", "123456");
        this.validador.agregarEstrategia(new EstrategiaValidacionNoEstaEnLista(ObtenerTopPeoresPasswords.instancia()));

        Assertions.assertFalse(this.validador.validar(this.usuario, usuario.getPassword()));
    }

    @Test
    @DisplayName("Una cadena que no tiene mayúscula, no es válida")
    public void noValidaSinMayus() {
        this.usuario = new Usuario("username", "clave");
        this.validador.agregarEstrategia(new EstrategiaValidacionRegExp("/^(?=.*?[A-Z])$/")); //Tiene mayuscula

        Assertions.assertFalse(this.validador.validar(this.usuario, usuario.getPassword()));
    }

    @Test
    @DisplayName("Una cadena que no tiene minúscula, no es válida")
    public void noValidaSinMinus() {
        this.usuario = new Usuario("username", "CLAVE");
        this.validador.agregarEstrategia(new EstrategiaValidacionRegExp("/^(?=.*?[a-z])$/")); //Tiene minúscula

        Assertions.assertFalse(this.validador.validar(this.usuario, usuario.getPassword()));
    }

    @Test
    @DisplayName("Una cadena que no tiene número, no es válida")
    public void noValidaSinNumero() {
        this.usuario = new Usuario("username", "CLAVE");
        this.validador.agregarEstrategia(new EstrategiaValidacionRegExp("/^(?=.*?[0-9])$/")); //Tiene número

        Assertions.assertFalse(this.validador.validar(this.usuario, usuario.getPassword()));
    }

    @Test
    @DisplayName("Una cadena que no tiene carácter especial, no es válida")
    public void noValidaSinEspecial() {
        this.usuario = new Usuario("username", "CLAVE");
        this.validador.agregarEstrategia(new EstrategiaValidacionRegExp("/^(?=.*?[#?!@$ %^&*-])$/")); //Tiene caracter especial

        Assertions.assertFalse(this.validador.validar(this.usuario, usuario.getPassword()));
    }

    @Test
    @DisplayName("Una cadena que no tiene longitud mayor o igual a 8, no es válida")
    public void noValidaSinLongitud() {
        this.usuario = new Usuario("username", "CLAVE");
        this.validador.agregarEstrategia(new EstrategiaValidacionRegExp("/^.{8,}$/")); //Tiene logitud 8 o mas

        Assertions.assertFalse(this.validador.validar(this.usuario, usuario.getPassword()));
    }

    @Test
    @DisplayName("Una password que es igual al usuario, no es válida")
    public void noValidaIgualAlUsuario() {
        this.usuario = new Usuario("username", "username");

        Assertions.assertFalse(this.validador.validar(this.usuario, usuario.getPassword()));
    }

    @Test
    @DisplayName("Una cadena que cumple todo lo anterior, es válida")
    public void esValida() {
        this.usuario = new Usuario("username", "ClaVeQueSiEsValida*8");
        this.validador.agregarEstrategia(new EstrategiaValidacionNoEstaEnLista(ObtenerTopPeoresPasswords.instancia()));
        this.validador.agregarEstrategia(new EstrategiaValidacionRegExp("[A-Z]")); //Tiene mayuscula
        this.validador.agregarEstrategia(new EstrategiaValidacionRegExp("[a-z]")); //Tiene minuscula
        this.validador.agregarEstrategia(new EstrategiaValidacionRegExp("[0-9]")); //Tiene numero
        this.validador.agregarEstrategia(new EstrategiaValidacionRegExp("[#?!@$ %^&*]")); //Tiene caracter especial
        this.validador.agregarEstrategia(new EstrategiaValidacionRegExp(".{8,}")); //Tiene logitud 8 o mas

        Assertions.assertTrue(this.validador.validar(this.usuario, usuario.getPassword()));
    }
}
