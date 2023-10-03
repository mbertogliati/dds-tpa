package ar.edu.utn.frba.dds.controllers.utils.factories.ValidadorUsuarioFactory;

import ar.edu.utn.frba.dds.modelos.validacion.*;

public class ValidadorConcretoFactory implements ValidadorUsuarioFactory {
    @Override
    public ValidadorUsuario crearValidador() {
        ValidadorUsuarioConcreto validadorConcreto = new ValidadorUsuarioConcreto();
        validadorConcreto.agregarEstrategias(
                new EstrategiaValidacionRegExp("[A-Z]"),
                new EstrategiaValidacionRegExp("[a-z]"),
                new EstrategiaValidacionRegExp("[0-9]"),
                new EstrategiaValidacionRegExp("[#?!@$ %^&*-]"),
                new EstrategiaValidacionRegExp(".{8,}"),
                new EstrategiaValidacionNoEstaEnLista(ObtenerTopPeoresPasswords.instancia())
        );
        return validadorConcreto;
    }
}
