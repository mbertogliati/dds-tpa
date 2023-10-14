package ar.edu.utn.frba.dds.controllers.generales.comunidades;

import ar.edu.utn.frba.dds.controllers.utils.ConverterComunidadOrganizacion;
import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.ErrorFusionException;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.PropuestaFusionComunidad;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_fusion_g19.AdapterFusion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_fusion_g19.Organizacion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_fusion_g19.ServicioFusion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_fusion_g19.SolicitudFusion;
import ar.edu.utn.frba.dds.repositorios.comunidades.ComunidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.IntentoFusionComunidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.RolRepositorio;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

public class FusionComunidadesController implements ICrudViewsHandler {
    private final AdapterFusion servicioDeFusion = new ServicioFusion();
    private ComunidadRepositorio repoComunidad;
    private IntentoFusionComunidadRepositorio repoIntentoFusionComunidades;
    private RolRepositorio repoRol;
    private EntityManager entityManager;

    public FusionComunidadesController(EntityManager entityManager){
        this.repoComunidad = new ComunidadRepositorio(entityManager);
        this.repoIntentoFusionComunidades = new IntentoFusionComunidadRepositorio(entityManager);
        this.repoRol = new RolRepositorio(entityManager);
        this.entityManager = entityManager;
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

        Usuario usuario = context.sessionAttribute("usuario");
        List<Organizacion> listaOrganizacion = repoComunidad.obtenerTodas().stream().filter(c -> c.getMembresias().stream().anyMatch(m -> m.getPersona().getId() == usuario.getPersonaAsociada().getId() && m.getRolComunidad().getId() == repoRol.rolAdminComunidad().getId())).map(ConverterComunidadOrganizacion::obtenerOrganizacion).toList();
        List<PropuestaFusionComunidad> propuestasDisponibles = servicioDeFusion.obtenerPropuestas(listaOrganizacion).stream().map(
            p ->
            new PropuestaFusionComunidad(
                repoComunidad.obtenerComunidadPorId(p.getIdOrganizacion1().intValue()),
                repoComunidad.obtenerComunidadPorId(p.getIdOrganizacion2().intValue()))
        ).toList();
        model.put("propuestas",propuestasDisponibles);
        context.render("fusionComunidades.hbs", model);
    }

    @Override
    public void save(Context context) {
        Comunidad comunidad1 = repoComunidad.obtenerComunidadPorId(Integer.parseInt(context.formParam("idComunidad1")));
        Comunidad comunidad2 = repoComunidad.obtenerComunidadPorId(Integer.parseInt(context.formParam("idComunidad2")));

        SolicitudFusion solicitudFusion = new SolicitudFusion();
        solicitudFusion.setOrganizacion1(ConverterComunidadOrganizacion.obtenerOrganizacion(comunidad1));
        solicitudFusion.setOrganizacion2(ConverterComunidadOrganizacion.obtenerOrganizacion(comunidad2));

        try {
            Comunidad comunidadFusionada = ConverterComunidadOrganizacion.obtenerComunidad(servicioDeFusion.aceptarFusion(solicitudFusion).getOrganizacionFusionada(), comunidad1, comunidad2, entityManager);

            comunidad1.agregarIntentoFusion(LocalDateTime.now(), comunidad2);
            comunidad2.agregarIntentoFusion(LocalDateTime.now(), comunidad1);

            repoComunidad.eliminarComunidad(comunidad1);
            repoComunidad.eliminarComunidad(comunidad2);
            repoComunidad.guardarComunidad(comunidadFusionada);

            context.sessionAttribute("msg",new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "'" + comunidad1.getNombre() + "' y '" + comunidad2.getNombre() + "' fusionadas correctamente."));
            context.redirect("/fusionarComunidades");
        }catch (ErrorFusionException e){
            context.sessionAttribute("msg",new MensajeVista(MensajeVista.TipoMensaje.ERROR,"Ocurrió un error al intentar realizar la fusión."));
            context.redirect("/fusionarComunidades");
        }
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
