package ar.edu.utn.frba.dds.geoRef;

import java.io.IOException;

public interface AdapterMetadatosGeograficos {
  MetadatoGeografico obtenerMetadatoGeografico(float latitud, float longitud) throws IOException;
  Provincia obtenerProvincia(float latitud, float longitud) throws IOException;
  Localidad obtenerLocalidad(float latitud, float longitud) throws IOException;
  Municipio obtenerMunicipio(float latitud, float longitud) throws IOException;
}
