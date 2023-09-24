package ar.edu.utn.frba.dds.modelos.meta_datos_geo;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class MetadatoGeografico {
  @ManyToOne
  @JoinColumn(name = "provincia_id", referencedColumnName = "id")
  Provincia provincia;

  @ManyToOne
  @JoinColumn(name = "departamento_id", referencedColumnName = "id")
  Departamento departamento;

  @ManyToOne
  @JoinColumn(name = "localidad_id", referencedColumnName = "id")
  Localidad localidad;

  public MetadatoGeografico(){}

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
