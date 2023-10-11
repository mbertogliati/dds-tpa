package integration_tests;

import ar.edu.utn.frba.dds.modelos.fusion_organizacion.AdapterFusion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.Organizacion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.PropuestaFusion;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_externo.ServicioFusion;
import java.util.ArrayList;
import java.util.List;

public class IntegrationTestFusionOrganizacion {

  public static void main(String args[]) {
    AdapterFusion adapterFusion = new ServicioFusion();
    testObtenerPropuestas(adapterFusion);
  }

  private static void testObtenerPropuestas(AdapterFusion adapterFusion) {
    List<Organizacion> organizaciones = new ArrayList<>();
    Organizacion organizacionA = obtenerOrganizacionTest(1l);
    Organizacion organizacionB = obtenerOrganizacionTest(2l);
    organizaciones.add(organizacionA);
    organizaciones.add(organizacionB);
    List<PropuestaFusion> propuestas = adapterFusion.obtenerPropuestas(organizaciones);

    propuestas.forEach(p -> {
      System.out.println("IdOrganizacion1:" + p.getIdOrganizacion1() + " - IdOrganizacion2:" + p.getIdOrganizacion2());
    });
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
}
