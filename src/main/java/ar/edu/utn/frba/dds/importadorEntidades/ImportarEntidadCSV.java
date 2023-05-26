package ar.edu.utn.frba.dds.importadorEntidades;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import java.util.ArrayList;
import java.util.List;

public class ImportarEntidadCSV implements ImportarEntidadAdapter{
  @Override
  public List<Entidad> crearEntidades(String path) {
    //TODO: IMPLEMENTAR IMPORTAR ENTIDADES POR CSV
    return new ArrayList<Entidad>();
  }
}
