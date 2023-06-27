package ar.edu.utn.frba.dds.meta_datos_geo;

import java.io.IOException;

public interface AdapterMetadatosGeograficos {
  MetadatoGeografico obtenerMetadatoGeografico(float latitud, float longitud) throws IOException;
  Provincia obtenerProvincia(float latitud, float longitud) throws IOException;
  Localidad obtenerLocalidad(float latitud, float longitud) throws IOException;
  Municipio obtenerMunicipio(float latitud, float longitud) throws IOException;

  //TODO: FIJARSE QUE SERVICIOGEOREF UTILIZA PROVINCIA, MUNICIPIO Y LOCALIDAD. NO SE SI ESTÁ BIEN MODULARIZADO
  //TODO: OBTENER LISTADO DE PROVINCIAS Y QUE CADA PROVINCIA TENGA SUS MUNICIPIOS Y QUE CADA MUNICIPIO TENGA SUS LOCALIDADES
}
