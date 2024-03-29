package ar.edu.utn.frba.dds.controllers.utils;

import ar.edu.utn.frba.dds.modelos.comunidades.Permiso;
import ar.edu.utn.frba.dds.modelos.comunidades.Rol;
import ar.edu.utn.frba.dds.repositorios.comunidades.PermisoRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.RolRepositorio;

import ar.edu.utn.frba.dds.server.EntityManagerContext;
import javax.persistence.EntityManager;
import java.util.Enumeration;
import java.util.List;
import javax.persistence.EntityManagerFactory;

public class ConfiguradorAutorizacion {
    private RolRepositorio rolRepositorio;
    private PermisoRepositorio permisoRepositorio;
    public ConfiguradorAutorizacion(EntityManagerFactory entityManagerFactory){
        EntityManager entityManagerParaEsteHilo = entityManagerFactory.createEntityManager();
        EntityManagerContext.setEntityManager(entityManagerParaEsteHilo);

        this.rolRepositorio = new RolRepositorio();
        this.permisoRepositorio = new PermisoRepositorio();
    }
    public void configurarRoles(){
        List<Rol> roles = this.rolRepositorio.buscarTodos();
        for(TipoRol tipoRol : TipoRol.values()){
            Rol rolAsociado = roles.stream().filter(rol -> rol.getId() == tipoRol.ordinal()).findFirst().orElse(null);
            if(rolAsociado == null){
                Rol rol = new Rol();
                rol.setNombre(tipoRol.nombreLindo());
                rol.setId(tipoRol.ordinal());
                rolRepositorio.guardar(rol);
            }
            else{
                rolAsociado.setNombre(tipoRol.nombreLindo());
                rolRepositorio.actualizar(rolAsociado);
            }
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
        Rol adminComunidad = this.rolRepositorio.buscarPorId(TipoRol.ADMINISTRADOR_COMUNIDAD.ordinal());
        if(adminComunidad.getPermisos().stream().noneMatch(permiso -> permiso.getId() == TipoPermiso.ADMINISTRAR_COMUNIDAD.ordinal())){
            adminComunidad.getPermisos().add(this.permisoRepositorio.buscarPorId(TipoPermiso.ADMINISTRAR_COMUNIDAD.ordinal()));
        }
        this.rolRepositorio.actualizar(adminComunidad);


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
