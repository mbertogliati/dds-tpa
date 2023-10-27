package ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_fusion_g19;

import java.util.List;

public interface AdapterFusion {
  List<PropuestaFusion> obtenerPropuestas(List<Organizacion> organizaciones);
  FusionCompletada aceptarFusion(SolicitudFusion solicitud);
  String rechazarFusion(String parametro);
}
