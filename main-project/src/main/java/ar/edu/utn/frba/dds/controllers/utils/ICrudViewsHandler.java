package ar.edu.utn.frba.dds.controllers.utils;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.server.EntityManagerContext;

public interface ICrudViewsHandler {
  void index(Context context);
  void show(Context context);
  void create(Context context);
  void save(Context context);
  void edit(Context context);
  void update(Context context);
  void delete(Context context);
}

