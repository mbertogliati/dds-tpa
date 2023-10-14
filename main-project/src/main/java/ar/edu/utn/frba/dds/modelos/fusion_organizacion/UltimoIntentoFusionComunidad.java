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
public class UltimoIntentoFusionComunidad {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "comunidad2_id", referencedColumnName = "id")
    private Comunidad comunidad;

    @Column(name = "fechaIntento")
    private LocalDateTime ultIntentoFusion;

    public UltimoIntentoFusionComunidad(){

    }

    public UltimoIntentoFusionComunidad(Comunidad comunidad, LocalDateTime fecha){
        this.comunidad=comunidad;
        this.ultIntentoFusion=fecha;
    }
}
