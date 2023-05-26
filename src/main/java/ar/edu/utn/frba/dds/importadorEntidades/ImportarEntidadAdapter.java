package ar.edu.utn.frba.dds.importadorEntidades;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import java.util.List;

public interface ImportarEntidadAdapter {
  public abstract List<Entidad> crearEntidades(String path);
}
