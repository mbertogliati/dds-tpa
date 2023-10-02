package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.entidades.EntidadPrestadora;
import ar.edu.utn.frba.dds.modelos.entidades.OrganismoControl;
import ar.edu.utn.frba.dds.repositorios.comunidades.UsuarioRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.EntidadPrestadoraRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.OrganismoControlRepositorio;
import io.javalin.http.Context;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public class OrganismosDeControlController implements ICrudViewsHandler {

  private EntidadPrestadoraRepositorio repoEntidadesPrestadoras;
  private UsuarioRepositorio repoUsuarios;
  private OrganismoControlRepositorio repoOrganismo;

  public OrganismosDeControlController(EntityManager entityManager){
    this.repoEntidadesPrestadoras = new EntidadPrestadoraRepositorio(entityManager);
    this.repoUsuarios = new UsuarioRepositorio(entityManager);
    this.repoOrganismo = new OrganismoControlRepositorio(entityManager);
  }

  public void sacarEntidadPrestadora(Context context){
    OrganismoControl organismoControl = repoOrganismo.buscarPorId(Integer.parseInt(context.pathParam("id")));

    organismoControl.sacarEntidadPrestadoraPorId(Integer.parseInt(context.pathParam("entidadPrestadora")));

    repoOrganismo.actualizar(organismoControl);

    context.redirect("/entidadesPrestadoras?result=successSacarEntidadPrestadora");
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
    nuevoOrganismo.setPersonaAInformar(((Usuario)context.sessionAttribute("usuario")).getPersonaAsociada());

    repoOrganismo.guardar(nuevoOrganismo);

    context.redirect("/entidadesPrestadoras?result=successCrearOrganismo");
  }

  @Override
  public void edit(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    OrganismoControl organismoControl = repoOrganismo.buscarPorId(Integer.parseInt(context.pathParam("id")));
    model.put("entidad", organismoControl);

    String result = context.queryParam("result");
    if(result != null){
      switch(result){
        case "successEditarOrganismo":
          model.put("msg",  new MensajeVista("success", "Organismo de control modificado correctamente"));
          break;
      }
    }

    model.put("accion", "organismosControl");

    context.render("verEntidadPrestadora.hbs", model);
  }

  @Override
  public void update(Context context) {
    OrganismoControl organismoControl = repoOrganismo.buscarPorId(Integer.parseInt(context.pathParam("id")));

    organismoControl.setNombre(context.formParam("nombre"));

    repoOrganismo.actualizar(organismoControl);

    context.redirect("/organismosControl/"+context.pathParam("id")+"?result=successEditarOrganismo");
  }

  @Override
  public void delete(Context context) {

  }

  @Getter
  @Setter
  private class Success{
    private String caso;

    public Success(String caso){
      this.caso = caso;
    }
  }

}
