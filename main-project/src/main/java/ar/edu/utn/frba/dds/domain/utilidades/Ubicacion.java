package ar.edu.utn.frba.dds.domain.utilidades;

import ar.edu.utn.frba.dds.meta_datos_geo.*;

import java.io.IOException;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ubicaciones")
public class Ubicacion {
  @Id
  @GeneratedValue
  private  int id;

  @Embedded
  private Coordenada coordenada;

  @Embedded
  MetadatoGeografico metadato;

  //TODO: que hacemos con esto en la persistencia?? Habria que guardarlo?
  @Transient
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

  public Ubicacion() {}

  public void cargarMetadatosGeograficos() throws IOException {
    if (this.adapterProveedorMetadatosGeograficos != null) {
      this.metadato = this.adapterProveedorMetadatosGeograficos.obtenerMetadatoGeografico(this.coordenada);
    }
  }
}
