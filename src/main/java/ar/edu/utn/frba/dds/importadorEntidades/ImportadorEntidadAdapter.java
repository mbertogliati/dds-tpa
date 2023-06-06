package ar.edu.utn.frba.dds.importadorEntidades;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;

import java.util.List;

public interface ImportadorEntidadAdapter {
  public List<Entidad> crearEntidades(String path);
}
