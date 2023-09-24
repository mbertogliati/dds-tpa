package ar.edu.utn.frba.dds.modelos.utilidades;

import ar.edu.utn.frba.dds.modelos.meta_datos_geo.AdapterProveedorMetadatosGeograficos;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Departamento;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Localidad;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.MetadatoGeografico;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Provincia;
import java.io.IOException;
import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Ubicacion {
  @Getter
  @Setter
  private static AdapterProveedorMetadatosGeograficos adapterProveedorMetadatosGeograficos;

  @Embedded
  @AttributeOverride(name="longitud", column=@Column(name="ubicacion_longitud"))
  @AttributeOverride(name="latitud", column=@Column(name="ubicacion_latitud"))
  private Coordenada coordenada;

  @Embedded
  @AssociationOverride(name = "provincia",
          joinColumns = @JoinColumn(name = "ubicacion_provincia_id"))
  @AssociationOverride(name = "departamento",
          joinColumns = @JoinColumn(name = "ubicacion_departamento_id"))
  @AssociationOverride(name = "localidad",
          joinColumns = @JoinColumn(name = "ubicacion_localidad_id"))
  MetadatoGeografico metadato;

  public Ubicacion(float latitud, float longitud) {
    this.coordenada = new Coordenada(latitud, longitud);
    adapterProveedorMetadatosGeograficos = null;
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
    if (adapterProveedorMetadatosGeograficos != null) {
      this.metadato = adapterProveedorMetadatosGeograficos.obtenerMetadatoGeografico(this.coordenada);
    }
  }
}
