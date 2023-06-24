package ar.edu.utn.frba.dds.meta_datos_geo;

import lombok.Getter;

public class MetadatoGeografico {
  @Getter
  Provincia provincia;

  @Getter
  Municipio municipio;

  @Getter
  Localidad localidad;

  public MetadatoGeografico(Provincia provincia, Municipio municipio, Localidad localidad) {
    this.provincia = provincia;
    this.municipio = municipio;
    this.localidad = localidad;
  }
}
