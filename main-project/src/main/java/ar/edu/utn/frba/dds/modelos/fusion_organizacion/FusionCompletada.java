package ar.edu.utn.frba.dds.modelos.fusion_organizacion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FusionCompletada {
  private Long idOrganizacion1;
  private Long idOrganizacion2;

  private Organizacion organizacionFusionada;
}
