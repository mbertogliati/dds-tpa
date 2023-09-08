package main;
import ar.edu.utn.frba.controller.AceptarFusionController;
import ar.edu.utn.frba.controller.OrganizacionesRelacionadasController;
import ar.edu.utn.frba.controller.RechazarFusionController;
import ar.edu.utn.frba.domain.FusionadorOrganizaciones;
import io.javalin.Javalin;
public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);

        app.get("/", ctx -> ctx.result("Response inicio."));

        app.post("/propuestasDeFusion", new OrganizacionesRelacionadasController());

        app.post("/aceptarFusion", new AceptarFusionController());

        app.post("/rechazarFusion", new RechazarFusionController());
    }
}
