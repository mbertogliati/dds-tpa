package ar.edu.utn.frba.dds.modelos.entidades;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import java.util.List;

public interface Informable {
    public Persona getPersonaAInformar();
    public List<Entidad> getEntidades();
    public String getNombre();
}
