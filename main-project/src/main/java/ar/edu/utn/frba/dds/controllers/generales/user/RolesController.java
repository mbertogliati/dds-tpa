package ar.edu.utn.frba.dds.controllers.generales.user;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.comunidades.Membresia;
import ar.edu.utn.frba.dds.modelos.comunidades.Rol;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.entidades.EntidadPrestadora;
import ar.edu.utn.frba.dds.modelos.entidades.OrganismoControl;
import ar.edu.utn.frba.dds.repositorios.comunidades.ComunidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.RolRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.UsuarioRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.EntidadPrestadoraRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.OrganismoControlRepositorio;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.server.EntityManagerContext;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

public class RolesController {
  RolRepositorio repoRol;
  UsuarioRepositorio repoUsuario;
  ComunidadRepositorio repoComunidad;
  OrganismoControlRepositorio repoOrganismos;
  EntidadPrestadoraRepositorio repoEntidades;

  public RolesController(){
    this.repoRol = new RolRepositorio();
    this.repoUsuario = new UsuarioRepositorio();
    this.repoComunidad = new ComunidadRepositorio();
    this.repoOrganismos = new OrganismoControlRepositorio();
    this.repoEntidades = new EntidadPrestadoraRepositorio();
  }

  public void show(Context context){
    Map<String,Object> model = GeneradorModel.model(context);

    Usuario usuario = context.sessionAttribute("usuario");

    List<Rol> rolesPlataforma = usuario.getRoles();
    model.put("rolesPlataforma", rolesPlataforma);

    List<Membresia> membresias = usuario.getPersonaAsociada().getMembresias();
    model.put("membresias", membresias);

    List<OrganismoControl> organismosControl = repoOrganismos.manejadosPor(usuario.getPersonaAsociada());
    model.put("organismosControl", organismosControl);

    List<EntidadPrestadora> entidadesPrestadoras = repoEntidades.manejadasPor(usuario.getPersonaAsociada());
    model.put("entidadesPrestadoras", entidadesPrestadoras);

    context.render("roles.hbs", model);
  }

  public void setRol(Context context){
    context.consumeSessionAttribute("comunidad");
    context.consumeSessionAttribute("adminComunidad");
    context.consumeSessionAttribute("adminPlataforma");
    context.consumeSessionAttribute("organismoControl");
    context.consumeSessionAttribute("entidadPrestadora");

    String idRolRecibido = context.formParam("rol");
    if(idRolRecibido == null){
      context.redirect("/roles");
      return;
    }
    int index = idRolRecibido.indexOf('-');

    int idRol;

    if(index != -1){
      String rol = idRolRecibido.substring(0, index);
      if(rol.equals("organismo")){
        idRol = -1;
      } else if (rol.equals("entidad")) {
        idRol = -2;
      }else{
        idRol = Integer.parseInt(rol);
      }
    }else{
      idRol = Integer.parseInt(idRolRecibido);
    }

    int idOrganizacion = Integer.parseInt(idRolRecibido.substring(index + 1));
    if(idRol == -1){
      OrganismoControl organismoControl = repoOrganismos.buscarPorId(idOrganizacion);
      context.sessionAttribute("organismoControl", organismoControl);
      context.sessionAttribute("rolSeleccionado", organismoControl.getNombre() + " - Manejador");
    } else if (idRol == -2) {
      EntidadPrestadora entidadPrestadora = repoEntidades.buscarPorId(idOrganizacion);
      context.sessionAttribute("entidadPrestadora", entidadPrestadora);
      context.sessionAttribute("rolSeleccionado", entidadPrestadora.getNombre() + " - Manejador");
    } else if(idRol == repoRol.rolAdminComunidad().getId() || idRol == repoRol.rolDefaultComunidad().getId()){
      int idComunidad = idOrganizacion;


      Comunidad comunidad = repoComunidad.obtenerPorId(idComunidad);
      context.sessionAttribute("comunidad", comunidad);
      context.sessionAttribute("rolSeleccionado", comunidad.getNombre() + " - "+ repoRol.rolDefaultComunidad().getNombre());
      if(idRol == repoRol.rolAdminComunidad().getId()){
        context.sessionAttribute("adminComunidad", true);
        context.sessionAttribute("rolSeleccionado", comunidad.getNombre() + " - "+ repoRol.rolAdminComunidad().getNombre());
      }
    }else{
      context.sessionAttribute("rolSeleccionado", repoRol.rolDefault().getNombre());
      if(idRol == repoRol.rolAdminPlataforma().getId()){
        context.sessionAttribute("adminPlataforma", true);
        context.sessionAttribute("rolSeleccionado", repoRol.rolAdminPlataforma().getNombre());
      }
    }

    context.redirect("/roles");
  }
}
