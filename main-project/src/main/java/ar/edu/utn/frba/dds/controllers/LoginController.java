package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.entidades.EntidadPrestadora;
import ar.edu.utn.frba.dds.modelos.entidades.OrganismoControl;
import ar.edu.utn.frba.dds.modelos.hasheo.EstrategiaHash;
import ar.edu.utn.frba.dds.modelos.hasheo.HashPBKDF2;
import ar.edu.utn.frba.dds.repositorios.comunidades.PersonaRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.UsuarioRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.EntidadPrestadoraRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.OrganismoControlRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;
import java.util.List;
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

  public void login(@NotNull Context context) throws Exception {
    String param = context.queryParam("error");

    if(param != null){
      Map<String, Object> model = GeneradorModel.model(context);
      model.put("error", "Hay error de logueo");
      context.render("login.hbs", model);
    }else{
      context.render("login.hbs");
    }

  }
  public void auth(@NotNull Context context){
    String username = context.formParam("username");
    String password = context.formParam("password");

    if(usuarioRepositorio.buscarPorUsername(username).isEmpty()){
      context.redirect("/login?error=logueo");
      return;
    }

    Usuario usuario = usuarioRepositorio.buscarPorUsername(username).get(0);


    if(Objects.equals(hasheador.hashear(password), usuario.getPassword())){
      context.sessionAttribute("usuario", usuario);

      if (VerificadorRol.tieneRol(usuario, VerificadorRol.Permiso.ADMINISTRAR_USUARIOS)){
        context.sessionAttribute("adminPlataforma", true);
      }

      List<EntidadPrestadora> entidadesManejadas = entidadPrestadoraRepositorio.manejadasPor(usuario.getPersonaAsociada());
      if(entidadesManejadas != null && !entidadesManejadas.isEmpty()){
        context.sessionAttribute("entidadesManejadas", entidadesManejadas);
      }

      List<OrganismoControl> organismosManejados = organismoControlRepositorio.manejadosPor(usuario.getPersonaAsociada());
      if(organismosManejados != null && !organismosManejados.isEmpty()){
        context.sessionAttribute("organismosManejados", organismosManejados);
      }

      context.redirect("/");
    }else{
      context.sessionAttribute("usuario", null);
      context.redirect("/login?error=logueo");
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
