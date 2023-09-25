package ar.edu.utn.frba.dds.modelos.entidades;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "entidadesPrestadoras")
@Getter
@Setter
public class EntidadPrestadora implements Informable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = { CascadeType.ALL })
    @JoinColumn(name = "entidad_prestadora_id", referencedColumnName = "id")
    private List<Entidad> entidades = new ArrayList<>();

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "personaAInformar_id", referencedColumnName = "id")
    private Persona personaAInformar;

    public EntidadPrestadora(){}
    public EntidadPrestadora(String nombre){
        this.nombre = nombre;
    }

}
