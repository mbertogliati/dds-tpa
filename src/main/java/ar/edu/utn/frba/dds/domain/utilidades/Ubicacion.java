package ar.edu.utn.frba.dds.domain.utilidades;

import ar.edu.utn.frba.dds.geoRef.ServicioGeoRef;
import java.io.IOException;
import java.util.Optional;
import lombok.Getter;

public class Ubicacion {
  @Getter
  private float latitud;
  @Getter
  private float longitud;
  @Getter
  private Optional<Localizacion> provincia;
  @Getter
  private Optional<Localizacion> departamento;
  @Getter
  private Optional<Localizacion> municipio;
  private ServicioGeoRef geoRef;

  public Ubicacion(float latitud, float longitud) throws IOException {
    this.latitud = latitud;
    this.longitud = longitud;
    this.geoRef = ServicioGeoRef.instancia();
    this.provincia = geoRef.provincia(latitud, longitud);
    this.departamento = geoRef.departamento(latitud, longitud);
    this.municipio = geoRef.municipio(latitud, longitud);
  }
}
