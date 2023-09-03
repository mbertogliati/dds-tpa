package ar.edu.utn.frba.dds.rankings;

//TODO: PERSISTIR LOS RANKINGS/INFORMES?
//TODO: REVISAR UBICACION/PROVINCIA/GEOREF Y TODO ESO

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

public class Ranking {
    @Getter
    private List<PuntosPorEntidad> puntosPorEntidad;
    @Getter @Setter
    private LocalDateTime fechaHoraCreacion;
    @Getter @Setter
    private String descripcion;

    public Ranking(){
        puntosPorEntidad = Collections.unmodifiableList(new ArrayList<PuntosPorEntidad>());
    }

    public boolean contieneEntidad(Entidad entidad){
        return puntosPorEntidad.stream().anyMatch(puntosPorEntidad -> puntosPorEntidad.getEntidad().equals(entidad));
    }

    public String toString(){
        String cabecera = "Ranking: " + descripcion + "\n" +
                "Fecha de creacion: " + fechaHoraCreacion.getDayOfWeek() + " " + fechaHoraCreacion.toLocalDate() + "\n" +
                "Hora de creacion: " + fechaHoraCreacion.getHour() + ":" + fechaHoraCreacion.getMinute() + "\n" +
                "Entidades: \n";
        StringBuilder entidadesString = new StringBuilder();
        PuntosPorEntidad puntosActuales;
        for (int i = 0; i < puntosPorEntidad.size(); i++) {
            puntosActuales = puntosPorEntidad.get(i);
            entidadesString
                    .append(i+1)
                    .append(". ")
                    .append(puntosActuales.getEntidad().getNombre())
                    .append(" - ")
                    .append(puntosActuales.getPuntos())
                    .append("\n");
        }
        return cabecera + entidadesString;
    }

    public Double puntosDe(Entidad entidad){
        return puntosPorEntidad.stream()
                .filter(puntosPorEntidad -> puntosPorEntidad.getEntidad().equals(entidad))
                .findFirst()
                .get()
                .getPuntos();
    }

    public void agregarEntidad(PuntosPorEntidad puntosPorEntidad){
        List<PuntosPorEntidad> nuevosPuntosPorEntidad = new ArrayList<PuntosPorEntidad>(this.puntosPorEntidad);
        nuevosPuntosPorEntidad.remove(puntosPorEntidad); //Chequea automaticamente si esta o no
        nuevosPuntosPorEntidad.add(puntosPorEntidad);

        this.puntosPorEntidad = Collections.unmodifiableList(nuevosPuntosPorEntidad);
    }

    public void ordernar(){
        List<PuntosPorEntidad> listaEntidades = new ArrayList<PuntosPorEntidad>(puntosPorEntidad);
        listaEntidades.sort((p1,p2) -> p2.getPuntos().compareTo(p1.getPuntos()));
        puntosPorEntidad = Collections.unmodifiableList(listaEntidades);
    }
    public Ranking filtrar(Function<PuntosPorEntidad,Boolean> filtro){
        Ranking rankingFiltrado = new Ranking();
        rankingFiltrado.setFechaHoraCreacion(fechaHoraCreacion);
        rankingFiltrado.setDescripcion(descripcion);
        rankingFiltrado.puntosPorEntidad = Collections.unmodifiableList(puntosPorEntidad.stream()
                .filter(filtro::apply)
                .toList());
        rankingFiltrado.ordernar();
        return rankingFiltrado;
    }
}


