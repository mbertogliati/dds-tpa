package ar.edu.utn.frba.dds.controllers.utils.builders.builderPersona;

import ar.edu.utn.frba.dds.controllers.exceptions.FormInvalidoException;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Localidad;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.MetadatoGeografico;
import ar.edu.utn.frba.dds.modelos.utilidades.Coordenada;
import ar.edu.utn.frba.dds.modelos.utilidades.FechasDeSemana;
import ar.edu.utn.frba.dds.modelos.utilidades.Ubicacion;
import ar.edu.utn.frba.dds.repositorios.converters.EstrategiaMomentoNotificacionConverter;
import ar.edu.utn.frba.dds.repositorios.meta_datos_geo.LocalidadRepositorio;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonaBuilderHashmap implements PersonaBuilder {
    private Map<String,List<String>> hashMapPersona;
    private LocalidadRepositorio repoLocalidad;
    private Persona personaEnCreacion = new Persona();
    private EstrategiaMomentoNotificacionConverter converterMomentoNotificacion = new EstrategiaMomentoNotificacionConverter();

    public PersonaBuilderHashmap(@NotNull Map<String, List<String>> hashMapPersona,@NotNull LocalidadRepositorio repoLocalidad){
        this.hashMapPersona = hashMapPersona;
        this.repoLocalidad = repoLocalidad;
    }
    @Override
    public Persona get() {
        return personaEnCreacion;
    }
    @Override
    public void reset() {
        this.personaEnCreacion = new Persona();
    }

    @Override
    public PersonaBuilder init() {
        this.reset();
        return this;
    }

    @Override
    public PersonaBuilder init(Persona persona) {
        this.personaEnCreacion = persona;
        return this;
    }

    @Override
    public PersonaBuilder configurarNombres() {
        personaEnCreacion.setNombre(hashMapPersona.get("nombre").get(0));
        personaEnCreacion.setApellido(hashMapPersona.get("apellido").get(0));
        return this;
    }

    @Override
    public PersonaBuilder configurarInformacionDeContacto() {
        personaEnCreacion.setEmail(hashMapPersona.get("email").get(0));
        //TODO: Cuando el número es muy largo tira excepcion
        if(hashMapPersona.get("celular").get(0).length() > 10) throw new FormInvalidoException("El número de celular es muy largo");
        personaEnCreacion.setWhatsapp(Integer.parseInt(hashMapPersona.get("celular").get(0)));
        return this;
    }

    @Override
    public PersonaBuilder configurarInformacionDeUbicacion() {
        Localidad localidad = repoLocalidad.obtenerLocalidadPorId(hashMapPersona.get("localidad").get(0));
        if(hashMapPersona.get("latitud") != null && !hashMapPersona.get("latitud").isEmpty()
         && hashMapPersona.get("longitud") != null && !hashMapPersona.get("longitud").isEmpty()) {
            Coordenada coordenada = new Coordenada(Float.parseFloat(hashMapPersona.get("latitud").get(0)), Float.parseFloat(hashMapPersona.get("longitud").get(0)));
            MetadatoGeografico geografico = new MetadatoGeografico(localidad);
            Ubicacion ubicacion = new Ubicacion();
            ubicacion.setCoordenada(coordenada);
            ubicacion.setMetadato(geografico);
            personaEnCreacion.setUltimaUbicacion(ubicacion);
        }
        return this;
    }

    @Override
    public PersonaBuilder configurarPreferenciasNotificacion() {
        configurarFechas();
        personaEnCreacion.setMetodoNotificacion(hashMapPersona.get("medio_notificacion").get(0));
        personaEnCreacion.setEstrategiaMomentoNotificacion(converterMomentoNotificacion.convertToEntityAttribute(hashMapPersona.get("momento_notificacion").get(0)));
        return this;
    }
    private void configurarFechas() {
        //CARGO FECHAS NUEVAS
        List<String> dias =  hashMapPersona.get("dias[]");
        List<String> horas = hashMapPersona.get("horas[]");
        if(dias == null || horas == null) return;
        List<FechasDeSemana> fechasSemana = new ArrayList<>();
        for(int i = 0; i< dias.size(); i++){
            fechasSemana.add(new FechasDeSemana(DayOfWeek.valueOf(dias.get(i)), LocalTime.parse(horas.get(i))));
        }
        personaEnCreacion.setFechas(fechasSemana);
    }
}
