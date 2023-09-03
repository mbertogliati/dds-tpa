package ar.edu.utn.frba.dds.validacion;

import ar.edu.utn.frba.dds.domain.comunidades.Usuario;

public interface ValidadorUsuario {
    public boolean validar(Usuario usuario, String password);
}
