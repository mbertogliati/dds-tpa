package ar.edu.utn.frba.dds.controllers.generales.user;

import ar.edu.utn.frba.dds.controllers.exceptions.FormInvalidoException;
import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.TipoRol;
import ar.edu.utn.frba.dds.controllers.utils.builders.builderPersona.PersonaBuilder;
import ar.edu.utn.frba.dds.controllers.utils.builders.builderPersona.PersonaBuilderHashmap;
import ar.edu.utn.frba.dds.controllers.utils.builders.builderUsuario.UsuarioBuilder;
import ar.edu.utn.frba.dds.controllers.utils.builders.builderUsuario.UsuarioBuilderHashmap;
import ar.edu.utn.frba.dds.controllers.utils.factories.ValidadorUsuarioFactory.ValidadorConcretoFactory;
import ar.edu.utn.frba.dds.controllers.utils.factories.ValidadorUsuarioFactory.ValidadorUsuarioFactory;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Rol;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.comunidades.notificacionesPersona.NotificacionAlMomento;
import ar.edu.utn.frba.dds.modelos.hasheo.EstrategiaHash;
import ar.edu.utn.frba.dds.modelos.hasheo.HashPBKDF2;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Localidad;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.MetadatoGeografico;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Provincia;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.geo_ref.ServicioGeoRef;
import ar.edu.utn.frba.dds.modelos.utilidades.Coordenada;
import ar.edu.utn.frba.dds.modelos.utilidades.Ubicacion;
import ar.edu.utn.frba.dds.modelos.validacion.EstrategiaValidacionNoEstaEnLista;
import ar.edu.utn.frba.dds.modelos.validacion.EstrategiaValidacionRegExp;
import ar.edu.utn.frba.dds.modelos.validacion.ObtenerTopPeoresPasswords;
import ar.edu.utn.frba.dds.modelos.validacion.ValidadorUsuario;
import ar.edu.utn.frba.dds.modelos.validacion.ValidadorUsuarioConcreto;
import ar.edu.utn.frba.dds.repositorios.comunidades.PersonaRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.RolRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.UsuarioRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.LocalidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.ProvinciaRepositorio;
import com.ctc.wstx.shaded.msv_core.reader.trex.ng.ListState;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class RegisterController{

  private static final ValidadorUsuarioFactory factoryValidador = new ValidadorConcretoFactory();
  private UsuarioBuilder usuarioBuilder;
  private PersonaBuilder personaBuilder;

  private PersonaRepositorio repoPersona;
  private UsuarioRepositorio repoUsuario;
  private RolRepositorio repoRol;
  private LocalidadRepositorio repoLocalidad;
  private ProvinciaRepositorio repoProvincia;

  public RegisterController(EntityManager entityManager){
    this.repoPersona = new PersonaRepositorio(entityManager);
    this.repoUsuario = new UsuarioRepositorio(entityManager);
    this.repoRol = new RolRepositorio(entityManager);
    this.repoLocalidad = new LocalidadRepositorio(entityManager);
    this.repoProvincia = new ProvinciaRepositorio(entityManager);
  }

  public void create(@NotNull Context context) throws Exception {
    Map<String, Object> model = GeneradorModel.model(context);
    List<Provincia> provincias = repoProvincia.buscarTodas();
    model.put("provincias", provincias);

    context.render("register.hbs", model);
  }
  public void save(@NotNull Context context) throws Exception {
    usuarioBuilder = new UsuarioBuilderHashmap(context.formParamMap(),repoRol);
    personaBuilder = new PersonaBuilderHashmap(context.formParamMap(),repoLocalidad);
    usuarioBuilder.configurarUsuario();
    if( !factoryValidador.crearValidador()
            .validar(usuarioBuilder.get(), context.formParam("password"))){
      throw new FormInvalidoException("La contraseña no cumple con los requisitos.");
    }

    Usuario usuario = usuarioBuilder.configurarPassword().get();

    Persona nuevaPersona = personaBuilder
            .configurarNombres()
            .configurarInformacionDeContacto()
            .configurarInformacionDeUbicacion()
            .get();

    usuario.setPersonaAsociada(nuevaPersona);
    repoUsuario.guardar(usuario);

    context.sessionAttribute("usuario", usuario);
    context.redirect("/");
  }
}
