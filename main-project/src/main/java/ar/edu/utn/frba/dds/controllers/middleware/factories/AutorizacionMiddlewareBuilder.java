package ar.edu.utn.frba.dds.controllers.middleware.factories;

import ar.edu.utn.frba.dds.controllers.middleware.AutorizacionMiddleware;
import ar.edu.utn.frba.dds.controllers.utils.TipoPermiso;
import ar.edu.utn.frba.dds.controllers.utils.TipoRol;
import ar.edu.utn.frba.dds.repositorios.comunidades.PermisoRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.RolRepositorio;

import javax.persistence.EntityManager;
import java.util.Arrays;

public class AutorizacionMiddlewareBuilder {
    private AutorizacionMiddleware autorizacionMiddleware;

    private RolRepositorio rolRepositorio;
    private PermisoRepositorio permisoRepositorio;

    public AutorizacionMiddlewareBuilder(EntityManager entityManager){
        this.rolRepositorio = new RolRepositorio(entityManager);
        this.permisoRepositorio = new PermisoRepositorio(entityManager);
        this.reset();
    }
    public AutorizacionMiddlewareBuilder conRolesDePlataforma(TipoRol ... roles){
        this.autorizacionMiddleware.getRolesPlataforma()
                .addAll( Arrays.stream(roles).map( tipoRol -> this.rolRepositorio.buscarPorId(tipoRol.ordinal())).toList());
        return this;
    }
    public AutorizacionMiddlewareBuilder conRolesDeComunidad(TipoRol ... roles){
        this.conComunidad();
        this.autorizacionMiddleware.getRolesComunidad()
                .addAll( Arrays.stream(roles).map( tipoRol -> this.rolRepositorio.buscarPorId(tipoRol.ordinal())).toList());
        return this;
    }
    public AutorizacionMiddlewareBuilder conComunidad(){
        this.autorizacionMiddleware.setChequearComunidad(true);
        return this;
    }
    public AutorizacionMiddlewareBuilder conPermisosPlataforma(TipoPermiso... permisos){
        this.autorizacionMiddleware.getPermisosPlataforma()
                .addAll( Arrays.stream(permisos).map( tipoPermiso -> this.permisoRepositorio.buscarPorId(tipoPermiso.ordinal())).toList());
        return this;
    }
    public AutorizacionMiddlewareBuilder conPermisosComunidad(TipoPermiso... permisos){
        this.conComunidad();
        this.autorizacionMiddleware.getPermisosComunidad()
                .addAll( Arrays.stream(permisos).map( tipoPermiso -> this.permisoRepositorio.buscarPorId(tipoPermiso.ordinal())).toList());
        return this;
    }
    public AutorizacionMiddleware build(){
        AutorizacionMiddleware autMiddleware = this.autorizacionMiddleware;
        reset();
        return autMiddleware;
    }
    public AutorizacionMiddleware reset(){
        this.autorizacionMiddleware = new AutorizacionMiddleware();
        return this.autorizacionMiddleware;
    }
}
