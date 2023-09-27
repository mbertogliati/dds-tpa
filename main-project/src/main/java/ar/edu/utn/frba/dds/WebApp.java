package ar.edu.utn.frba.dds;

import static kotlin.jvm.internal.Reflection.function;

import ar.edu.utn.frba.dds.controllers.AdministrarUsuarioController;
import ar.edu.utn.frba.dds.controllers.AperturaIncidenteController;
import ar.edu.utn.frba.dds.controllers.CerradorIncidenteController;
import ar.edu.utn.frba.dds.controllers.CreadorIncidenteController;
import ar.edu.utn.frba.dds.controllers.CargaMasivaController;
import ar.edu.utn.frba.dds.controllers.CierreIncidenteController;
import ar.edu.utn.frba.dds.controllers.IncidentesController;
import ar.edu.utn.frba.dds.controllers.IndexController;
import ar.edu.utn.frba.dds.controllers.LoginController;
import ar.edu.utn.frba.dds.controllers.LogoutController;
import ar.edu.utn.frba.dds.controllers.RegisterController;
import ar.edu.utn.frba.dds.controllers.VerificarRegisterController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerDepartamentosController;
import ar.edu.utn.frba.dds.controllers.RankingsController;
import ar.edu.utn.frba.dds.controllers.RecibirCargaMasiva;
import ar.edu.utn.frba.dds.controllers.UsuariosController;
import ar.edu.utn.frba.dds.controllers.VerRankingController;
import ar.edu.utn.frba.dds.controllers.VerificarLoginController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerEntidadesController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerEstablecimientosController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerIncidentesController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerLocalidadesController;
import ar.edu.utn.frba.dds.controllers.formulariosDinamicos.ObtenerServiciosController;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.rendering.JavalinRenderer;
import java.io.IOException;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import org.eclipse.jetty.server.session.SessionHandler;

public class WebApp {
  public static void main(String[] args) {
    initTemplateEngine();

    Integer puerto = Integer.parseInt(System.getProperty("port", "8080"));

    Javalin app = Javalin.create(config()).start(puerto);

    EntityManager entityManager = (new CreadorEntityManager()).entityManagerCreado();

    app.get("/", new IndexController());

    app.get("/incidentes", new IncidentesController(entityManager));

    app.get("/aperturaIncidente", new AperturaIncidenteController(entityManager));
    app.post("/aperturaIncidente", new CreadorIncidenteController(entityManager));

    app.get("/cierreIncidente", new CierreIncidenteController(entityManager));
    app.post("/cierreIncidente", new CerradorIncidenteController(entityManager));

    app.get("/rankings", new RankingsController(entityManager));
    app.get("/rankings/{id}", new VerRankingController(entityManager));

    app.get("/cargaMasiva", new CargaMasivaController());
    app.post("/cargaMasiva", new RecibirCargaMasiva(entityManager));

    app.get("/login", new LoginController());
    app.post("/login", new VerificarLoginController(entityManager));

    //ADMINISTRACIÓN DE USUARIOS (ALTA)
    app.get("/register", new RegisterController());
    app.post("/register", new VerificarRegisterController(entityManager));

    app.get("/logout", new LogoutController());

    //ADMINISTRACIÓN DE USUARIOS (ADMIN PLATAFORMA)
    app.get("/usuarios", new UsuariosController(entityManager));
    app.get("/usuario/{id}", new AdministrarUsuarioController(entityManager));

    //TODO: ADMINISTRACIÓN DE USUARIOS: MODIFICACIÓN

    //TODO: ADMINISTRACIÓN DE USUARIOS: BAJA
    app.post("/usuario/{id}",new AdministrarUsuarioController(entityManager));
    app.put("/usuario/{id}",new AdministrarUsuarioController(entityManager));
    app.delete("/usuario/{id}",new AdministrarUsuarioController(entityManager));

    //CONTROLADORES PARA FORMULARIOS DINAMICOS
    app.get("/obtenerDepartamentos", new ObtenerDepartamentosController(entityManager));
    app.get("/obtenerLocalidades", new ObtenerLocalidadesController(entityManager));
    app.get("/obtenerEntidades", new ObtenerEntidadesController(entityManager));
    app.get("/obtenerEstablecimientos", new ObtenerEstablecimientosController(entityManager));
    app.get("/obtenerServicios", new ObtenerServiciosController(entityManager));
    app.get("/obtenerIncidentes", new ObtenerIncidentesController(entityManager));

    //TODO: IMPLEMENTAR FactoryController e ICrudViewsHandler
    //TODO: USAR before PARA IMPLEMENTAR EL LOGIN Y CHEQUEOS
    //Before de javalin: https://github.com/tipsy/javalin-website-example/blob/master/src/main/java/app/Main.java#L39
    //Repo de Eze: https://github.com/dds-utn/proservices-mvc/tree/main
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

}
