package ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_fusion_g19;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FusionCompletada {
  private Long idOrganizacion1;
  private Long idOrganizacion2;

  private Organizacion organizacionFusionada;
}
