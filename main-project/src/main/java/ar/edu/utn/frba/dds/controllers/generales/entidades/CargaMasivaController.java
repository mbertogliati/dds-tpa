package ar.edu.utn.frba.dds.controllers.generales.entidades;

import ar.edu.utn.frba.dds.controllers.utils.GeneradorModel;
import ar.edu.utn.frba.dds.controllers.utils.ICrudViewsHandler;
import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.entidades.OrganismoControl;
import ar.edu.utn.frba.dds.modelos.importadorEntidades.ImportadorEntidadAdapter;
import ar.edu.utn.frba.dds.modelos.importadorEntidades.ImportadorEntidadCSV;
import ar.edu.utn.frba.dds.modelos.utilidades.Ubicacion;
import ar.edu.utn.frba.dds.repositorios.entidades.EntidadPrestadoraRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.OrganismoControlRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

public class CargaMasivaController implements ICrudViewsHandler {

  private ImportadorEntidadAdapter importador = new ImportadorEntidadCSV();
  private OrganismoControlRepositorio repoOrganismoControl;
  private EntidadPrestadoraRepositorio repoEntidadPrestadora;

  public CargaMasivaController(EntityManager entityManager){
    repoOrganismoControl = new OrganismoControlRepositorio(entityManager);
    repoEntidadPrestadora = new EntidadPrestadoraRepositorio(entityManager);

  }

  @Override
  public void index(Context context) {

  }

  @Override
  public void show(Context context) {

  }

  @Override
  public void create(@NotNull Context context){
    Map<String, Object> model = GeneradorModel.model(context);

    context.render("cargaMasivaEntidades.hbs", model);
  }

  @Override
  public void save(@NotNull Context context){
    List<UploadedFile> uploadedFiles = context.uploadedFiles("file");

    if (!uploadedFiles.isEmpty()) {
      UploadedFile file = uploadedFiles.get(0);
      String fileName = file.filename();

      System.out.println("Received file: " + fileName);
      File archivo = new File("main-project/src/main/resources/public/upload/" + file.filename());
      try{FileUtils.copyInputStreamToFile(file.content(),archivo);}
      catch(Exception e){

      }

      List<OrganismoControl> organismosControl = importador.importar(archivo.getPath());

      Usuario usuario = context.sessionAttribute("usuario");
      Persona persona = usuario.getPersonaAsociada();
      Ubicacion ubicacion = persona.getUltimaUbicacion();

      //GUARDO LA UBICACION DE LA PERSONA COMO LA UBICACION DE LAS ENTIDADES Y ESTABLECIMIENTOS

      organismosControl.forEach(o -> {
        o.setPersonaAInformar(persona);
        o.getEntidadesPrestadoras().forEach(ep -> {
          ep.setUbicacion(ubicacion);
          ep.setPersonaAInformar(persona);
          ep.getEntidades().forEach(e -> {
            e.setUbicacion(ubicacion);
            e.getEstablecimientos().forEach(es -> {
              es.setUbicacion(ubicacion);
            });
          });
        });
      });

      organismosControl.forEach(o -> repoOrganismoControl.guardar(o));

      archivo.delete();
    }

    context.sessionAttribute("msg", new MensajeVista(MensajeVista.TipoMensaje.SUCCESS, "Carga masiva realizada correctamente."));
    context.redirect("/cargaMasiva?success");
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
