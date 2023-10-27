package ar.edu.utn.frba.dds.controllers.utils.builders.builderUsuario;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;

public interface UsuarioBuilder {
    public Usuario get();
    public UsuarioBuilder init();
    public UsuarioBuilder init(Usuario usuario);
    public UsuarioBuilder configurarUsuario();
    public UsuarioBuilder configurarPassword();
    public void reset();
}
