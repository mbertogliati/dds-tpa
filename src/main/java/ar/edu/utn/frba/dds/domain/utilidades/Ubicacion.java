package ar.edu.utn.frba.dds.domain.utilidades;

import ar.edu.utn.frba.dds.geoRef.*;

import java.io.IOException;

import lombok.Getter;

public class Ubicacion {
  @Getter
  private float latitud;
  @Getter
  private float longitud;
  @Getter
  private Provincia provincia;
  @Getter
  private Municipio municipio;
  @Getter
  private Localidad localidad;
  private ServicioGeoRef geoRef;

  public Ubicacion(float latitud, float longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
    this.geoRef = ServicioGeoRef.instancia();
  }

  public void setLocalizaciones() throws IOException {
    this.provincia = this.geoRef.provincia(this.latitud, this.longitud);
    this.municipio = this.geoRef.municipio(this.latitud, this.longitud);
    this.localidad = this.geoRef.localidad(this.latitud, this.longitud);
  }

  public void setLatitudLongitud(float latitud, float longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }
}
