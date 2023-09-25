package ar.edu.utn.frba.dds.modelos.rankings;

import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "puntosPorEntidades")
@Getter
@Setter
public class PuntosPorEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "entidad_id", referencedColumnName = "id")
    private Entidad entidad;

    @Column(name = "puntos")
    private Double puntos;

    public PuntosPorEntidad(){

    }
    public PuntosPorEntidad(Entidad entidad, Double puntos){
        this.entidad = entidad;
        this.puntos = puntos;
    }

}
