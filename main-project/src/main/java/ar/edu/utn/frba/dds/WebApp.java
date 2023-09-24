package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.controllers.AperturaIncidenteController;
import ar.edu.utn.frba.dds.controllers.CargaMasivaController;
import ar.edu.utn.frba.dds.controllers.CierreIncidenteController;
import ar.edu.utn.frba.dds.controllers.IncidentesController;
import ar.edu.utn.frba.dds.controllers.IndexController;
import ar.edu.utn.frba.dds.controllers.LoginController;
import ar.edu.utn.frba.dds.controllers.RankingsController;
import ar.edu.utn.frba.dds.controllers.UsuariosController;
import ar.edu.utn.frba.dds.repositorios.incidentes.IncidenteRepositorio;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.rendering.JavalinRenderer;
import java.io.IOException;
import java.util.function.Consumer;
import javax.persistence.EntityManager;

public class WebApp {
  public static void main(String[] args) {
    initTemplateEngine();

    Integer puerto = Integer.parseInt(System.getProperty("port", "8080"));

    Javalin app = Javalin.create(config()).start(puerto);

    EntityManager entityManager = (new CreadorEntityManager()).entityManagerCreado();

    app.get("/", new IndexController());
    app.get("/incidentes", new IncidentesController(new IncidenteRepositorio(entityManager)));
    app.get("/aperturaIncidente", new AperturaIncidenteController());
    app.get("/cierreIncidente", new CierreIncidenteController());
    app.get("/rankings", new RankingsController());
    app.get("/cargaMasiva", new CargaMasivaController());
    app.get("/usuarios", new UsuariosController());
    app.get("/login", new LoginController());
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
