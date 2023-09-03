package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;


import java.time.temporal.ChronoUnit;
import java.util.*;

public class PromedioEntreAperturaYCierre implements EstrategiaCalculoPuntos {
    @Override
    public List<PuntosPorEntidad> calcularPuntos(List<IncidentePorComunidad> incidentes) {

        Map<Entidad,List<Long>> diccionarioTiemposDeCierre = new HashMap<>();
        incidentes.stream().forEach(incidentePorComunidad -> {
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

