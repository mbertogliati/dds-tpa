package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.domain.incidentes.IncidentePorComunidad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasIncidentes implements EstrategiaCalculoPuntos {
    @Override
    public List<PuntosPorEntidad> calcularPuntos(List<IncidentePorComunidad> incidentesPorComunidad) {
        Map<Entidad,List<Incidente>> diccionarioIncidentesPorEntidad = incidentes24HorasPorEntidad(incidentesPorComunidad);
        List<PuntosPorEntidad> puntos = new ArrayList<>();

        diccionarioIncidentesPorEntidad.forEach((entidad, incidentesDeEntidad) -> {
            puntos.add(new PuntosPorEntidad( entidad, (double) incidentesDeEntidad.size()));
        });

        return puntos;
    }

    private Map<Entidad,List<Incidente>> incidentes24HorasPorEntidad(List<IncidentePorComunidad> incidentesPorComunidad){
        List<Incidente> incidentes = incidentesPorComunidad.stream().map(IncidentePorComunidad::getIncidente).toList();
        Map<Entidad,List<Incidente>> diccionarioIncidentes = new HashMap<Entidad, List<Incidente>>();

        incidentes.forEach(incidenteActual -> {
           if(diccionarioIncidentes.containsKey(incidenteActual.obtenerEntidad())){
                List<Incidente> listaIncidentes = diccionarioIncidentes.get(incidenteActual.obtenerEntidad());
               if (! listaIncidentes.stream().anyMatch(i ->
                       i.getServiciosAfectados().equals(incidenteActual.getServiciosAfectados()) &&
                       i.getFechaHoraApertura()
                               .plusDays(1)
                               .isAfter(incidenteActual.getFechaHoraApertura()))
                ) listaIncidentes.add(incidenteActual);

           }
           else{
                diccionarioIncidentes.put(incidenteActual.obtenerEntidad(),new ArrayList<>(List.of(incidenteActual)));
              }

        });

        return diccionarioIncidentes;


    }
}
