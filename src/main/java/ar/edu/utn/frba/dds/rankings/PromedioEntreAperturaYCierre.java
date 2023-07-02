package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;


import java.time.temporal.ChronoUnit;
import java.util.*;

public class PromedioEntreAperturaYCierre implements EstrategiaCalculoPuntos {
    @Override
    public Map<Entidad,Double> calcularPuntos(List<IncidentePorComunidad> incidentes) {

        Map<Entidad,List<Long>> diccionarioTiemposDeCierre = new HashMap<>();
        incidentes.stream().forEach(incidentePorComunidad -> {
            agregarTiempo(diccionarioTiemposDeCierre,incidentePorComunidad.getIncidente().obtenerEntidad(), tiempoHastaCierreEnMinutos(incidentePorComunidad));
        });

        Map<Entidad,Double> diccionarioPromedios = new HashMap<>();
        diccionarioTiemposDeCierre.forEach((entidad, tiempos) -> {
            diccionarioPromedios.put(entidad, tiempos.stream().mapToLong(tiempo -> tiempo).average().getAsDouble());
        });

        return diccionarioPromedios;
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

