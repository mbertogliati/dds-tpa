package ar.edu.utn.frba.dds.modelos.entidades;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
@Table(name = "organismosDeControl")
@Getter
@Setter
public class OrganismoControl implements Informable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "organismoControl")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<EntidadPrestadora> entidadesPrestadoras;

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "personaAInformar_id", referencedColumnName = "id")
    private Persona personaAInformar;

    public OrganismoControl(){
        this.entidadesPrestadoras = new ArrayList<EntidadPrestadora>();
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

    public void sacarEntidadPrestadoraPorId(int idBuscado){
        this.entidadesPrestadoras.removeIf(e -> e.getId() == idBuscado);
    }

}
