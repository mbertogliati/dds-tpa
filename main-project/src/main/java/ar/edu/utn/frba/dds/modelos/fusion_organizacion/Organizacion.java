package ar.edu.utn.frba.dds.modelos.fusion_organizacion;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Organizacion {
  private Long idOrganizacion;
  private Set<Long> establecimientos = new HashSet<>();
  private Set<Long> servicios = new HashSet<>();
  private Double gradoConfianza;
  private Set<Long> miembros = new HashSet<>();
  private Set<UltimoIntentoFusion> ultimosIntentosDeFusion = new HashSet<>();
}
