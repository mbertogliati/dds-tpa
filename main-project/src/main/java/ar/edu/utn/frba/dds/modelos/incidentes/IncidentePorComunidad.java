package ar.edu.utn.frba.dds.modelos.incidentes;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import java.time.LocalDateTime;
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
@Table(name = "incidentes_por_comunidad")
@Getter
@Setter
public class IncidentePorComunidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "incidente_id", referencedColumnName = "id")
    @Cascade(CascadeType.ALL)
    private Incidente incidente;

    @Column(name = "esta_cerrado")
    private boolean estaCerrado = false;

    @Column(name = "fecha_hora_cierre", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaHoraCierre = null;

    @ManyToOne
    @JoinColumn(name = "autor_cierre_id", referencedColumnName = "id")
    @Cascade(CascadeType.ALL)
    private Persona autorCierre = null;

    public IncidentePorComunidad(){}
    public IncidentePorComunidad(Incidente incidente){
        this.incidente = incidente;
    }
}
