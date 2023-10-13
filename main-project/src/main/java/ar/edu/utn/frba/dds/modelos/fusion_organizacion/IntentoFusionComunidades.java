package ar.edu.utn.frba.dds.modelos.fusion_organizacion;

import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "ultimoIntentoFusionComunidades")
public class IntentoFusionComunidades {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    private Comunidad comunidad1;
    @ManyToOne
    private Comunidad comunidad2;
    private LocalDateTime ultIntentoFusion;
}
