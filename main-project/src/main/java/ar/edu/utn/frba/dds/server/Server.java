package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.exceptions.FormInvalidoException;
import ar.edu.utn.frba.dds.controllers.utils.InicializadorCronTask;
import ar.edu.utn.frba.dds.controllers.utils.CreadorEntityManager;
import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.helper.ConditionalHelpers;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.rendering.JavalinRenderer;
import java.io.IOException;
import java.util.function.Consumer;
import javax.persistence.EntityManager;

public class Server {
  private static Javalin app = null;
  private static InicializadorCronTask inicializadorCronTask = null;

  public static Javalin app() {
    if(app == null)
      throw new RuntimeException("App no inicializada");
    return app;
  }

  public static void init() {
    if(app == null) {
      EntityManager entityManager = (new CreadorEntityManager()).entityManagerCreado();
      Integer puerto = Integer.parseInt(System.getProperty("port", System.getenv("APP_MAIN_PORT")));
      app = Javalin.create(config()).start(puerto);

      app.exception(FormInvalidoException.class, (e, ctx) -> {
        ctx.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.ERROR, e.getMessage()));
        ctx.redirect(ctx.path() + "?error");
      });

      //configurarCronTasks();
      initTemplateEngine();

      inicializadorCronTask = new InicializadorCronTask();
      inicializadorCronTask.inicializar();

      Router.init(entityManager);
    }
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

          handlebars.registerHelper("eq", ConditionalHelpers.eq);
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
}
