package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
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

  private static ValidadorUsuario validador;
  private static EstrategiaHash hasheador = new HashPBKDF2();

  static {
    ValidadorUsuarioConcreto validadorConcreto = new ValidadorUsuarioConcreto();
    validadorConcreto.agregarEstrategias(
        new EstrategiaValidacionRegExp("[A-Z]"),
        new EstrategiaValidacionRegExp("[a-z]"),
        new EstrategiaValidacionRegExp("[0-9]"),
        new EstrategiaValidacionRegExp("[#?!@$ %^&*-]"),
        new EstrategiaValidacionRegExp(".{8,}"),
        new EstrategiaValidacionNoEstaEnLista(ObtenerTopPeoresPasswords.instancia())
    );
    validador = validadorConcreto;
  }

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
    String param = context.queryParam("error");
    Map<String, Object> model = new HashMap<>();
    List<Provincia> provincias = repoProvincia.buscarTodas();
    model.put("provincias", provincias);
    if(param != null){
      model.put("error", "Revise sus datos");
      context.render("register.hbs", model);
    }else{
      context.render("register.hbs", model);
    }

  }
  public void save(@NotNull Context context) throws Exception {
    Usuario usuario = new Usuario();
    usuario.setUsername(context.formParam("username"));

    if(!Objects.equals(context.formParam("password"), context.formParam("repetir_password"))
        || !validador.validar(usuario,context.formParam("password"))){
      context.redirect("/register?error=register");
      return;
    }

    usuario.setPassword(hasheador.hashear(context.formParam("password")));
    usuario.setRolPlataforma(repoRol.rolDefault());

    Float latitud = Float.valueOf(context.formParam("latitud"));
    Float longitud = Float.valueOf(context.formParam("longitud"));
    Coordenada coordenada = new Coordenada(latitud, longitud);

    Localidad localidad = repoLocalidad.obtenerLocalidadPorId(context.formParam("localidad"));
    MetadatoGeografico geografico = new MetadatoGeografico(localidad);

    Ubicacion ubicacion = new Ubicacion();
    ubicacion.setCoordenada(coordenada);
    ubicacion.setMetadato(geografico);

    Persona nuevaPersona = new Persona(context.formParam("nombre"),context.formParam("apellido"));
    usuario.setPersonaAsociada(nuevaPersona);
    nuevaPersona.setEmail(context.formParam("email"));
    nuevaPersona.setWhatsapp(Integer.parseInt(context.formParam("celular")));

    nuevaPersona.setUltimaUbicacion(ubicacion);

    nuevaPersona.setMetodoNotificacion("MAIL");
    nuevaPersona.setEstrategiaMomentoNotificacion(new NotificacionAlMomento());

    repoUsuario.guardar(usuario);

    context.sessionAttribute("usuario", usuario);
    context.redirect("/");
  }
}
