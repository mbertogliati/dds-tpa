package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

public class Ranking {
    @Getter
    private List<Entidad> entidades;
    private Map<Entidad,Double> diccionarioPuntos;
    @Getter @Setter
    private LocalDateTime fechaHoraCreacion;
    @Getter @Setter
    private String descripcion;

    public Ranking(){
        entidades = Collections.unmodifiableList(new ArrayList<Entidad>());
        diccionarioPuntos = new HashMap<Entidad,Double>();
    }

    public boolean contieneEntidad(Entidad entidad){
        return entidades.contains(entidad);
    }

    public String toString(){
        String cabecera = "Ranking: " + descripcion + "\n" +
                "Fecha de creacion: " + fechaHoraCreacion.getDayOfWeek() + " " + fechaHoraCreacion.toLocalDate() + "\n" +
                "Hora de creacion: " + fechaHoraCreacion.getHour() + ":" + fechaHoraCreacion.getMinute() + "\n" +
                "Entidades: \n";
        StringBuilder entidadesString = new StringBuilder();
        for (int i = 0; i < entidades.size(); i++) {
            entidadesString
                    .append(i+1)
                    .append(". ")
                    .append(entidades.get(i).getNombre())
                    .append(" - ")
                    .append(puntosDe(entidades.get(i)))
                    .append("\n");
        }
        return cabecera + entidadesString;
    }

    public Double puntosDe(Entidad entidad){
        return diccionarioPuntos.get(entidad);
    }

    public void agregarEntidad(Entidad entidad, Double puntos){
        List<Entidad> listaEntidades = new ArrayList<Entidad>(entidades);
        listaEntidades.remove(entidad); //Chequea automaticamente si esta o no
        listaEntidades.add(entidad);
        diccionarioPuntos.put(entidad,puntos);

        entidades = Collections.unmodifiableList(listaEntidades);
    }

    public void ordernar(){
        List<Entidad> listaEntidades = new ArrayList<Entidad>(entidades);
        listaEntidades.sort((e1,e2) -> diccionarioPuntos.get(e2).compareTo(diccionarioPuntos.get(e1)));
        entidades = Collections.unmodifiableList(listaEntidades);
    }
}


