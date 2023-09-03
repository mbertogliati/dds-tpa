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

  public boolean esIgual(MetadatoGeografico metadato) {
    if (metadato == null) {
      return false;
    }
    return this.provincia.esIgual(metadato.getProvincia())
        && this.municipio.esIgual(metadato.getMunicipio())
        && this.departamento.esIgual(metadato.getDepartamento())
        && this.localidad.esIgual(metadato.getLocalidad());
  }
}
