package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.controllers.CargaMasivaController;
import ar.edu.utn.frba.dds.controllers.EntidadesController;
import ar.edu.utn.frba.dds.controllers.EntidadesPrestadorasController;
import ar.edu.utn.frba.dds.controllers.EstablecimientosController;
import ar.edu.utn.frba.dds.controllers.IncidentesController;
import ar.edu.utn.frba.dds.controllers.IndexController;
import ar.edu.utn.frba.dds.controllers.LoginController;
import ar.edu.utn.frba.dds.controllers.RegisterController;
import ar.edu.utn.frba.dds.controllers.ServiciosController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerDepartamentosController;
import ar.edu.utn.frba.dds.controllers.RankingsController;
import ar.edu.utn.frba.dds.controllers.UsuariosController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerEntidadesController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerEstablecimientosController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerIncidentesController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerLocalidadesController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerServiciosController;
import ar.edu.utn.frba.dds.controllers.utils.CreadorEntityManager;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.rendering.JavalinRenderer;
import java.io.IOException;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import static io.javalin.apibuilder.ApiBuilder.*;

public class WebApp {
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

    EntityManager entityManager = (new CreadorEntityManager()).entityManagerCreado();
    app.routes(() -> {
      //INDEX
      get("/", new IndexController());

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
        path("{id}",() ->{
          get(new EntidadesController(entityManager)::edit);
          post(new EntidadesController(entityManager)::update);
        });
      });

      //ENTIDADES PRESTADORAS
      get("/entidadesPrestadoras", new EntidadesPrestadorasController(entityManager)::index);

      //ESTABLECIMIENTOS
      path("/establecimientos/{id}", () ->{
        get(new EstablecimientosController(entityManager)::show);
        post(new EstablecimientosController(entityManager)::update);
        get("sacarServicio/{idServicioPrestado}", new EstablecimientosController(entityManager)::sacarServicio);
        post("agregarServicio", new EstablecimientosController(entityManager)::agregarServicio);
      });

      //SERVICIOS
      path("/servicios", () ->{
        get(new ServiciosController(entityManager)::create);
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
        });
      });

      //CONTROLADORES PARA FORMULARIOS DINAMICOS
      path("/obtener",() -> {
        get("departamentos", new ObtenerDepartamentosController(entityManager));
        get("localidades", new ObtenerLocalidadesController(entityManager));
        get("entidades", new ObtenerEntidadesController(entityManager));
        get("establecimientos", new ObtenerEstablecimientosController(entityManager));
        get("servicios", new ObtenerServiciosController(entityManager));
        get("incidentes", new ObtenerIncidentesController(entityManager));
      });

    });


    //TODO: IMPLEMENTAR FactoryController

    //Repo de Eze: https://github.com/dds-utn/proservices-mvc/tree/main

    //TODO: Unificar footer y header para poder reutilizar plantillas

    //TODO: Chequear la asignación de ubicaciones a personas y entidades/establecimientos (por eso falla el filtrado de incidentes para revisión)

    //TODO: Se debe permitir la administración de comunidades y miembros
    //TODO: Se debe permitir la asignación de personas a servicios de interés
    //TODO: Se debe permitir la asociación de localizaciones a personas
    //TODO: Se debe permitir la administración de entidades prestadoras y organismos de control
    //TODO: Se debe permitir marcar como “afectado” u “observador” a los miembros de las comunidades para un servicio en particular.
    //TODO: Se debe permitir enviar información a entidades prestadoras y organismos de control
    //TODO: Se debe permitir generar los rankings de incidentes
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

  private static boolean requiereAutenticacion(String path){
    return
        !(
            path.equals("/login")
          || path.equals("/register")
        );
  }

}
