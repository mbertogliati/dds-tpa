package integration_tests;

import ar.edu.utn.frba.dds.modelos.fusion_organizacion.AdapterFusion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.FusionCompletada;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.Organizacion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.PropuestaFusion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.SolicitudFusion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_externo.ServicioFusion;
import java.util.ArrayList;
import java.util.List;

public class IntegrationTestFusionOrganizacion {

  public static void main(String args[]) {
    AdapterFusion adapterFusion = new ServicioFusion();
    testObtenerPropuestas(adapterFusion);
    testAceptarPropuestas(adapterFusion);
  }

  private static void testObtenerPropuestas(AdapterFusion adapterFusion) {
    List<Organizacion> organizaciones = new ArrayList<>();
    Organizacion organizacionA = obtenerOrganizacionTest(1l);
    Organizacion organizacionB = obtenerOrganizacionTest(2l);
    organizaciones.add(organizacionA);
    organizaciones.add(organizacionB);

    System.out.println("Obteniendo propuestas de fusión...");

    List<PropuestaFusion> propuestas = adapterFusion.obtenerPropuestas(organizaciones);

    if(propuestas == null) {
      System.out.println("No se encontraron propuestas!");
      return;
    }

    System.out.println("Se encontraron estas propuestas de fusión:");

    propuestas.forEach(p -> {
      System.out.println("\tPropuesta:  IdOrganizacion1:" + p.getIdOrganizacion1() + " - IdOrganizacion2:" + p.getIdOrganizacion2());
    });
  }

  private static void testAceptarPropuestas(AdapterFusion adapterFusion) {
    SolicitudFusion solicitudFusion = new SolicitudFusion();

    solicitudFusion.setOrganizacion1(obtenerOrganizacionTest(1l));
    solicitudFusion.setOrganizacion2(obtenerOrganizacionTest(2l));

    System.out.println("Aceptando fusión...");

    FusionCompletada fusionCompletada = adapterFusion.aceptarFusion(solicitudFusion);

    if(fusionCompletada == null) {
      System.out.println("No se pudo realizar la fusión!");
      return;
    }

    System.out.println("Fusion completada entre organizaciones: idOrganizacion1: " + fusionCompletada.getIdOrganizacion1() + ", idOrganizacion2: " + fusionCompletada.getIdOrganizacion2());

    Organizacion nuevaOrganizacion = fusionCompletada.getOrganizacionFusionada();

    System.out.println("Nueva organizacion generada");
    mostrarOrganizacion(nuevaOrganizacion);
  }

  private static Organizacion obtenerOrganizacionTest(Long idOrganizacion) {
    Organizacion organizacion = new Organizacion();
    organizacion.setIdOrganizacion(idOrganizacion);
    organizacion.getServicios().add(1l);
    organizacion.getServicios().add(2l);
    organizacion.getMiembros().add(1l);
    organizacion.getMiembros().add(2l);
    organizacion.getEstablecimientos().add(1l);
    organizacion.getEstablecimientos().add(2l);
    organizacion.setGradoConfianza(1.0d);
    return organizacion;
  }

  private static void mostrarOrganizacion(Organizacion organizacion) {
    System.out.println("\tid: "  + organizacion.getIdOrganizacion());
    System.out.println("\tgradoConfianza: "  + organizacion.getGradoConfianza());
    System.out.println("\tmiembros:" + organizacion.getMiembros());
    System.out.println("\tservicios:" + organizacion.getServicios());
    System.out.println("\testablecimientos:" + organizacion.getEstablecimientos());
  }
}
