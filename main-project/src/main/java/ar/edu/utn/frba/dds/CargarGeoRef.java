package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.modelos.meta_datos_geo.AdapterProveedorMetadatosGeograficos;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Provincia;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.geo_ref.ServicioGeoRef;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.ProvinciaRepositorio;
import java.io.IOException;
import java.util.List;

public class CargarGeoRef {

  //TODO: CARGAR TODO DE NUEVO

  public static void main(String[] args) throws IOException {
    AdapterProveedorMetadatosGeograficos servicioGeoRef = new ServicioGeoRef();

    List<Provincia> provincias = servicioGeoRef.provincias();

    provincias.forEach(p -> {
      try {
        p.setDepartamentos(servicioGeoRef.departamentosDeProvincia(p));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    provincias.forEach(p -> p.getDepartamentos().forEach(d -> {
      try {
        d.setLocalidades(servicioGeoRef.localidadesDeDepartamento(d, p));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }));

    ProvinciaRepositorio repo = new ProvinciaRepositorio(new CreadorEntityManager().entityManagerCreado());

    provincias.forEach(p -> repo.guardar(p));

  }
}
