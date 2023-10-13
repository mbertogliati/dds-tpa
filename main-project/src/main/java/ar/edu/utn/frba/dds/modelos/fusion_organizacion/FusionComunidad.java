package ar.edu.utn.frba.dds.modelos.fusion_organizacion;

import ar.edu.utn.frba.dds.modelos.base.ModelBase;
import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "fusionComunidad")
public class FusionComunidad {
    @Id
    @GeneratedValue
    private int id;
    private LocalDateTime fechaHoraFusion = LocalDateTime.now();
    @ManyToOne
    private Comunidad comunidad1;
    @ManyToOne
    private Comunidad comunidad2;
}
