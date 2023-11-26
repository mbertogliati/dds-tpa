package ar.edu.utn.frba.dds.controllers.utils;

import ar.edu.utn.frba.dds.modelos.comunidades.Permiso;
import ar.edu.utn.frba.dds.modelos.comunidades.Rol;
import ar.edu.utn.frba.dds.repositorios.comunidades.PermisoRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.RolRepositorio;

import javax.persistence.EntityManager;
import java.util.Enumeration;
import java.util.List;

public class ConfiguradorAutorizacion {
    private EntityManager entityManager;
    private RolRepositorio rolRepositorio;
    private PermisoRepositorio permisoRepositorio;
    public ConfiguradorAutorizacion(EntityManager entityManager){
        this.entityManager = entityManager;
        this.rolRepositorio = new RolRepositorio(entityManager);
        this.permisoRepositorio = new PermisoRepositorio(entityManager);
    }
    public void configurarRoles(){
        List<Rol> roles = this.rolRepositorio.buscarTodos();
        for(TipoRol tipoRol : TipoRol.values()){
            Rol rolAsociado = roles.stream().filter(rol -> rol.getId() == tipoRol.ordinal()).findFirst().orElse(null);
            if(rolAsociado == null){
                Rol rol = new Rol();
                rol.setNombre(tipoRol.toString());
                rol.setId(tipoRol.ordinal());
                rolRepositorio.guardar(rol);
            }
            else{
                rolAsociado.setNombre(tipoRol.toString());
                rolRepositorio.actualizar(rolAsociado);
            }
            configurarPermisos();
            //Se le asignan al administrador todos los permisos que existen
            Rol admin = this.rolRepositorio.buscarPorId(TipoRol.ADMINISTRADOR.ordinal());
            this.permisoRepositorio.buscarTodos().forEach(permiso -> {
                        if(admin.getPermisos().stream().noneMatch(permiso1 -> permiso1.getId() == permiso.getId())){
                            admin.getPermisos().add(permiso);
                        }
                    }
            );
            this.rolRepositorio.actualizar(admin);
        }
    }
    private void configurarPermisos(){
        List<Permiso> permisos = this.permisoRepositorio.buscarTodos();
        for(TipoPermiso tipoPermiso : TipoPermiso.values()){
            Permiso permisoAsociado = permisos.stream().filter(rol -> rol.getId() == tipoPermiso.ordinal()).findFirst().orElse(null);
            if(permisoAsociado == null){
                Permiso permiso = new Permiso();
                permiso.setDetalles(tipoPermiso.toString());
                permiso.setId(tipoPermiso.ordinal());
                permisoRepositorio.guardar(permiso);
            }
            else{
                permisoAsociado.setDetalles(tipoPermiso.toString());
                permisoRepositorio.actualizar(permisoAsociado);
            }
        }
    }
}
