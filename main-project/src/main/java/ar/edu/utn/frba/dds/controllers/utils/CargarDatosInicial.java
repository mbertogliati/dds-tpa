package ar.edu.utn.frba.dds.controllers.utils;

import ar.edu.utn.frba.dds.modelos.comunidades.Permiso;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Rol;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.AdapterProveedorMetadatosGeograficos;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Provincia;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.geo_ref.ServicioGeoRef;
import ar.edu.utn.frba.dds.repositorios.comunidades.PermisoRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.RolRepositorio;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.ProvinciaRepositorio;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;

public class CargarDatosInicial {
  public static void main(String[] args) throws IOException {
    EntityManager entityManager = new CreadorEntityManager().entityManagerCreado();

    cargarGeoRef(entityManager);
    System.out.println("GeoRef cargado correctamente.");
    cargarRoles(entityManager);
    System.out.println("Roles cargados correctamente.");
  }

  private static void cargarGeoRef(EntityManager entityManager) throws IOException {
    AdapterProveedorMetadatosGeograficos servicioGeoRef = new ServicioGeoRef();

    ProvinciaRepositorio repoProvincia = new ProvinciaRepositorio(entityManager);

    List<Provincia> provincias = servicioGeoRef.provincias();

    provincias.forEach(p -> {

      try {
        p.setDepartamentos(servicioGeoRef.departamentosDeProvinciaParaTabla(p));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      p.getDepartamentos().forEach(d ->
      {
        try {
          d.setLocalidades(servicioGeoRef.localidadesDeDepartamentoParaTabla(d, p));
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      });

    });

    provincias.forEach(repoProvincia::guardar);
  }

  private static void cargarRoles(EntityManager entityManager){
    RolRepositorio repoRol = new RolRepositorio(entityManager);
    PermisoRepositorio repoPermiso = new PermisoRepositorio(entityManager);

    Permiso administrarUsuarios = new Permiso("administrarUsuarios");
    Permiso administrarComunidad = new Permiso("administrarComunidad");
    repoPermiso.guardar(administrarUsuarios);
    repoPermiso.guardar(administrarComunidad);

    Rol rolAdminPlataforma = new Rol();
    rolAdminPlataforma.agregarPermiso(administrarUsuarios);

    Rol rolAdminComunidad = new Rol();
    rolAdminComunidad.agregarPermiso(administrarComunidad);

    Rol rolDefaultPlataforma = new Rol();
    Rol rolDefaultComunidad = new Rol();

    repoRol.guardar(rolAdminPlataforma);
    repoRol.guardar(rolAdminComunidad);
    repoRol.guardar(rolDefaultPlataforma);
    repoRol.guardar(rolDefaultComunidad);
  }
}
