package ar.edu.utn.frba.dds.server;

import static io.javalin.apibuilder.ApiBuilder.before;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerDepartamentosCierreIncidentesController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerDepartamentosController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerDepartamentosIncidentesController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerEntidadesController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerEntidadesIncidentesController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerEstablecimientosController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerEstablecimientosIncidentesController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerEtiquetasController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerIncidentesCierreController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerIncidentesController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerLocalidadesCierreIncidentesController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerLocalidadesController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerLocalidadesIncidentesController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerServiciosPrestadosController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerServiciosPrestadosIncidentesController;
import ar.edu.utn.frba.dds.controllers.generales.comunidades.FusionComunidadesController;
import ar.edu.utn.frba.dds.controllers.generales.servicios.EtiquetasController;
import ar.edu.utn.frba.dds.controllers.generales.servicios.TipoEtiquetasController;
import ar.edu.utn.frba.dds.controllers.generales.user.RolesController;
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
import ar.edu.utn.frba.dds.controllers.middleware.factories.AutorizacionMiddlewareBuilder;
import ar.edu.utn.frba.dds.controllers.utils.TipoPermiso;
import ar.edu.utn.frba.dds.controllers.utils.TipoRol;

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
      path("/incidentes", () -> {
        before(new AutorizacionMiddlewareBuilder(entityManager).conComunidad().build());
        get( new IncidentesController(entityManager)::getAll);
      });

      path("/aperturaIncidente", () ->{
        before(new AutorizacionMiddlewareBuilder(entityManager).conComunidad().build());
        get(new IncidentesController(entityManager)::vistaApertura);
        post(new IncidentesController(entityManager)::abrir);
      });
      path("/cierreIncidente", () -> {
        before(new AutorizacionMiddlewareBuilder(entityManager).conComunidad().build());
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
        before(new AutorizacionMiddlewareBuilder(entityManager).conRolesDePlataforma(TipoRol.ADMINISTRADOR).build());
        get(new CargaMasivaController(entityManager)::create);
        post(new CargaMasivaController(entityManager)::save);
      });

      // ENTIDADES
      path("/entidades", () -> {
        before(new AutorizacionMiddlewareBuilder(entityManager).conPermisos(TipoPermiso.ADMINISTRAR_ENTIDADES).build());
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
        before(new AutorizacionMiddlewareBuilder(entityManager).conPermisos(TipoPermiso.ADMINISTRAR_ENTIDADES_PRESTADORAS).build());
        get("{id}", new EntidadesPrestadorasController(entityManager)::edit);
        path("", () -> {
          before(new AutorizacionMiddlewareBuilder(entityManager).conRolesDePlataforma(TipoRol.ADMINISTRADOR).build());

          get(new EntidadesPrestadorasController(entityManager)::index);
          post(new EntidadesPrestadorasController(entityManager)::save);
          get("crear",new EntidadesPrestadorasController(entityManager)::create);
          get("crear/{id}",new EntidadesPrestadorasController(entityManager)::crearConOrganismoControl);
          path("{id}", () ->{
            post(new EntidadesPrestadorasController(entityManager)::update);
            post("delete", new EntidadesPrestadorasController(entityManager)::delete);
            get("sacarEntidad/{idEntidad}", new EntidadesPrestadorasController(entityManager)::sacarEntidad);
          });
        });

      });

      //ORGANISMOS DE CONTROL
      path("/organismosControl", () -> {
        before(new AutorizacionMiddlewareBuilder(entityManager).conPermisos(TipoPermiso.ADMINISTRAR_ORGANISMOS_DE_CONTROL).build());
        get("{id}",new OrganismosDeControlController((entityManager))::edit);
        post(new OrganismosDeControlController(entityManager)::save);
        get("crear", new OrganismosDeControlController(entityManager)::create);
        path("{id}", () -> {
          get("sacarEntidadPrestadora/{entidadPrestadora}", new OrganismosDeControlController((entityManager))::sacarEntidadPrestadora);
          post("delete", new OrganismosDeControlController(entityManager)::delete);
          post( new OrganismosDeControlController((entityManager))::update);
        });

      });

      //ESTABLECIMIENTOS
      path("/establecimientos/", () -> {
        before(new AutorizacionMiddlewareBuilder(entityManager).conPermisos(TipoPermiso.ADMINISTRAR_ESTABLECIMIENTOS).build());
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
        before(new AutorizacionMiddlewareBuilder(entityManager).conPermisos(TipoPermiso.ADMINISTRAR_SERVICIOS).build());
        get(new ServiciosController(entityManager)::create);
        get("{idEstablecimiento}", new ServiciosController(entityManager)::createFromEstablecimiento);
        post(new ServiciosController(entityManager)::save);
      });

      //ETIQUETAS
      path("/etiquetas", () -> {
        before(new AutorizacionMiddlewareBuilder(entityManager).conPermisos(TipoPermiso.ADMINISTRAR_ETIQUETAS).build());
        get(new EtiquetasController(entityManager)::index);
        post(new EtiquetasController(entityManager)::save);
        post("{id}/delete", new EtiquetasController(entityManager)::delete);
      });

      //TIPO ETIQUETAS
      path("/tipoEtiquetas", () -> {
        before(new AutorizacionMiddlewareBuilder(entityManager).conPermisos(TipoPermiso.ADMINISTRAR_ESTABLECIMIENTOS).build());
        post(new TipoEtiquetasController(entityManager)::save);
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

      //ADMINISTRACIÓN DE USUARIOS
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

      //ROLES
      path("roles", ()->{
        before(new AutorizacionMiddlewareBuilder(entityManager).conPermisos(TipoPermiso.ADMINISTRAR_ROLES).build());
        get(new RolesController(entityManager)::show);
        post(new RolesController(entityManager)::setRol);
      });

      //COMUNIDADES
      get("/misComunidades", new ComunidadesController(entityManager)::deUsuario);
      path("/comunidades", () ->{
        get(new ComunidadesController(entityManager)::index);
        path("crear", () -> {
          before(new AutorizacionMiddlewareBuilder(entityManager).conPermisos(TipoPermiso.ADMINISTRAR_COMUNIDAD).build());
          get(new ComunidadesController(entityManager)::create);
          post(new ComunidadesController(entityManager)::save);
        });

        path("{idComunidad}", () ->{
          before(new AutorizacionMiddlewareBuilder(entityManager).conComunidad().build());
          get(new ComunidadesController(entityManager)::show);
          path("", () -> {
            before(new AutorizacionMiddlewareBuilder(entityManager).conPermisos(TipoPermiso.ADMINISTRAR_COMUNIDAD).build());
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
      });

      path("fusionarComunidades", () -> {
        before(new AutorizacionMiddlewareBuilder(entityManager).conPermisos(TipoPermiso.ADMINISTRAR_COMUNIDAD).build());
        get(new FusionComunidadesController(entityManager)::create);
        post(new FusionComunidadesController(entityManager)::save);
      });

      //CONTROLADORES PARA FORMULARIOS DINAMICOS
      path("/obtener",() -> {
        get("departamentos", new ObtenerDepartamentosController(entityManager));
        get("localidades", new ObtenerLocalidadesController(entityManager));
        get("entidades", new ObtenerEntidadesController(entityManager));
        get("establecimientos", new ObtenerEstablecimientosController(entityManager));
        get("serviciosPrestados", new ObtenerServiciosPrestadosController(entityManager));
        get("incidentes", new ObtenerIncidentesController(entityManager));
        get("departamentosIncidentes", new ObtenerDepartamentosIncidentesController(entityManager));
        get("localidadesIncidentes", new ObtenerLocalidadesIncidentesController(entityManager));
        get("entidadesIncidentes", new ObtenerEntidadesIncidentesController(entityManager));
        get("establecimientosIncidentes", new ObtenerEstablecimientosIncidentesController(entityManager));
        get("serviciosPrestadosIncidentes", new ObtenerServiciosPrestadosIncidentesController(entityManager));
        get("departamentosCierreIncidentes", new ObtenerDepartamentosCierreIncidentesController(entityManager));
        get("localidadesCierreIncidentes", new ObtenerLocalidadesCierreIncidentesController(entityManager));
        get("incidentesCierre", new ObtenerIncidentesCierreController(entityManager));
        get("etiquetas", new ObtenerEtiquetasController(entityManager));
      });

    });
  }
}
