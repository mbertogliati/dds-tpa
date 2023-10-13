package ar.edu.utn.frba.dds.modelos.fusion_organizacion;

import java.util.List;

public interface AdapterFusion {
  List<PropuestaFusion> obtenerPropuestas(List<Organizacion> organizaciones);
  FusionCompletada aceptarFusion(SolicitudFusion solicitud);
  String rechazarFusion(String parametro);
}
