package ar.edu.utn.frba.domain.entidades;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropuestaFusion {
    private static int contadorId = -1;

    private int id;
    private Long idOrganizacion1;
    private Long idOrganizacion2;

    private Organizacion organizacionFusionada;

    public PropuestaFusion(){
        this.id = this.nextId();
    }

    public int nextId() {
        contadorId++;
        return contadorId;
    }
}
