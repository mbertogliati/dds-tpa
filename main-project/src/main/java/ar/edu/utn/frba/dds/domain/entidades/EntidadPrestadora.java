package ar.edu.utn.frba.dds.domain.entidades;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.utilidades.InformacionAdapter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "entidadesPrestadoras")
@Getter
@Setter
public class EntidadPrestadora implements Informable{
    @Id
    @GeneratedValue
    private int id;

    @OneToMany
    @JoinColumn(name = "entidad_prestadora_id", referencedColumnName = "id")
    private List<Entidad> entidades = new ArrayList<>();

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "personaAInformar_id", referencedColumnName = "id")
    private Persona personaAInformar;

    public EntidadPrestadora(){}
    public EntidadPrestadora(String nombre){
        this.nombre = nombre;
    }

}
