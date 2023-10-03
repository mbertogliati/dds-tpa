package ar.edu.utn.frba.dds.controllers.utils.builders.builderUsuario;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.hasheo.EstrategiaHash;
import ar.edu.utn.frba.dds.modelos.hasheo.HashPBKDF2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioBuilderHashmap implements UsuarioBuilder{
    private Map<String, List<String>> hashMapUsuario;
    private static final EstrategiaHash estrategiaHash = new HashPBKDF2();
    private Usuario usuarioEnCreacion = new Usuario();

    public UsuarioBuilderHashmap(Map<String, List<String>> hashMapUsuario) {
        this.hashMapUsuario = hashMapUsuario;
    }


    @Override
    public Usuario get() {
        return usuarioEnCreacion;
    }

    @Override
    public UsuarioBuilder init() {
        this.reset();
        return this;
    }

    @Override
    public UsuarioBuilder init(Usuario usuario) {
        this.usuarioEnCreacion = usuario;
        return this;
    }

    @Override
    public UsuarioBuilder configurarUsuario() {
        usuarioEnCreacion.setUsername(hashMapUsuario.get("username").get(0));
        return this;
    }

    @Override
    public UsuarioBuilder configurarPassword() {
        usuarioEnCreacion.setPassword(estrategiaHash.hashear(hashMapUsuario.get("password").get(0)));
        return this;
    }

    @Override
    public void reset() {
        this.usuarioEnCreacion = new Usuario();
    }
}
