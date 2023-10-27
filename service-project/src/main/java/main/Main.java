package main;
import ar.edu.utn.frba.controller.AceptarFusionController;
import ar.edu.utn.frba.controller.OrganizacionesRelacionadasController;
import ar.edu.utn.frba.controller.RechazarFusionController;
import ar.edu.utn.frba.domain.FusionadorOrganizaciones;
import io.javalin.Javalin;
import io.javalin.openapi.plugin.OpenApiConfiguration;
import io.javalin.openapi.plugin.OpenApiPlugin;
import io.javalin.openapi.plugin.redoc.ReDocConfiguration;
import io.javalin.openapi.plugin.redoc.ReDocPlugin;
import io.javalin.openapi.plugin.swagger.SwaggerConfiguration;
import io.javalin.openapi.plugin.swagger.SwaggerPlugin;

public class Main {
    public static void main(String[] args) {
        int portNumber = 8080;

        Javalin app = Javalin.create(config -> {
            OpenApiConfiguration openApiConfiguration = new OpenApiConfiguration();
            openApiConfiguration.getInfo().setTitle("Servicio 1 - Fusión de comunidades");
            config.plugins.register(new OpenApiPlugin(openApiConfiguration));
            config.plugins.register(new SwaggerPlugin(new SwaggerConfiguration()));
            config.plugins.register(new ReDocPlugin(new ReDocConfiguration()));
        }).start(portNumber);

        app.get("/", ctx -> ctx.result("Response inicio."));

        app.post("/propuestasDeFusion", new OrganizacionesRelacionadasController());

        app.post("/aceptarFusion", new AceptarFusionController());

        app.post("/rechazarFusion", new RechazarFusionController());

        System.out.format("\nCheck out ReDoc docs at http://localhost:%d%n/redoc", portNumber);
        System.out.format("\nCheck out Swagger UI docs at http://localhost:%d%n/swagger", portNumber);
    }
}
