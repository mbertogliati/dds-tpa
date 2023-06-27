package ar.edu.utn.frba.dds.meta_datos_geo;

import lombok.Getter;

public class MetadatoGeografico {
  @Getter
  Provincia provincia;

  @Getter
  Municipio municipio;

  @Getter
  Departamento departamento;

  @Getter
  Localidad localidad;

  public MetadatoGeografico(Provincia provincia, Municipio municipio, Departamento departamento, Localidad localidad) {
    this.provincia = provincia;
    this.municipio = municipio;
    this.departamento = departamento;
    this.localidad = localidad;
  }
}
