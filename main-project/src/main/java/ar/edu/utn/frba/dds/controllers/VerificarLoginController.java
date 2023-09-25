package ar.edu.utn.frba.dds.controllers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class VerificarLoginController implements Handler {
  public VerificarLoginController(EntityManager entityManager){

  }

  @Override
  public void handle(@NotNull Context context) throws Exception {
    //TODO: IMPLEMENTAR LOGIN CON VERIFICADOR DE CONTRASEÑAS
    //TODO: IMPLEMENTAR SESIONES Y QUE SI NO ESTAS LOGUEADO, NO TE DEJE INGRESAR

  }
}
