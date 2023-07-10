package ar.edu.utn.frba.dds.domain.entidades;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;

import java.util.List;

public interface Informable {
    public Persona getPersonaAInformar();
    public List<Entidad> getEntidades();
    public String getNombre();
}
