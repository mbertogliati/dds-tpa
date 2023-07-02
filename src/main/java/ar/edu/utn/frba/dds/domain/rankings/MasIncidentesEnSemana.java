package ar.edu.utn.frba.dds.domain.rankings;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasIncidentesEnSemana implements GeneradorRanking {
    @Override
    public Ranking generarRanking(List<IncidentePorComunidad> incidentesPorComunidad) {
        Ranking ranking = new Ranking();


        CantidadDeIncidentesPorEntidad(incidentesPorComunidad).forEach((entidad, cantidad) -> {
            ranking.agregarEntidad(entidad, (double) cantidad);
        });

        ranking.setFechaHoraCreacion(LocalDateTime.now());
        ranking.setDescripcion("Ranking de entidades con mas incidentes en la semana");

        return ranking;
    }

    private List<Incidente> ordenarIncidentes(List<IncidentePorComunidad> incidentes){
        return new ArrayList<>(incidentes.stream()
                .map(IncidentePorComunidad::getIncidente)
                .sorted((i1,i2) -> i1.getFechaHoraApertura().compareTo(i2.getFechaHoraApertura()))
                .toList());
    }

    private Map<Entidad,Integer> CantidadDeIncidentesPorEntidad(List<IncidentePorComunidad> incidentesPorComunidad){
        List<Incidente> incidentes = ordenarIncidentes(incidentesPorComunidad);
        Map<Entidad,List<Incidente>> diccionarioIncidentes = new HashMap<Entidad, List<Incidente>>();

        incidentes.forEach(incidenteActual -> {
           if(diccionarioIncidentes.containsKey(incidenteActual.obtenerEntidad())){
                List<Incidente> listaIncidentes = diccionarioIncidentes.get(incidenteActual.obtenerEntidad());
               if (! listaIncidentes.stream().anyMatch(i ->
                       i.getServicioPrestados().equals(incidenteActual.getServicioPrestados()) &&
                       i.getFechaHoraApertura()
                               .plusDays(1)
                               .isAfter(incidenteActual.getFechaHoraApertura()))
                ) listaIncidentes.add(incidenteActual);

           }
           else{
                diccionarioIncidentes.put(incidenteActual.obtenerEntidad(),new ArrayList<>(List.of(incidenteActual)));
              }

        });

        Map<Entidad,Integer> diccionarioCantidadIncidentes = new HashMap<Entidad, Integer>();

        diccionarioIncidentes.forEach((entidad, incidentesDeEntidad) -> {
            diccionarioCantidadIncidentes.put(entidad, incidentesDeEntidad.size());
        });

        return diccionarioCantidadIncidentes;
    }
}
