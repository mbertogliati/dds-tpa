package ar.edu.utn.frba.dds.domain.rankings;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;

import java.time.LocalDateTime;
import java.util.List;

public class GradoDeImpacto implements GeneradorRanking {

    @Getter @Setter
    private MedidorImpacto medidorImpacto;

    @Override
    public List<Entidad> ordenar(List<Entidad> entidades) {
        //TODO: IMPLEMENTAR ORDENAMIENTO
        return entidades;
    }
}
