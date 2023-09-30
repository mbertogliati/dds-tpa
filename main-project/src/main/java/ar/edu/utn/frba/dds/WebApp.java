package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.controllers.CargaMasivaController;
import ar.edu.utn.frba.dds.controllers.ComunidadesController;
import ar.edu.utn.frba.dds.controllers.EntidadesController;
import ar.edu.utn.frba.dds.controllers.EntidadesPrestadorasController;
import ar.edu.utn.frba.dds.controllers.EstablecimientosController;
import ar.edu.utn.frba.dds.controllers.GenerarRankingController;
import ar.edu.utn.frba.dds.controllers.IncidentesController;
import ar.edu.utn.frba.dds.controllers.IndexController;
import ar.edu.utn.frba.dds.controllers.LoginController;
import ar.edu.utn.frba.dds.controllers.OrganismosDeControlController;
import ar.edu.utn.frba.dds.controllers.RegisterController;
import ar.edu.utn.frba.dds.controllers.ServiciosController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerDatosController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerDepartamentosController;
import ar.edu.utn.frba.dds.controllers.RankingsController;
import ar.edu.utn.frba.dds.controllers.UsuariosController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerEntidadesController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerEstablecimientosController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerIncidentesController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerLocalidadesController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerServiciosController;
import ar.edu.utn.frba.dds.controllers.utils.CreadorCronTask;
import ar.edu.utn.frba.dds.controllers.utils.CreadorEntityManager;
import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.helper.ConditionalHelpers;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.rendering.JavalinRenderer;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import static io.javalin.apibuilder.ApiBuilder.*;

public class WebApp {
  public static CreadorCronTask creadorCronTask = new CreadorCronTask();
  public static void main(String[] args) {
    initTemplateEngine();

    Integer puerto = Integer.parseInt(System.getProperty("port", "8080"));

    Javalin app = Javalin.create(config()).start(puerto);

    app.before(ctx -> {
      if(requiereAutenticacion(ctx.path()) && ctx.sessionAttribute("usuario") == null){
        ctx.status(401).result("Unauthorized");
        ctx.redirect("/login");
      }
    });
    configurarCronTasks();

    EntityManager entityManager = (new CreadorEntityManager()).entityManagerCreado();
    app.routes(() -> {
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
          get("sacarEstablecimiento/{idEstablecimiento}", new EntidadesController(entityManager)::sacarEstablecimiento);
        });
      });

      //ENTIDADES PRESTADORAS
      path("/entidadesPrestadoras",() -> {
        get(new EntidadesPrestadorasController(entityManager)::index);
        post(new EntidadesPrestadorasController(entityManager)::save);
        get("crear",new EntidadesPrestadorasController(entityManager)::create);
        path("{id}", () ->{
          get(new EntidadesPrestadorasController(entityManager)::edit);
          post(new EntidadesPrestadorasController(entityManager)::update);
          get("sacarEntidad/{idEntidad}", new EntidadesPrestadorasController(entityManager)::sacarEntidad);
        });
      });

      //ORGANISMOS DE CONTROL
      path("/organismosControl", () -> {
        post(new OrganismosDeControlController(entityManager)::save);
        get("crear", new OrganismosDeControlController(entityManager)::create);
        get("/{id}/sacarEntidadPrestadora/{entidadPrestadora}", new OrganismosDeControlController((entityManager))::sacarEntidadPrestadora);
        get("/{id}", new OrganismosDeControlController((entityManager))::edit);
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
        get(new RegisterController(entityManager)::create);
        post(new RegisterController(entityManager)::save);
      });

      //ADMINISTRACIÓN DE USUARIOS (ADMIN PLATAFORMA)
      path("/usuarios",()->{
        get(new UsuariosController(entityManager)::index);
        path("{id}", () -> {
          get(new UsuariosController(entityManager)::show);
          get("edit",new UsuariosController(entityManager)::edit);
          post("edit",new UsuariosController(entityManager)::update);
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
          post("agregarServicio", new ComunidadesController(entityManager)::agregarServicio);
          get("sacarServicio/{idServicio}", new ComunidadesController(entityManager)::quitarServicio);
          get("sacarMiembro/{idUsuario}", new ComunidadesController(entityManager)::sacarMiembro);
          get("unirMiembro/{idUsuario}", new ComunidadesController(entityManager)::unirMiembro);
          get("cambiarRol/{idUsuario}/{idServicio}/{nuevoRol}", new ComunidadesController(entityManager)::cambiarRol);
        });
      });

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


    //Repo de Eze: https://github.com/dds-utn/proservices-mvc/tree/main

    //TODO: Se debe permitir enviar información a entidades prestadoras y organismos de control
    //TODO: Se debe permitir generar los rankings de incidentes
    //TODO: Usar cron tasks para el envío de notificaciones sin apuro

    //TODO: IMPLEMENTAR FactoryController
  }

  private static Consumer<JavalinConfig> config(){
    return config -> {
      config.staticFiles.add(staticFiles -> {
        staticFiles.hostedPath = "/";
        staticFiles.directory = "/public";
      });
    };
  }

  private static void initTemplateEngine() {
    JavalinRenderer.register((path, model, context)->{ // Función que renderiza el template Handlebars handlebars = new Handlebars();
      Handlebars handlebars = new Handlebars();

      handlebars.registerHelper("eq",ConditionalHelpers.eq);
      handlebars.registerHelper("or",ConditionalHelpers.or);
      handlebars.registerHelper("and",ConditionalHelpers.and);
      Template template = null;
          try {
            template = handlebars.compile("templates/" + path.replace(".hbs", ""));
            return template.apply(model);
          } catch (IOException e) {
            e.printStackTrace();
            context.status(HttpStatus.NOT_FOUND);
            return "No se encuentra la página indicada...";
          }
        }, ".hbs" // Extensión del archivo de template
    );
  }
  private static void configurarCronTasks(){
    GenerarRankingController generarRankingController = new GenerarRankingController(
        new CreadorEntityManager().entityManagerCreado()
    );
    creadorCronTask.crearCronTaskSemanal(generarRankingController::generarRankingUltimaSemana, DayOfWeek.MONDAY, LocalTime.parse("00:00"));
  }

  private static boolean requiereAutenticacion(String path){
    return
        !(path.equals("/login")
        || path.equals("/register")
        || path.startsWith("/obtener")
        || path.startsWith("/noAuth"));
  }

}
