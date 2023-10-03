package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.entidades.EntidadPrestadora;
import ar.edu.utn.frba.dds.modelos.entidades.OrganismoControl;
import ar.edu.utn.frba.dds.repositorios.comunidades.UsuarioRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.EntidadPrestadoraRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.OrganismoControlRepositorio;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public class EntidadesPrestadorasController implements ICrudViewsHandler {

  private EntidadPrestadoraRepositorio repoEntidadesPrestadoras;
  private UsuarioRepositorio repoUsuarios;
  private OrganismoControlRepositorio repoOrganismo;

  public EntidadesPrestadorasController(EntityManager entityManager){
    this.repoEntidadesPrestadoras = new EntidadPrestadoraRepositorio(entityManager);
    this.repoUsuarios = new UsuarioRepositorio(entityManager);
    this.repoOrganismo = new OrganismoControlRepositorio(entityManager);
  }

  @Override
  public void index(@NotNull Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    String paramSuccess = context.queryParam("success");

    if(paramSuccess != null){
      model.put("success", new Success(paramSuccess));
    }

    Usuario usuario = context.sessionAttribute("usuario");
    Persona persona = usuario.getPersonaAsociada();

    List<EntidadPrestadora> entidadesManejadas = repoEntidadesPrestadoras.manejadasPor(persona);
    if(entidadesManejadas != null && !entidadesManejadas.isEmpty()){
      model.put("entidadesPrestadorasGen", entidadesManejadas);
    }

    List<OrganismoControl> organismosManejados = repoOrganismo.manejadosPor(persona);
    if(organismosManejados != null && !organismosManejados.isEmpty()){
      model.put("organismosDeControl", organismosManejados);
    }

    String result = context.queryParam("result");
    if(result != null){
      switch(result){
        case "successCrearOrganismo":
          model.put("msg",  new MensajeVista("success", "Organismo de control creado correctamente"));
          break;
        case "successCrearEP":
          model.put("msg",  new MensajeVista("success", "Entidad prestadora creada correctamente"));
          break;
        case "successAddEntidad":
          model.put("msg",  new MensajeVista("success", "Entidad creada y agregada correctamente"));
          break;
        case "successSacarEntidad":
          model.put("msg",  new MensajeVista("success", "Entidad eliminada correctamente"));
          break;
      }
    }

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
      repoOrganismo.actualizar(organismoSeleccionado);
    }

    context.redirect("/entidadesPrestadoras?result=successCrearEP");
  }

  @Override
  public void edit(Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    EntidadPrestadora entidadPrestadora = repoEntidadesPrestadoras.buscarPorId(Integer.parseInt(context.pathParam("id")));
    model.put("entidad", entidadPrestadora);

    String result = context.queryParam("result");
    if(result != null){
      switch(result){
        case "successEditarEntidad":
          model.put("msg",  new MensajeVista("success", "Entidad prestadora modificada correctamente"));
          break;
      }
    }

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

    context.redirect("/entidadesPrestadoras/"+context.pathParam("id")+"?result=successEditarEntidad");
  }

  @Override
  public void delete(Context context) {

  }

  public void sacarEntidad(Context context){
    EntidadPrestadora entidadPrestadora = repoEntidadesPrestadoras.buscarPorId(Integer.parseInt(context.pathParam("id")));

    entidadPrestadora.sacarEntidadPorId(Integer.parseInt(context.pathParam("idEntidad")));

    repoEntidadesPrestadoras.actualizar(entidadPrestadora);

    context.redirect("/entidadesPrestadoras?result=successSacarEntidad");
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
