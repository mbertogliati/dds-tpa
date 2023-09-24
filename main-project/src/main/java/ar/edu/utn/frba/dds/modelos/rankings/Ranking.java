package ar.edu.utn.frba.dds.modelos.rankings;

import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rankings")
@Getter
@Setter
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany
    @JoinColumn(name = "ranking_id", referencedColumnName = "id")
    private List<PuntosPorEntidad> puntosPorEntidad;

    @Column(name = "fecha_hora_creacion", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaHoraCreacion;

    @Column(name = "descripcion")
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


