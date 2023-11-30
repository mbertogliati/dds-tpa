package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.exceptions.ExternalException;
import ar.edu.utn.frba.dds.controllers.exceptions.FormInvalidoException;
import ar.edu.utn.frba.dds.controllers.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.controllers.exceptions.handlers.ExternalExceptionHandler;
import ar.edu.utn.frba.dds.controllers.exceptions.handlers.FormInvalidoHandler;
import ar.edu.utn.frba.dds.controllers.exceptions.handlers.UnauthorizedHandler;
import ar.edu.utn.frba.dds.controllers.utils.*;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.helper.ConditionalHelpers;
import io.github.flbulgarelli.jpa.extras.perthread.PerThreadEntityManagerAccess;
import io.github.flbulgarelli.jpa.extras.perthread.PerThreadEntityManagerProperties;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.rendering.JavalinRenderer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import lombok.With;

public class Server {
  private static Javalin app = null;
  private static InicializadorCronTask inicializadorCronTask = null;
  private static ConfiguradorAutorizacion configuradorAutorizacion = null;

  public static Javalin app() {
    if(app == null)
      throw new RuntimeException("App no inicializada");
    return app;
  }

  public static void init() {
    if(app == null) {
      System.out.println("Inicializando app...");
      LocalDateTime inicio = LocalDateTime.now();

      EntityManagerFactory entityManagerFactory = crearEntityManagerFactory();

      Integer puerto = Integer.parseInt(System.getProperty("port", System.getenv("APP_MAIN_PORT")));
      app = Javalin.create(config()).start(puerto);

      initTemplateEngine();
      configurarErrorHandlers();

      //configurarCronTasks(entityManagerFactory);
      configurarAutorizacion(entityManagerFactory);

      configurarExceptionHandlers();
      Router.init(entityManagerFactory);
      //Datetime difference
      System.out.println("Listo! "+ ChronoUnit.SECONDS.between(inicio, LocalDateTime.now())+" s");
    }
  }
  private static EntityManagerFactory crearEntityManagerFactory() {
    Map<String, String> propiedades = new HashMap<>();
    //hibernate naming convention
    propiedades.put("hibernate.connection.url",System.getenv("DB_CONNECTION_HOST"));
    propiedades.put("hibernate.connection.username", System.getenv("DB_CONNECTION_USERNAME"));
    propiedades.put("hibernate.connection.password",System.getenv("DB_CONNECTION_PASSWORD"));
    propiedades.put("hibernate.connection.driver_class", System.getenv("DB_CONNECTION_DRIVER_CLASS"));

    //Javax naming convention
    propiedades.put("javax.persistence.jdbc.url", System.getenv("DB_CONNECTION_HOST"));
    propiedades.put("javax.persistence.jdbc.user", System.getenv("DB_CONNECTION_USERNAME"));
    propiedades.put("javax.persistence.jdbc.password",System.getenv("DB_CONNECTION_PASSWORD"));
    propiedades.put("javax.persistence.jdbc.driver", System.getenv("DB_CONNECTION_DRIVER_CLASS"));

    propiedades.put("hibernate.hbm2ddl.auto", System.getenv("DB_CONNECTION_HIBERNATE_HBM2DDL_AUTO"));
    propiedades.put("hibernate.dialect", System.getenv("DB_CONNECTION_HIBERNATE_DIALECT"));
    propiedades.put("hibernate.show_sql", System.getenv("DB_CONNECTION_HIBERNATE_SHOW_SQL"));

    WithSimplePersistenceUnit.configure(p -> {
      p.putAll(propiedades);
    });
    return Persistence.createEntityManagerFactory("simple-persistence-unit", propiedades);
  }
  private static void configurarErrorHandlers(){
    app.error(404, new HttpErrorHandler(404, "Not Found", "El recurso solicitado no existe."));
    app.error(500, new HttpErrorHandler(500, "Internal Server Error", "Parece ha habido un problema con la aplicación. Por favor, intente nuevamente más tarde."));
  }
  private static void configurarExceptionHandlers(){
    app.exception(FormInvalidoException.class, new FormInvalidoHandler());
    app.exception(UnauthorizedException.class, new UnauthorizedHandler());
    app.exception(ExternalException.class, new ExternalExceptionHandler());
  }

  private static void configurarCronTasks(EntityManagerFactory entityManagerFactory){
    inicializadorCronTask = new InicializadorCronTask(entityManagerFactory);
    inicializadorCronTask.inicializar(entityManagerFactory);
  }

  private static void configurarAutorizacion(EntityManagerFactory entityManagerFactory){
    configuradorAutorizacion = new ConfiguradorAutorizacion(entityManagerFactory);
    configuradorAutorizacion.configurarRoles();
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
