package ar.edu.utn.frba.dds.server;

import static io.javalin.apibuilder.ApiBuilder.before;
import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.patch;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.controllers.exceptions.UnauthorizedException;
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
import ar.edu.utn.frba.dds.controllers.generales.cron_task.CronTaskController;
import ar.edu.utn.frba.dds.controllers.generales.servicios.EtiquetasController;
import ar.edu.utn.frba.dds.controllers.generales.servicios.TipoEtiquetasController;
import ar.edu.utn.frba.dds.controllers.generales.user.LocalizacionUsuarioController;
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
import ar.edu.utn.frba.dds.controllers.middleware.AndMiddleware;
import ar.edu.utn.frba.dds.controllers.middleware.AuthMiddleware;
import ar.edu.utn.frba.dds.controllers.middleware.OrMiddleware;
import ar.edu.utn.frba.dds.controllers.middleware.SelfUserMiddleware;
import ar.edu.utn.frba.dds.controllers.middleware.factories.AutorizacionMiddlewareBuilder;
import ar.edu.utn.frba.dds.controllers.utils.TipoPermiso;
import ar.edu.utn.frba.dds.controllers.utils.TipoRol;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class Router {
  public static void init(EntityManagerFactory entityManagerFactory){
    AutorizacionMiddlewareBuilder autorizacion = new AutorizacionMiddlewareBuilder();

    Server.app().routes(() -> {

      //BEFORE ALL
      before(new AuthMiddleware());

      //INDEX
      get("/", (ctx) -> {ctx.redirect("/home");});
      get("/home", new IndexController());

      //INCIDENTES
      path("/incidentes", () -> {
        before(autorizacion.conComunidad().build());
        get( new IncidentesController()::getAll);
      });

      path("/aperturaIncidente", () ->{
        before(autorizacion.conComunidad().build());
        get(new IncidentesController()::vistaApertura);
        post(new IncidentesController()::abrir);
      });
      path("/cierreIncidente", () -> {
        before(autorizacion.conComunidad().build());
        get(new IncidentesController()::vistaCierre);
        post(new IncidentesController()::cerrar);
      });

      //RANKINGS
      path("/rankings", () -> {
        get(new RankingsController()::index);
        path("{id}",() ->{
          get(new RankingsController()::show);
        });
      });

      //CARGA MASIVA ENTIDADES
      path("/cargaMasiva", () ->{
        before(autorizacion.conRolesDePlataforma(TipoRol.ADMINISTRADOR).build());
        get(new CargaMasivaController()::create);
        post(new CargaMasivaController()::save);
      });

      // ENTIDADES
      path("/entidades", () -> {
        before(autorizacion.conPermisosPlataforma(TipoPermiso.ADMINISTRAR_ENTIDADES).build());
        path("crear", () -> {
          get("{idEntidadPrestadora}", new EntidadesController()::create);
          post(new EntidadesController()::save);
        });
        path("{id}",() ->{

          get(new EntidadesController()::edit);
          post(new EntidadesController()::update);
          post("delete", new EntidadesController()::delete);
          get("sacarEstablecimiento/{idEstablecimiento}", new EntidadesController()::sacarEstablecimiento);

        });
      });

      //ENTIDADES PRESTADORAS
      path("/entidadesPrestadoras",() -> {
        path("", () -> {
          before(autorizacion.conRolesDePlataforma(TipoRol.ADMINISTRADOR).build());

          get(new EntidadesPrestadorasController()::index);
          post(new EntidadesPrestadorasController()::save);
          get("crear",new EntidadesPrestadorasController()::create);
          get("crear/{id}",new EntidadesPrestadorasController()::crearConOrganismoControl);
          path("{id}", () ->{
            post(new EntidadesPrestadorasController()::update);
            post("delete", new EntidadesPrestadorasController()::delete);
            get("sacarEntidad/{idEntidad}", new EntidadesPrestadorasController()::sacarEntidad);
          });
        });
        before(autorizacion.conPermisosPlataforma(TipoPermiso.ADMINISTRAR_ENTIDADES_PRESTADORAS).build());
        get("{id}", new EntidadesPrestadorasController()::edit);
      });

      //ORGANISMOS DE CONTROL
      path("organismosControl", () -> {
        before(autorizacion.conPermisosPlataforma(TipoPermiso.ADMINISTRAR_ORGANISMOS_DE_CONTROL).build());
        get("crear", new OrganismosDeControlController()::create);
        get("{id}",new OrganismosDeControlController()::edit);
        post(new OrganismosDeControlController()::save);
        path("{id}", () -> {
          get("sacarEntidadPrestadora/{entidadPrestadora}", new OrganismosDeControlController()::sacarEntidadPrestadora);
          post("delete", new OrganismosDeControlController()::delete);
          post( new OrganismosDeControlController()::update);
        });
      });

      //ESTABLECIMIENTOS
      path("/establecimientos/", () -> {
        before(autorizacion.conPermisosPlataforma(TipoPermiso.ADMINISTRAR_ESTABLECIMIENTOS).build());
        path("crear", () ->{
          get("{idEntidad}", new EstablecimientosController()::create);
          post(new EstablecimientosController()::save);
        });
        path("{id}", () -> {
          get(new EstablecimientosController()::show);
          post(new EstablecimientosController()::update);
          post("delete", new EstablecimientosController()::delete);
          get("sacarServicio/{idServicioPrestado}", new EstablecimientosController()::sacarServicio);
          post("agregarServicio", new EstablecimientosController()::agregarServicio);
        });
      });

      //SERVICIOS
      path("/servicios", () ->{
        before(autorizacion.conPermisosPlataforma(TipoPermiso.ADMINISTRAR_SERVICIOS).build());
        get(new ServiciosController()::create);
        get("{idEstablecimiento}", new ServiciosController()::createFromEstablecimiento);
        post(new ServiciosController()::save);
      });

      //ETIQUETAS
      path("/etiquetas", () -> {
        before(autorizacion.conPermisosPlataforma(TipoPermiso.ADMINISTRAR_ETIQUETAS).build());
        get(new EtiquetasController()::index);
        post(new EtiquetasController()::save);
        post("{id}/delete", new EtiquetasController()::delete);
      });

      //TIPO ETIQUETAS
      path("/tipoEtiquetas", () -> {
        before(autorizacion.conPermisosPlataforma(TipoPermiso.ADMINISTRAR_ESTABLECIMIENTOS).build());
        post(new TipoEtiquetasController()::save);
      });

      //LOGIN
      path("/login", () -> {
        get(new LoginController()::login);
        post(new LoginController()::auth);
      });
      get("/logout", new LoginController()::delete);

      //REGISTER
      path("/register", () -> {
        //before(new ValidadorUsuarioMiddleware());
        get(new RegisterController()::create);
        post(new RegisterController()::save);
      });

      //ADMINISTRACIÓN DE USUARIOS
      path("/usuarios",()->{
        get(new AndMiddleware(
            autorizacion.conPermisosPlataforma(TipoPermiso.ADMINISTRAR_USUARIOS).build(),
            new UsuariosController()::index));
        path("{idUsuario}", () -> {
          before(new OrMiddleware(
                  new UnauthorizedException("No tiene permisos para acceder a este recurso"),
                  new SelfUserMiddleware(),
                  autorizacion.conPermisosPlataforma(TipoPermiso.ADMINISTRAR_USUARIOS).build()));
          path("edit", ()->{
            get(new UsuariosController()::edit);
            post(new UsuariosController()::update);
          });
          get(new UsuariosController()::show);
          get("interes", new UsuariosController()::getInteres);
          post("agregarServicio", new UsuariosController()::agregarServicio);
          post("agregarEntidad", new UsuariosController()::agregarEntidad);
          get("sacarServicio/{idServicio}", new UsuariosController()::sacarServicio);
          get("sacarEntidad/{idEntidad}", new UsuariosController()::sacarEntidad);
        });
      });

      //ROLES
      path("roles", ()->{
        get(new RolesController()::show);
        post(new RolesController()::setRol);
      });

      //COMUNIDADES
      get("/misComunidades", new ComunidadesController()::deUsuario);

      path("/comunidades", () ->{
          get(new ComunidadesController()::index);
          path("crear", () -> {
            before(new AutorizacionMiddlewareBuilder().conPermisosPlataforma(TipoPermiso.ADMINISTRAR_COMUNIDAD).build());
            get(new ComunidadesController()::create);
            post(new ComunidadesController()::save);
          });

          path("{idComunidad}", () ->{

            get(new ComunidadesController()::show);

            get("sacarMiembro/{idUsuario}", new AndMiddleware(
                new OrMiddleware(new UnauthorizedException("No tiene permisos para realizar esta acción."),
                    new SelfUserMiddleware(),
                    autorizacion.conPermisosPlataforma(TipoPermiso.ADMINISTRAR_COMUNIDAD).build()),
                new ComunidadesController()::sacarMiembro));
            get("unirMiembro/{idUsuario}", new AndMiddleware(
                new OrMiddleware(new UnauthorizedException("No tiene permisos para realizar esta acción."),
                    new SelfUserMiddleware(),
                    autorizacion.conPermisosPlataforma(TipoPermiso.ADMINISTRAR_COMUNIDAD).build()),
                new ComunidadesController()::unirMiembro));
            get("cambiarRol/{idUsuario}/{idServicio}/{nuevoRol}", new AndMiddleware(
                new OrMiddleware( new UnauthorizedException("No tiene permisos para realizar esta acción."),
                    autorizacion.conPermisosComunidad(TipoPermiso.ADMINISTRAR_COMUNIDAD).build(),
                    new AndMiddleware(
                        autorizacion.conComunidad().build(),
                        new SelfUserMiddleware())),
                new ComunidadesController()::cambiarRol ));

            //path("", () -> {
            path("edit",() -> {
              //before(autorizacion.conPermisosComunidad(TipoPermiso.ADMINISTRAR_COMUNIDAD).build());
              before(
                  new OrMiddleware(
                      new UnauthorizedException("No tiene permisos para realizar esta acción."),
                      autorizacion.conPermisosComunidad(TipoPermiso.ADMINISTRAR_COMUNIDAD).build(),
                      autorizacion.conPermisosPlataforma(TipoPermiso.ADMINISTRAR_COMUNIDAD).build()
                  )
              );
              get(new ComunidadesController()::edit);
              post(new ComunidadesController()::update);
            });

            /*post("agregarServicio", new AndMiddleware(
                                            autorizacion.conPermisosComunidad(TipoPermiso.ADMINISTRAR_COMUNIDAD).build(),
                                            new ComunidadesController()::agregarServicio));*/

            path("agregarServicio", () -> {
              before(
                  autorizacion.conPermisosComunidad(TipoPermiso.ADMINISTRAR_COMUNIDAD).build()
              );
              post(new ComunidadesController()::agregarServicio);
            });

            get("sacarServicio/{idServicio}", new AndMiddleware(
                autorizacion.conPermisosComunidad(TipoPermiso.ADMINISTRAR_COMUNIDAD).build(),
                new ComunidadesController()::quitarServicio));
            //});

          });
      });

      path("fusionarComunidades", () -> {
        before(autorizacion.conPermisosPlataforma(TipoPermiso.ADMINISTRAR_COMUNIDAD).build());
        get(new FusionComunidadesController()::create);
        post(new FusionComunidadesController()::save);
      });

      //CONTROLADORES PARA FORMULARIOS DINAMICOS
      path("/obtener",() -> {
        get("departamentos", new ObtenerDepartamentosController());
        get("localidades", new ObtenerLocalidadesController());
        get("entidades", new ObtenerEntidadesController());
        get("establecimientos", new ObtenerEstablecimientosController());
        get("serviciosPrestados", new ObtenerServiciosPrestadosController());
        get("incidentes", new ObtenerIncidentesController());
        get("departamentosIncidentes", new ObtenerDepartamentosIncidentesController());
        get("localidadesIncidentes", new ObtenerLocalidadesIncidentesController());
        get("entidadesIncidentes", new ObtenerEntidadesIncidentesController());
        get("establecimientosIncidentes", new ObtenerEstablecimientosIncidentesController());
        get("serviciosPrestadosIncidentes", new ObtenerServiciosPrestadosIncidentesController());
        get("departamentosCierreIncidentes", new ObtenerDepartamentosCierreIncidentesController());
        get("localidadesCierreIncidentes", new ObtenerLocalidadesCierreIncidentesController());
        get("incidentesCierre", new ObtenerIncidentesCierreController());
        get("etiquetas", new ObtenerEtiquetasController());
      });

      //Cron Tasks
      CronTaskController cronTaskController = new CronTaskController();
      path("/cron-task", () -> {
        before(autorizacion.conRolesDePlataforma(TipoRol.ADMINISTRADOR).build());
        get(cronTaskController::index);
        path("crear", () -> {
          get(cronTaskController::create);
          post(cronTaskController::save);
        });
        path("borrar/{id}",() -> {
          post(cronTaskController::delete);
        });
        path("editar/{id}",() -> {
          get(cronTaskController::edit);
          post(cronTaskController::update);
        });
        path("habilitar/{id}", () -> {
          post(cronTaskController::habilitar);
        });
      });

      post("usuario-localizacion", new LocalizacionUsuarioController());
    });
  }
}
