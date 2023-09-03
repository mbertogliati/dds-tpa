package ar.edu.utn.frba.dds.domain.entidades;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.utilidades.InformacionAdapter;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrganismoControl implements Informable{
    @Getter
    private List<EntidadPrestadora> entidadesPrestadoras;
    @Getter @Setter
    private String nombre;
    @Getter @Setter
    private Persona personaAInformar;

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
