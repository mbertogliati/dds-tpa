package ar.edu.utn.frba.dds.domain.utilidades;

import ar.edu.utn.frba.dds.geoRef.ServicioGeoRef;
import java.io.IOException;
import lombok.Getter;

public class Ubicacion {
  @Getter
  private float latitud;
  @Getter
  private float longitud;
  @Getter
  private Localizacion provincia;
  @Getter
  private Localizacion departamento;
  @Getter
  private Localizacion municipio;
  private ServicioGeoRef geoRef;

  public Ubicacion(float latitud, float longitud) throws IOException {
    this.latitud = latitud;
    this.longitud = longitud;
    this.geoRef = ServicioGeoRef.instancia();
    this.provincia = geoRef.provincia(latitud, longitud);
    this.departamento = geoRef.municipio(latitud, longitud);
    this.municipio = geoRef.municipio(latitud, longitud);
  }
}
