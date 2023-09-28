package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.entidades.EntidadPrestadora;
import ar.edu.utn.frba.dds.modelos.entidades.OrganismoControl;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public class EntidadesPrestadorasController implements ICrudViewsHandler {

  public EntidadesPrestadorasController(EntityManager entityManager){
  }

  @Override
  public void index(@NotNull Context context) {
    Map<String, Object> model = GeneradorModel.model(context);

    String paramSuccess = context.queryParam("success");

    if(paramSuccess != null){
      model.put("success", new Success(paramSuccess));
    }

    if(context.sessionAttribute("adminPlataforma") != null){
      model.put("adminPlataforma", true);
    }

    List<EntidadPrestadora> entidadesPrestadoras = context.sessionAttribute("entidadesManejadas");
    if(entidadesPrestadoras != null && !entidadesPrestadoras.isEmpty()) {
      model.put("entidadesPrestadorasGen", entidadesPrestadoras);
    }

    List<OrganismoControl> organismosDeControl = context.sessionAttribute("organismosManejados");
    if(organismosDeControl != null && !organismosDeControl.isEmpty()) {
      model.put("organismosDeControl", organismosDeControl);
    }
    Usuario usuario = context.sessionAttribute("usuario");
    Persona persona = usuario.getPersonaAsociada();
    model.put("persona", persona);

    context.render("entidadesPrestadoras.hbs", model);
  }

  @Override
  public void show(Context context) {

  }

  @Override
  public void create(Context context) {

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

  @Getter
  @Setter
  private class Success{
    private String caso;

    public Success(String caso){
      this.caso = caso;
    }
  }

}
