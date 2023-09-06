package ar.edu.utn.frba.dds.importadorEntidades;

import ar.edu.utn.frba.dds.domain.entidades.OrganismoControl;
import java.util.List;

public interface ImportadorEntidadAdapter {
  public List<OrganismoControl> importar(String path);
}
