package ar.edu.utn.frba.dds.domain.incidentes;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Table(name = "incidentes_por_comunidad")
@Getter
@Setter
public class IncidentePorComunidad {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "incidente_id", referencedColumnName = "id")
    private Incidente incidente;

    @Column(name = "esta_cerrado")
    private boolean estaCerrado = false;

    @Column(name = "fecha_hora_cierre", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaHoraCierre = null;

    @ManyToOne
    @JoinColumn(name = "autor_cierre_id", referencedColumnName = "id")
    private Persona autorCierre = null;
    public IncidentePorComunidad(){}
    public IncidentePorComunidad(Incidente incidente){
        this.incidente = incidente;
    }
}
