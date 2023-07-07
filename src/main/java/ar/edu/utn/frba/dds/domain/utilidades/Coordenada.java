package ar.edu.utn.frba.dds.domain.utilidades;

import lombok.Getter;
import lombok.Setter;

public class Coordenada {
  @Getter @Setter
  private float latitud;
  @Getter @Setter
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
