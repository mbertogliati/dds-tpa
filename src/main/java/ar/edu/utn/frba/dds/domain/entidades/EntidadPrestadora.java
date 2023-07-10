package ar.edu.utn.frba.dds.domain.entidades;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.utilidades.InformacionAdapter;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class EntidadPrestadora implements Informable{
    @Getter @Setter
    private List<Entidad> entidades = new ArrayList<>();
    @Getter @Setter
    private String nombre;
    @Getter @Setter
    private Persona personaAInformar;

    public EntidadPrestadora(String nombre){
        this.nombre = nombre;
    }

}
