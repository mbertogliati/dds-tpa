package ar.edu.utn.frba.dds.controllers.generales.entidades;

import ar.edu.utn.frba.dds.controllers.utils.FiltradorEntidades;
import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.server.EntityManagerContext;
import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.entidades.EntidadPrestadora;
import ar.edu.utn.frba.dds.modelos.entidades.OrganismoControl;
import ar.edu.utn.frba.dds.repositorios.comunidades.UsuarioRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.EntidadPrestadoraRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.OrganismoControlRepositorio;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.server.EntityManagerContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class EntidadesPrestadorasController implements ICrudViewsHandler {

  private EntidadPrestadoraRepositorio repoEntidadesPrestadoras;
  private UsuarioRepositorio repoUsuarios;
  private OrganismoControlRepositorio repoOrganismo;

  public EntidadesPrestadorasController(){
    this.repoEntidadesPrestadoras = new EntidadPrestadoraRepositorio();
    this.repoUsuarios = new UsuarioRepositorio();
    this.repoOrganismo = new OrganismoControlRepositorio();
  }

  @Override
  public void index(@NotNull Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    Usuario usuario = context.sessionAttribute("usuario");
    Persona persona = usuario.getPersonaAsociada();

    EntidadPrestadora entidadPrestadora = context.sessionAttribute("entidadPrestadora");
    if(entidadPrestadora != null){
      List<EntidadPrestadora> entidades = new ArrayList<>();
      entidades.add(entidadPrestadora);
      model.put("entidadesPrestadorasGen", entidades);
    }
    /*
    List<EntidadPrestadora> entidadesManejadas = repoEntidadesPrestadoras.manejadasPor(persona);
    if(entidadesManejadas != null && !entidadesManejadas.isEmpty()){
      List<EntidadPrestadora> entidadesNuevas = FiltradorEntidades.entidadesPrestadoras(entidadesManejadas);
      model.put("entidadesPrestadorasGen", entidadesNuevas);
    }
    */

    OrganismoControl organismoControl = context.sessionAttribute("organismoControl");
    if(organismoControl != null){
      List<OrganismoControl> organismos = new ArrayList<>();
      organismos.add(organismoControl);
      model.put("organismosDeControl", organismos);
    }
    /*
    List<OrganismoControl> organismosManejados = repoOrganismo.manejadosPor(persona);
    if(organismosManejados != null && !organismosManejados.isEmpty()){
      List<OrganismoControl> organismosNuevos = FiltradorEntidades.organismosDeControl(organismosManejados);
      model.put("organismosDeControl", organismosNuevos);
    }
    */


    model.put("persona", persona);

    context.render("entidadesPrestadoras.hbs", model);
  }

  @Override
  public void show(Context context) {

  }

  @Override
  public void create(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    model.put("editable", true);
    List<Usuario> usuarios = repoUsuarios.buscarTodos();
    model.put("usuarios",usuarios);
    model.put("accion", "entidadesPrestadoras");
    model.put("crear", true);

    context.render("verEntidadPrestadora.hbs", model);
  }

  public void crearConOrganismoControl(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    model.put("editable", true);
    List<Usuario> usuarios = repoUsuarios.buscarTodos();
    model.put("usuarios",usuarios);
    model.put("organismo",repoOrganismo.buscarPorId(Integer.parseInt(context.pathParam("id"))));
    model.put("accion", "entidadesPrestadoras");
    model.put("crear", true);

    context.render("verEntidadPrestadora.hbs", model);
  }

  @Override
  public void save(Context context) {
    EntidadPrestadora nuevaEntidad = new EntidadPrestadora(context.formParam("nombre"));
    nuevaEntidad.setPersonaAInformar(repoUsuarios.buscarPorId(Integer.parseInt(context.formParam("usuario"))).getPersonaAsociada());

    repoEntidadesPrestadoras.guardar(nuevaEntidad);

    if(context.formParam("organismo") != null && !Objects.equals(context.formParam("organismo"), "")){
      OrganismoControl organismoSeleccionado = repoOrganismo.buscarPorId(Integer.parseInt(context.formParam("organismo")));
      organismoSeleccionado.getEntidadesPrestadoras().add(nuevaEntidad);
      nuevaEntidad.setOrganismoControl(organismoSeleccionado);
      repoOrganismo.actualizar(organismoSeleccionado);
    }

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Entidad prestadora creada correctamente."));
    context.redirect("/entidadesPrestadoras?success");
  }

  @Override
  public void edit(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    EntidadPrestadora entidadPrestadora = repoEntidadesPrestadoras.buscarPorId(Integer.parseInt(context.pathParam("id")));
    model.put("entidad", entidadPrestadora);

    List<Usuario> usuarios = repoUsuarios.buscarTodos();
    model.put("usuarios",usuarios);
    model.put("accion", "entidadesPrestadoras");

    context.render("verEntidadPrestadora.hbs", model);
  }

  @Override
  public void update(Context context) {
    EntidadPrestadora entidadPrestadora = repoEntidadesPrestadoras.buscarPorId(Integer.parseInt(context.pathParam("id")));

    entidadPrestadora.setNombre(context.formParam("nombre"));

    repoEntidadesPrestadoras.actualizar(entidadPrestadora);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Entidad prestadora modificada correctamente."));
    context.redirect("/entidadesPrestadoras/"+context.pathParam("id")+"?success");
  }

  @Override
  public void delete(Context context) {
    EntidadPrestadora entidadPrestadora = repoEntidadesPrestadoras.buscarPorId(Integer.parseInt(context.pathParam("id")));

    repoEntidadesPrestadoras.eliminar(entidadPrestadora);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Entidad prestadora eliminada correctamente."));
    context.redirect("/entidadesPrestadoras?success");
  }

  public void sacarEntidad(Context context){
    EntidadPrestadora entidadPrestadora = repoEntidadesPrestadoras.buscarPorId(Integer.parseInt(context.pathParam("id")));

    entidadPrestadora.sacarEntidadPorId(Integer.parseInt(context.pathParam("idEntidad")));

    repoEntidadesPrestadoras.actualizar(entidadPrestadora);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Entidad eliminada correctamente."));
    context.redirect("/entidadesPrestadoras?success");
  }

}
