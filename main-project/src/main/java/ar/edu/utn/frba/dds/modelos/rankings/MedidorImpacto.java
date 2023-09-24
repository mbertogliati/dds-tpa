package ar.edu.utn.frba.dds.modelos.rankings;

import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.incidentes.IncidentePorComunidad;
import java.util.List;

public interface MedidorImpacto {
    public Double medirImpactoDe(Entidad entidad, List<IncidentePorComunidad> incidentes);
}
