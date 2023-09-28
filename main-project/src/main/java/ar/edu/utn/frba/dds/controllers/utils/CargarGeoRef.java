package ar.edu.utn.frba.dds.controllers.utils;

import ar.edu.utn.frba.dds.modelos.meta_datos_geo.AdapterProveedorMetadatosGeograficos;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Departamento;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.geo_ref.ServicioGeoRef;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.DepartamentoRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.LocalidadRepositorio;
import java.io.IOException;
import java.util.List;

public class CargarGeoRef {

  public static void main(String[] args) throws IOException {
    AdapterProveedorMetadatosGeograficos servicioGeoRef = new ServicioGeoRef();

    /*
    ProvinciaRepositorio repo = new ProvinciaRepositorio(new CreadorEntityManager().entityManagerCreado());
    List<Provincia> provincias = repo.buscarTodas();

    provincias.forEach(p -> {
      try {
        p.setDepartamentos(servicioGeoRef.departamentosDeProvinciaParaTabla(p));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
*/
    DepartamentoRepositorio repoDepto = new DepartamentoRepositorio(new CreadorEntityManager().entityManagerCreado());

    List<Departamento> departamentos = repoDepto.buscarTodos();

    departamentos.forEach(d -> {
      try {
        d.setLocalidades(servicioGeoRef.localidadesDeDepartamentoParaTabla(d, d.getProvincia()));
        System.out.println("Provincia: " + d.getProvincia().getNombre() + " - Depto: " + d.getNombre());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    LocalidadRepositorio repoLocalidad = new LocalidadRepositorio(new CreadorEntityManager().entityManagerCreado());

    departamentos.forEach(d -> d.getLocalidades().forEach(l -> repoLocalidad.guardarLocalidad(l)));

/*
    provincias.forEach(p -> p.getDepartamentos().forEach(d -> {
      try {
        d.setLocalidades(servicioGeoRef.localidadesDeDepartamentoParaTabla(d, p));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }));*/


  }
}
