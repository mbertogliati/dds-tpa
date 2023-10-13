package ar.edu.utn.frba.dds.server;

import static io.javalin.apibuilder.ApiBuilder.before;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerDatosController;
import ar.edu.utn.frba.dds.controllers.generales.comunidades.FusionComunidadesController;
import ar.edu.utn.frba.dds.controllers.generales.entidades.CargaMasivaController;
import ar.edu.utn.frba.dds.controllers.generales.comunidades.ComunidadesController;
import ar.edu.utn.frba.dds.controllers.generales.entidades.EntidadesController;
import ar.edu.utn.frba.dds.controllers.generales.entidades.EntidadesPrestadorasController;
import ar.edu.utn.frba.dds.controllers.generales.entidades.EstablecimientosController;
import ar.edu.utn.frba.dds.controllers.generales.incidentes.IncidentesController;
import ar.edu.utn.frba.dds.controllers.generales.user.IndexController;
import ar.edu.utn.frba.dds.controllers.generales.user.LoginController;
import ar.edu.utn.frba.dds.controllers.generales.entidades.OrganismosDeControlController;
import ar.edu.utn.frba.dds.controllers.generales.incidentes.RankingsController;
import ar.edu.utn.frba.dds.controllers.generales.user.RegisterController;
import ar.edu.utn.frba.dds.controllers.generales.servicios.ServiciosController;
import ar.edu.utn.frba.dds.controllers.generales.comunidades.UsuariosController;
import ar.edu.utn.frba.dds.controllers.middleware.AuthMiddleware;
import javax.persistence.EntityManager;

