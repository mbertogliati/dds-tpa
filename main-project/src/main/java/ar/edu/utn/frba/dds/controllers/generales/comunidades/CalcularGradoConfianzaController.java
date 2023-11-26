package ar.edu.utn.frba.dds.controllers.generales.comunidades;

import ar.edu.utn.frba.dds.controllers.utils.CreadorEntityManager;
import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.*;
import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.servicio_externo.ServicioCalculoGradoConfianza;
import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.comunidades.Membresia;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.incidentes.IncidentePorComunidad;
import ar.edu.utn.frba.dds.repositorios.comunidades.ComunidadRepositorio;
import ar.edu.utn.frba.dds.repositorios.comunidades.UsuarioRepositorio;
import ar.edu.utn.frba.dds.repositorios.incidentes.IncidentePorComunidadRepositorio;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

public class CalcularGradoConfianzaController{
    UsuarioRepositorio repoUsuario;
    ComunidadRepositorio repoComunidad;
    IncidentePorComunidadRepositorio repoIncidentes;
    AdapterCalculoGradoConfianza calculadorGradoConfianza = new ServicioCalculoGradoConfianza();

    public CalcularGradoConfianzaController(EntityManager entityManager){
        repoComunidad = new ComunidadRepositorio(entityManager);
        repoUsuario = new UsuarioRepositorio(entityManager);
        repoIncidentes = new IncidentePorComunidadRepositorio(entityManager);
    }

    public void calcularGradosDeConfianza(){
        System.out.println("[INFO]: Calculando grados de confianza...");
        List<Comunidad> comunidades = repoComunidad.obtenerTodas();

        for (Comunidad comunidad : comunidades) {
            List<IncidentePorComunidad> incidentes = comunidad.getIncidentes().stream().filter(IncidentePorComunidad::isEstaCerrado).toList();

            //Obtengo todos los usuarios asociados a tal comunidad:
            List<UsuarioAEvaluar> usuariosAEvaluar = this.obtenerUsuariosAEvaluar(comunidad);

            //Armo el parametro de la confianza

            ParametroConfianza parametro = new ParametroConfianza();
            parametro.setIncidentes(incidentes);
            parametro.setUsuarios(usuariosAEvaluar);

            ResultadoConfianza resultado = calculadorGradoConfianza.calcular(parametro);
            if(resultado != null) {
                comunidad.setGradoConfianza(resultado.getNivel());

                if (comunidad.getGradoConfianza() < 2)
                    comunidad.setActivo(false);

                repoComunidad.actualizarComunidad(comunidad);

                this.actualizarUsuarios(resultado.getUsuarios());
            }
        }
        System.out.println("[INFO]: Grados de confianza calculados correctamente.");
    }
    private List<UsuarioAEvaluar> obtenerUsuariosAEvaluar(Comunidad comunidad){
        List<Persona> personas = comunidad.getMembresias().stream().map(Membresia::getPersona).toList();

        List<Usuario> usuarios = repoUsuario.buscarTodos().stream()
                .filter(usuario -> personas.stream().anyMatch( p -> p.getId() == usuario.getPersonaAsociada().getId())).toList();

        return usuarios.stream().map(u
                -> new UsuarioAEvaluar(u, u.getPersonaAsociada().getGradoConfianza())).toList();
    }
    private void actualizarUsuarios(List<UsuarioEvaluado> usuariosEvaluados){
        usuariosEvaluados.forEach(u -> {
            Usuario usuario = repoUsuario.buscarPorId(u.getId());
            usuario.getPersonaAsociada().setGradoConfianza(u.getPuntajeFinal());

            //Se inactivan como dice el enunciado
            if(Objects.equals(u.getNivelConfianza(), "No confiable"))
                usuario.setActivo(false);

            repoUsuario.actualizar(usuario);
        });
    }
}
