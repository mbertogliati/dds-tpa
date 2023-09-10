package ar.edu.utn.frba.dds.domain.rankings;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;
import java.util.List;

public interface MedidorImpacto {
    public Double medirImpactoDe(Entidad entidad, List<IncidentePorComunidad> incidentes);
}