public class Router {
  public static void init(EntityManager entityManager){
    Server.app().routes(() -> {
      //BEFORE ALL
      before(new AuthMiddleware());

      //INDEX
      get("/", (ctx) -> {ctx.redirect("/home");});
      get("/home", new IndexController());

      //INCIDENTES
      get("/incidentes", new IncidentesController(entityManager)::getAll);
      path("/aperturaIncidente", () ->{
        get(new IncidentesController(entityManager)::vistaApertura);
        post(new IncidentesController(entityManager)::abrir);
      });
      path("/cierreIncidente", () -> {
        get(new IncidentesController(entityManager)::vistaCierre);
        post(new IncidentesController(entityManager)::cerrar);
      });

      //RANKINGS
      path("/rankings", () -> {
        get(new RankingsController(entityManager)::index);
        path("{id}",() ->{
          get(new RankingsController(entityManager)::show);
        });
      });

      //CARGA MASIVA ENTIDADES
      path("/cargaMasiva", () ->{
        get(new CargaMasivaController(entityManager)::create);
        post(new CargaMasivaController(entityManager)::save);
      });

      // ENTIDADES
      path("/entidades", () -> {
        path("crear", () -> {
          get("{idEntidadPrestadora}", new EntidadesController(entityManager)::create);
          post(new EntidadesController(entityManager)::save);
        });
        path("{id}",() ->{
          get(new EntidadesController(entityManager)::edit);
          post(new EntidadesController(entityManager)::update);
          post("delete", new EntidadesController(entityManager)::delete);
          get("sacarEstablecimiento/{idEstablecimiento}", new EntidadesController(entityManager)::sacarEstablecimiento);
        });
      });

      //ENTIDADES PRESTADORAS
      path("/entidadesPrestadoras",() -> {
        get(new EntidadesPrestadorasController(entityManager)::index);
        post(new EntidadesPrestadorasController(entityManager)::save);
        get("crear",new EntidadesPrestadorasController(entityManager)::create);
        get("crear/{id}",new EntidadesPrestadorasController(entityManager)::crearConOrganismoControl);
        path("{id}", () ->{
          get(new EntidadesPrestadorasController(entityManager)::edit);
          post(new EntidadesPrestadorasController(entityManager)::update);
          post("delete", new EntidadesPrestadorasController(entityManager)::delete);
          get("sacarEntidad/{idEntidad}", new EntidadesPrestadorasController(entityManager)::sacarEntidad);
        });
      });

      //ORGANISMOS DE CONTROL
      path("/organismosControl", () -> {
        post(new OrganismosDeControlController(entityManager)::save);
        get("crear", new OrganismosDeControlController(entityManager)::create);
        path("{id}", () -> {
          get("sacarEntidadPrestadora/{entidadPrestadora}", new OrganismosDeControlController((entityManager))::sacarEntidadPrestadora);
          get(new OrganismosDeControlController((entityManager))::edit);
          post("delete", new OrganismosDeControlController(entityManager)::delete);
        });
        post("/{id}", new OrganismosDeControlController((entityManager))::update);
      });

      //ESTABLECIMIENTOS
      path("/establecimientos/", () -> {
        path("crear", () ->{
          get("{idEntidad}", new EstablecimientosController(entityManager)::create);
          post(new EstablecimientosController(entityManager)::save);
        });
        path("{id}", () -> {
          get(new EstablecimientosController(entityManager)::show);
          post(new EstablecimientosController(entityManager)::update);
          post("delete", new EstablecimientosController(entityManager)::delete);
          get("sacarServicio/{idServicioPrestado}", new EstablecimientosController(entityManager)::sacarServicio);
          post("agregarServicio", new EstablecimientosController(entityManager)::agregarServicio);
        });
      });

      //SERVICIOS
      path("/servicios", () ->{
        get(new ServiciosController(entityManager)::create);
        get("{idEstablecimiento}", new ServiciosController(entityManager)::createFromEstablecimiento);
        post(new ServiciosController(entityManager)::save);
      });

      //LOGIN
      path("/login", () -> {
        get(new LoginController(entityManager)::login);
        post(new LoginController(entityManager)::auth);
      });
      get("/logout", new LoginController(entityManager)::delete);

      //REGISTER
      path("/register", () -> {
        //before(new ValidadorUsuarioMiddleware());
        get(new RegisterController(entityManager)::create);
        post(new RegisterController(entityManager)::save);
      });

      //ADMINISTRACIÓN DE USUARIOS (ADMIN PLATAFORMA)
      path("/usuarios",()->{
        get(new UsuariosController(entityManager)::index);
        path("{id}", () -> {
          path("edit", ()->{
            get(new UsuariosController(entityManager)::edit);
            post(new UsuariosController(entityManager)::update);
          });
          get(new UsuariosController(entityManager)::show);
          get("interes", new UsuariosController(entityManager)::getInteres);
          post("agregarServicio", new UsuariosController(entityManager)::agregarServicio);
          post("agregarEntidad", new UsuariosController(entityManager)::agregarEntidad);
          get("sacarServicio/{idServicio}", new UsuariosController(entityManager)::sacarServicio);
          get("sacarEntidad/{idEntidad}", new UsuariosController(entityManager)::sacarEntidad);
        });
      });

      //COMUNIDADES
      get("/misComunidades", new ComunidadesController(entityManager)::deUsuario);
      path("/comunidades", () ->{
        get(new ComunidadesController(entityManager)::index);
        path("crear", () -> {
          get(new ComunidadesController(entityManager)::create);
          post(new ComunidadesController(entityManager)::save);
        });
        path("{id}", () ->{
          get(new ComunidadesController(entityManager)::show);
          get("edit", new ComunidadesController(entityManager)::edit);
          post("edit", new ComunidadesController(entityManager)::update);
          post("delete", new ComunidadesController(entityManager)::delete);
          post("agregarServicio", new ComunidadesController(entityManager)::agregarServicio);
          get("sacarServicio/{idServicio}", new ComunidadesController(entityManager)::quitarServicio);
          get("sacarMiembro/{idUsuario}", new ComunidadesController(entityManager)::sacarMiembro);
          get("unirMiembro/{idUsuario}", new ComunidadesController(entityManager)::unirMiembro);
          get("cambiarRol/{idUsuario}/{idServicio}/{nuevoRol}", new ComunidadesController(entityManager)::cambiarRol);
        });
      });

      get("fusionarComunidades",new FusionComunidadesController(entityManager)::create);

      //CONTROLADORES PARA FORMULARIOS DINAMICOS
      path("/obtener",() -> {
        get("departamentos", new ObtenerDatosController(entityManager)::Departamentos);
        get("localidades", new ObtenerDatosController(entityManager)::Localidades);
        get("entidades", new ObtenerDatosController(entityManager)::Entidades);
        get("establecimientos", new ObtenerDatosController(entityManager)::Establecimientos);
        get("servicios", new ObtenerDatosController(entityManager)::Servicios);
        get("incidentes", new ObtenerDatosController(entityManager)::Incidentes);
      });

    });
  }
}
