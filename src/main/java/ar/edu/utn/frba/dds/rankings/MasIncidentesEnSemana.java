package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MasIncidentesEnSemana implements GeneradorPuntos {
    @Override
    public Map<Entidad,Double> generarPuntos(List<IncidentePorComunidad> incidentesPorComunidad) {
        return CantidadDeIncidentesPorEntidad(incidentesPorComunidad).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (double) e.getValue()));
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

        Map<Entidad,Integer> diccionarioCantidadIncidentes = new HashMap<>();

        diccionarioIncidentes.forEach((entidad, incidentesDeEntidad) -> {
            diccionarioCantidadIncidentes.put(entidad, incidentesDeEntidad.size());
        });

        return diccionarioCantidadIncidentes;
    }
}
