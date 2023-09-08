package ar.edu.utn.frba.dds.domain.utilidades;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Coordenada {
  @Column(name = "latitud")
  private float latitud;

  @Column(name = "longitud")
  private float longitud;

  public Coordenada() {
    this.latitud = 0;
    this.longitud = 0;
  }

  public Coordenada(float latitud, float longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }

  public void setLatitudLongitud(float latitud, float longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }
}
