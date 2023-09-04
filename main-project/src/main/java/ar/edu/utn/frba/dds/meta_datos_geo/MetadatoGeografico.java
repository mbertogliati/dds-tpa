package ar.edu.utn.frba.dds.meta_datos_geo;

import lombok.Getter;

public class MetadatoGeografico {
  @Getter
  Provincia provincia;

  @Getter
  Departamento departamento;

  @Getter
  Localidad localidad;

  public MetadatoGeografico(Provincia provincia, Departamento departamento, Localidad localidad) {
    this.provincia = provincia;
    this.departamento = departamento;
    this.localidad = localidad;
  }

  public boolean esIgual(MetadatoGeografico metadato) {
    if (metadato == null) {
      return false;
    }
    return this.provincia.getId() == metadato.getProvincia().getId()
        && this.departamento.getId() == metadato.getDepartamento().getId()
        && this.localidad.getId() == metadato.getLocalidad().getId();
  }
}
