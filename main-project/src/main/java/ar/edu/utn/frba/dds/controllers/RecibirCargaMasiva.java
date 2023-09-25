package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.entidades.OrganismoControl;
import ar.edu.utn.frba.dds.modelos.importadorEntidades.ImportadorEntidadAdapter;
import ar.edu.utn.frba.dds.modelos.importadorEntidades.ImportadorEntidadCSV;
import ar.edu.utn.frba.dds.repositorios.entidades.EntidadPrestadoraRepositorio;
import ar.edu.utn.frba.dds.repositorios.entidades.OrganismoControlRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

public class RecibirCargaMasiva implements Handler {
  ImportadorEntidadAdapter importador = new ImportadorEntidadCSV();
  OrganismoControlRepositorio repoOrganismoControl;
  EntidadPrestadoraRepositorio repoEntidadPrestadora;
  public RecibirCargaMasiva(EntityManager entityManager){
    repoOrganismoControl = new OrganismoControlRepositorio(entityManager);
    repoEntidadPrestadora = new EntidadPrestadoraRepositorio(entityManager);
  }

  @Override
  public void handle(@NotNull Context context) throws Exception {
    if(VerificadorLogueo.noEstaLogueado(context.sessionAttribute("logueado"))){
      context.redirect("/login");
      return;
    }

    List<UploadedFile> uploadedFiles = context.uploadedFiles("file");

    if (!uploadedFiles.isEmpty()) {
      UploadedFile file = uploadedFiles.get(0);
      String fileName = file.filename();

      System.out.println("Received file: " + fileName);
      File archivo = new File("main-project/src/main/resources/public/upload/" + file.filename());
      FileUtils.copyInputStreamToFile(file.content(),archivo);

      List<OrganismoControl> organismosControl = importador.importar(archivo.getPath());

      //TODO: NO ANDA PORQUE ORM TIRA EXCEPTION:
      /*
      organismosControl.forEach(o ->
      {
        o.getEntidadesPrestadoras().forEach(e -> repoEntidadPrestadora.guardar(e));
        repoOrganismoControl.guardar(o);
      });*/


      archivo.delete();
    }
  }
}
