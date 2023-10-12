package ar.edu.utn.frba.dds.controllers.generales.user;

import ar.edu.utn.frba.dds.controllers.exceptions.FormInvalidoException;
import ar.edu.utn.frba.dds.controllers.utils.VerificadorRol;
import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.IntentoDeLogin;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.hasheo.EstrategiaHash;
import ar.edu.utn.frba.dds.modelos.hasheo.HashPBKDF2;
import ar.edu.utn.frba.dds.repositorios.comunidades.PersonaRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.UsuarioRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.EntidadPrestadoraRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.OrganismoControlRepositorio;
import io.javalin.http.Context;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class LoginController{
  private static EstrategiaHash hasheador = new HashPBKDF2();
  PersonaRepositorio personaRepositorio;
  UsuarioRepositorio usuarioRepositorio;
  EntidadPrestadoraRepositorio entidadPrestadoraRepositorio;
  OrganismoControlRepositorio organismoControlRepositorio;

  public LoginController(EntityManager entityManager) {
    this.personaRepositorio = new PersonaRepositorio(entityManager);
    this.usuarioRepositorio = new UsuarioRepositorio(entityManager);
    this.entidadPrestadoraRepositorio = new EntidadPrestadoraRepositorio(entityManager);
    this.organismoControlRepositorio = new OrganismoControlRepositorio(entityManager);
  }

  public void login(@NotNull Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    context.render("login.hbs", model);
  }

  public void auth(@NotNull Context context){
    IntentoDeLogin intentoDeLogin = context.sessionAttribute("intentoDeLogin");
    if(intentoDeLogin == null){
      intentoDeLogin = new IntentoDeLogin();
      context.sessionAttribute("intentoDeLogin", intentoDeLogin);
    }

    if(!intentoDeLogin.puedeLoguearse()){
      throw new FormInvalidoException("Demasiados intentos fallidos de inicio de sesión. Debe esperar " + intentoDeLogin.getSegundosRestantes() + " segundos.");
    }

    String username = context.formParam("username");
    String password = context.formParam("password");

    if(usuarioRepositorio.buscarPorUsername(username).isEmpty()){
      intentoDeLogin.sumarIntento();
      throw new FormInvalidoException("Error. No existe el usuario especificado.");
    }

    Usuario usuario = usuarioRepositorio.buscarPorUsername(username).get(0);

    if(Objects.equals(hasheador.hashear(password), usuario.getPassword())){
      context.sessionAttribute("usuario", usuario);
      intentoDeLogin.reset();
      if (VerificadorRol.tieneRol(usuario, VerificadorRol.Permiso.ADMINISTRAR_USUARIOS)){
        context.sessionAttribute("adminPlataforma", true);
      }

      context.redirect("/");
    }else{
      context.sessionAttribute("usuario", null);
      intentoDeLogin.sumarIntento();
      throw new FormInvalidoException("Error. La contraseña es incorrecta.");
    }
  }
  public void delete(@NotNull Context context){
    context.consumeSessionAttribute("usuario");
    context.consumeSessionAttribute("adminPlataforma");
    context.consumeSessionAttribute("entidadesManejadas");
    context.consumeSessionAttribute("organismosManejados");
    context.redirect("/");
  }
}
