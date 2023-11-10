package ar.edu.utn.frba.dds.modelos.entidades;

import ar.edu.utn.frba.dds.modelos.base.ModelBase;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.utilidades.Ubicacion;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "entidades_prestadoras", schema = "public")
@Getter
@Setter

public class EntidadPrestadora extends ModelBase implements Informable{
    @OneToMany(mappedBy = "prestadora")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Entidad> entidades = new ArrayList<>();

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "personaAInformar_id", referencedColumnName = "id")
    private Persona personaAInformar;

    @ManyToOne
    private OrganismoControl organismoControl;

    public EntidadPrestadora(){}
    public EntidadPrestadora(String nombre){
        this.nombre = nombre;
    }

    public EntidadPrestadora(int id, String nombre, List<Entidad> entidades) {
        this.id=id;
        this.nombre=nombre;
        this.entidades=entidades;
    }

    public void setUbicacion(Ubicacion ubicacion){
        this.entidades.forEach(e -> {
            e.setUbicacion(ubicacion);
            e.getEstablecimientos().forEach(est -> est.setUbicacion(ubicacion));
        });
    }
    public void agregarEntidad(Entidad entidad){
        this.entidades.add(entidad);
    }
    public void sacarEntidadPorId(int idEntidad){
        this.entidades.removeIf(e -> e.getId() == idEntidad);
    }

    @Override
    public void setActivo(Boolean valor){
        super.setActivo(valor);
        entidades.forEach(e -> e.setActivo(valor));
    }
}
