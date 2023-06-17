package ar.edu.utn.frba.dds.domain.utilidades;

import ar.edu.utn.frba.dds.geoRef.ServicioGeoRef;
import java.io.IOException;
import java.util.List;
import lombok.Getter;

public class Ubicacion {
  @Getter
  private float latitud;
  @Getter
  private float longitud;
  @Getter
  private List<Localizacion> localizaciones;
  private ServicioGeoRef geoRef;

  public Ubicacion(float latitud, float longitud) throws IOException {
    this.latitud = latitud;
    this.longitud = longitud;
    this.geoRef = ServicioGeoRef.instancia();
    this.localizaciones = geoRef.ubicacion(latitud, longitud);
  }

  public void setLatitudLongitud(float latitud, float longitud) throws IOException {
    this.latitud = latitud;
    this.longitud = longitud;
    this.localizaciones = geoRef.ubicacion(latitud, longitud);
  }

  private Localizacion getLocalizacion(TipoLocalizacion tipo) {
    return this.localizaciones.stream().filter(f -> f.getTipo().equals(tipo)).findFirst().get();
  }

  public Localizacion getProvincia() {
    return getLocalizacion(TipoLocalizacion.PROVINCIA);
  }

  public Localizacion getMunicipio() {
    return getLocalizacion(TipoLocalizacion.MUNICIPIO);
  }

  public Localizacion getDepartamento() {
    return getLocalizacion(TipoLocalizacion.DEPARTAMENTO);
  }
}
