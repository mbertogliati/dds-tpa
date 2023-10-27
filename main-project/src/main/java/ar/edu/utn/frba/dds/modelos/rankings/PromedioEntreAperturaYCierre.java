package ar.edu.utn.frba.dds.modelos.rankings;

import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.incidentes.IncidentePorComunidad;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromedioEntreAperturaYCierre implements EstrategiaCalculoPuntos {
    @Override
    public List<PuntosPorEntidad> calcularPuntos(List<IncidentePorComunidad> incidentes) {

        Map<Entidad,List<Long>> diccionarioTiemposDeCierre = new HashMap<>();
        incidentes.stream().filter(IncidentePorComunidad::isEstaCerrado).forEach(incidentePorComunidad -> {
            agregarTiempo(diccionarioTiemposDeCierre,incidentePorComunidad.getIncidente().obtenerEntidad(), tiempoHastaCierreEnMinutos(incidentePorComunidad));
        });

        List<PuntosPorEntidad> puntos = new ArrayList<>();
        diccionarioTiemposDeCierre.forEach((entidad, tiempos) -> {
            puntos.add(new PuntosPorEntidad( entidad, tiempos.stream().mapToLong(tiempo -> tiempo).average().getAsDouble()));
        });

        return puntos;
    }
    private Long tiempoHastaCierreEnMinutos(IncidentePorComunidad incidentePorComunidad){
        return ChronoUnit.MINUTES.between(
                incidentePorComunidad.getIncidente().getFechaHoraApertura(),
                incidentePorComunidad.getFechaHoraCierre());
    }

    private void agregarTiempo(Map<Entidad,List<Long>> diccionario, Entidad entidad, Long tiempo){
        if(diccionario.containsKey(entidad)){
            diccionario.get(entidad).add(tiempo);
        }else{
            diccionario.put(entidad, new ArrayList<Long>(List.of(tiempo)));
        }
    }
}

