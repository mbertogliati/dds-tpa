package ar.edu.utn.frba.dds.meta_datos_geo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "localidades")
@Getter
@Setter
public class Localidad  {
    @Id
    private String id;

    @Column(name = "nombre")
    private String nombre;

    public Localidad(){}

    public Localidad(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
