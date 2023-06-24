package ar.edu.utn.frba.dds.geoRef;

import lombok.Getter;
import lombok.Setter;

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
