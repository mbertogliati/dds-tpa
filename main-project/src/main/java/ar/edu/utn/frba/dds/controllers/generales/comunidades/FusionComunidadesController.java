package ar.edu.utn.frba.dds.controllers.generales.comunidades;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_fusion_g19.AdapterFusion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_fusion_g19.PropuestaFusion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_fusion_g19.ServicioFusion;
import io.javalin.http.Context;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

public class FusionComunidadesController implements ICrudViewsHandler {
    private final AdapterFusion servicioDeFusion = new ServicioFusion();

    public FusionComunidadesController(EntityManager entityManager){

    }

    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {
        Map<String, Object> model = GeneradorModel.model(context);
        List<PropuestaFusion> propuestasDisponibles = servicioDeFusion.obtenerPropuestas(null);
        context.render("fusionComunidades.hbs", model);
    }

    @Override
    public void save(Context context) {

    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }
}
