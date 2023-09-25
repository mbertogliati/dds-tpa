package ar.edu.utn.frba.dds.modelos.meta_datos_geo;

import ar.edu.utn.frba.dds.modelos.utilidades.Coordenada;
import java.io.IOException;
import java.util.List;

public interface AdapterProveedorMetadatosGeograficos {
  MetadatoGeografico obtenerMetadatoGeografico(Coordenada coordenada) throws IOException;

  Provincia obtenerProvincia(Coordenada coordenada) throws IOException;
  Departamento obtenerDepartamento(Coordenada coordenada) throws  IOException;
  Localidad obtenerLocalidad(Coordenada coordenada) throws IOException;

  List<Provincia> provincias() throws IOException;
  List<Departamento> departamentosDeProvincia(Provincia provincia) throws IOException;
  List<Departamento> departamentosDeProvinciaParaTabla(Provincia provincia) throws IOException;
  List<Localidad> localidadesDeDepartamento(Departamento departamento, Provincia provincia) throws IOException;
  List<Localidad> localidadesDeDepartamentoParaTabla(Departamento departamento, Provincia provincia) throws IOException;

}
