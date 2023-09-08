package ar.edu.utn.frba.domain.entidades;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropuestaFusion {
    private Long idOrganizacion1;
    private Long idOrganizacion2;

    private Organizacion organizacionFusionada;
}
