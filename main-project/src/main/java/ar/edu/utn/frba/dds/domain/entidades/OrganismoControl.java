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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "organismosDeControl")
@Getter
@Setter
public class OrganismoControl implements Informable{
    @Id
    @GeneratedValue
    private int id;

    @OneToMany
    @JoinColumn(name = "organismo_control_id", referencedColumnName = "id")
    private List<EntidadPrestadora> entidadesPrestadoras;

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "personaAInformar_id", referencedColumnName = "id")
    private Persona personaAInformar;

    public OrganismoControl(){

    }
    public OrganismoControl(String nombre){
        this.nombre = nombre;
        this.entidadesPrestadoras = new ArrayList<>();
    }

    public List<Entidad> getEntidades(){
        Set<Entidad> entidades = new HashSet<Entidad>();
        for(EntidadPrestadora entidadPrestadora : entidadesPrestadoras){
            entidades.addAll(entidadPrestadora.getEntidades());
        }
        return entidades.stream().toList();
    }

}
