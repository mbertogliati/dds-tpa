package ar.edu.utn.frba.dds.domain.utilidades;

import ar.edu.utn.frba.dds.meta_datos_geo.*;

import java.io.IOException;

import lombok.Getter;
import lombok.Setter;


public class Ubicacion {
  @Getter @Setter
  private Coordenada coordenada;
  @Getter @Setter
  MetadatoGeografico metadato;
  @Setter
  private AdapterProveedorMetadatosGeograficos adapterProveedorMetadatosGeograficos;

  public Ubicacion(float latitud, float longitud) {
    this.coordenada = new Coordenada(latitud, longitud);
    this.adapterProveedorMetadatosGeograficos = null;
    this.metadato = null;
  }

  //Constructores de provincia, municipio o localidad para seleccionar de un menú (sin lat/long)
  public Ubicacion(Provincia provincia, Departamento departamento, Localidad localidad) {
    this.metadato = new MetadatoGeografico(provincia, departamento, localidad);
  }
  public Ubicacion(Provincia provincia) {
    this.metadato = new MetadatoGeografico(provincia, null, null);
  }

  public void cargarMetadatosGeograficos() throws IOException {
    if (this.adapterProveedorMetadatosGeograficos != null) {
      this.metadato = this.adapterProveedorMetadatosGeograficos.obtenerMetadatoGeografico(this.coordenada);
    }
  }
}
