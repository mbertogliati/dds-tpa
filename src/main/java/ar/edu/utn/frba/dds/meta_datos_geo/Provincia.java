package ar.edu.utn.frba.dds.meta_datos_geo;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class Provincia extends EntidadGeografica {
  @Getter @Setter
  private List<Municipio> municipios;
  @Getter @Setter
  private List<Departamento> departamentos;

  public Provincia(String id, String nombre) {
    super(id, nombre);
    this.municipios = new ArrayList<Municipio>();
    this.departamentos = new ArrayList<Departamento>();
  }
}
