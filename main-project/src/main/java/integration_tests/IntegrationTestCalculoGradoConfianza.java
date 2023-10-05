package integration_tests;

import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.AdapterCalculoGradoConfianza;
import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.ParametroConfianza;
import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.ResultadoConfianza;
import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.UsuarioAEvaluar;
import ar.edu.utn.frba.dds.modelos.calculo_grado_confianza.servicio_externo.ServicioCalculoGradoConfianza;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import ar.edu.utn.frba.dds.modelos.incidentes.Incidente;
import ar.edu.utn.frba.dds.modelos.incidentes.IncidentePorComunidad;
import ar.edu.utn.frba.dds.modelos.servicios.Servicio;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import java.time.LocalDateTime;

public class IntegrationTestCalculoGradoConfianza {
  public static void main(String args[]) {
    AdapterCalculoGradoConfianza calculoGradoConfianza = new ServicioCalculoGradoConfianza();
    testCalcular(calculoGradoConfianza);
  }

  private static void testCalcular(AdapterCalculoGradoConfianza calculoGradoConfianza) {
    ParametroConfianza parametro = new ParametroConfianza();

    Usuario usuarioA = new Usuario();
    usuarioA.setId(1);

    Usuario usuarioB = new Usuario();
    usuarioB.setId(2);

    parametro.getUsuarios().add(new UsuarioAEvaluar(usuarioA, 0));
    parametro.getUsuarios().add(new UsuarioAEvaluar(usuarioB, 2));

    Persona autorApertura = new Persona();
    autorApertura.setId(1);
    autorApertura.setNombre("Test Autor Apertura");

    Persona autorCierre = new Persona();
    autorApertura.setId(2);
    autorApertura.setNombre("Test Autor Cierre");

    parametro.getIncidentes().add(generarIncidentePorComunidad(1, autorApertura, autorCierre));
    parametro.getIncidentes().add(generarIncidentePorComunidad(2, autorApertura, autorCierre));

    ResultadoConfianza resultado = calculoGradoConfianza.calcular(parametro);
    if(resultado != null) {
      System.out.println("nivel: " + resultado.nivel);
      resultado.usuarios.forEach(e -> {
        System.out.println("\t---");
        System.out.println("\tid: " + e.id);
        System.out.println("\tpuntaje_inicial: " + e.puntajeInicial);
        System.out.println("\tpuntaje_final: " + e.puntajeFinal);
        System.out.println("\tnivel_de_confianza: " + e.nivelConfianza);
        System.out.println("\trecomendacion: " + e.recomendacion);
      });
    } else
      System.out.println("No se pudo calcular.");
  }

  private static IncidentePorComunidad generarIncidentePorComunidad(int indice, Persona autorApertura, Persona autorCierre) {
    IncidentePorComunidad incidentePorComunidad = new IncidentePorComunidad();

    Servicio servicio = new Servicio();
    servicio.setId(indice);
    servicio.setNombre("Test Servicio");

    Establecimiento establecimiento = new Establecimiento();
    establecimiento.setId(indice);
    establecimiento.setNombre("Test Establecimiento");

    ServicioPrestado servicioPrestado = new ServicioPrestado();
    servicioPrestado.setId(indice);
    servicioPrestado.setServicio(servicio);
    servicioPrestado.setEstablecimiento(establecimiento);

    Incidente incidente = new Incidente();
    incidente.getServiciosAfectados().add(servicioPrestado);
    incidente.setFechaHoraApertura(LocalDateTime.now());
    incidente.setAutorApertura(autorApertura);

    incidentePorComunidad.setId(indice);
    incidentePorComunidad.setIncidente(incidente);
    incidentePorComunidad.setFechaHoraCierre(LocalDateTime.now());
    incidentePorComunidad.setAutorCierre(autorCierre);

    return incidentePorComunidad;
  }
}
