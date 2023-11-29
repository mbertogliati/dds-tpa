package ar.edu.utn.frba.dds.controllers.generales.entidades;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.server.EntityManagerContext;
import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.entidades.OrganismoControl;
import ar.edu.utn.frba.dds.repositorios.comunidades.UsuarioRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.EntidadPrestadoraRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.OrganismoControlRepositorio;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.server.EntityManagerContext;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class OrganismosDeControlController implements ICrudViewsHandler {

  private EntidadPrestadoraRepositorio repoEntidadesPrestadoras;
  private UsuarioRepositorio repoUsuarios;
  private OrganismoControlRepositorio repoOrganismo;

  public OrganismosDeControlController(){
    this.repoEntidadesPrestadoras = new EntidadPrestadoraRepositorio();
    this.repoUsuarios = new UsuarioRepositorio();
    this.repoOrganismo = new OrganismoControlRepositorio();
  }

  public void sacarEntidadPrestadora(Context context){
    OrganismoControl organismoControl = repoOrganismo.buscarPorId(Integer.parseInt(context.pathParam("id")));

    organismoControl.sacarEntidadPrestadoraPorId(Integer.parseInt(context.pathParam("entidadPrestadora")));

    repoOrganismo.actualizar(organismoControl);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Entidad prestadora eliminada correctamente."));
    context.redirect("/entidadesPrestadoras?success");
  }

  @Override
  public void index(@NotNull Context context) {
  }

  @Override
  public void show(Context context) {

  }

  @Override
  public void create(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    model.put("editable", true);
    model.put("crear", true);

    List<Usuario> usuarios = repoUsuarios.buscarTodos();
    model.put("usuarios",usuarios);
    model.put("accion", "organismosControl");

    context.render("verEntidadPrestadora.hbs", model);
  }

  @Override
  public void save(Context context) {
    OrganismoControl nuevoOrganismo = new OrganismoControl(context.formParam("nombre"));
    nuevoOrganismo.setPersonaAInformar(repoUsuarios.buscarPorId(Integer.parseInt(context.formParam("usuario"))).getPersonaAsociada());

    repoOrganismo.guardar(nuevoOrganismo);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Organismo de control creado correctamente."));
    context.redirect("/entidadesPrestadoras?successo");
  }

  @Override
  public void edit(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    OrganismoControl organismoControl = repoOrganismo.buscarPorId(Integer.parseInt(context.pathParam("id")));
    model.put("entidad", organismoControl);


    List<Usuario> usuarios = repoUsuarios.buscarTodos();
    model.put("usuarios",usuarios);

    model.put("accion", "organismosControl");

    context.render("verEntidadPrestadora.hbs", model);
  }

  @Override
  public void update(Context context) {
    OrganismoControl organismoControl = repoOrganismo.buscarPorId(Integer.parseInt(context.pathParam("id")));

    organismoControl.setNombre(context.formParam("nombre"));

    repoOrganismo.actualizar(organismoControl);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Organismo de control modificado correctamente."));
    context.redirect("/organismosControl/"+context.pathParam("id")+"?success");
  }

  @Override
  public void delete(Context context) {
    OrganismoControl organismoControl = repoOrganismo.buscarPorId(Integer.parseInt(context.pathParam("id")));

    repoOrganismo.eliminar(organismoControl);

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Organismo de control eliminado correctamente."));
    context.redirect("/entidadesPrestadoras?success");
  }

}
