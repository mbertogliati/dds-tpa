package ar.edu.utn.frba.dds.modelos.importadorEntidades;

import ar.edu.utn.frba.dds.modelos.entidades.OrganismoControl;
import java.util.List;

public interface ImportadorEntidadAdapter {
  public List<OrganismoControl> importar(String path);
}
