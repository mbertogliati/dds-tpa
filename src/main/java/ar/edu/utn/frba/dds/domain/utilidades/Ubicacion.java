package ar.edu.utn.frba.dds.domain.utilidades;

import ar.edu.utn.frba.dds.meta_datos_geo.*;

import java.io.IOException;

import lombok.Getter;
import lombok.Setter;

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
  @Setter
  private AdapterMetadatosGeograficos adapterMetadatosGeograficos;

  public Ubicacion(float latitud, float longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
    this.adapterMetadatosGeograficos = null;
  }

  public void cargarMetadatosGeograficos() throws IOException {
    if (this.adapterMetadatosGeograficos != null) {
      var metadatos = this.adapterMetadatosGeograficos.obtenerMetadatoGeografico(this.latitud, this.longitud);
      this.provincia = metadatos.getProvincia();
      this.municipio = metadatos.getMunicipio();
      this.localidad = metadatos.getLocalidad();
    }
  }

  public void setLatitudLongitud(float latitud, float longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }
}
