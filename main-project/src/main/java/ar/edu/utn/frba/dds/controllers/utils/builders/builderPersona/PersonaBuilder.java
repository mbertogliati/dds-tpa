package ar.edu.utn.frba.dds.controllers.utils.builders.builderPersona;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;

public interface PersonaBuilder {
    public Persona get();
    public PersonaBuilder init();
    public PersonaBuilder init(Persona persona);
    public PersonaBuilder configurarNombres();
    public PersonaBuilder configurarInformacionDeContacto();
    public PersonaBuilder configurarInformacionDeUbicacion();
    public PersonaBuilder configurarPreferenciasNotificacion();
    public void reset();

}
