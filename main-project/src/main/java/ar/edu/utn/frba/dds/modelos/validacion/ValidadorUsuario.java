package ar.edu.utn.frba.dds.modelos.validacion;

import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;

public interface ValidadorUsuario {
    public boolean validar(Usuario usuario, String password);
}
