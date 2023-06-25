package ar.edu.utn.frba.dds.domain.incidentes;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.notificaciones.Notificable;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Incidente implements Notificable {
    @Getter
    private List<ServicioPrestado> servicioPrestados;
    @Getter @Setter
    private String observaciones;
    @Getter
    private Date fechaApertura;
    @Getter
    private Persona autorApertura;

    public Incidente(Persona autor, ServicioPrestado ... serviciosPrestados){
        this.fechaApertura = new Date();
        this.autorApertura = autor;
        this.servicioPrestados = new ArrayList<>();
        Collections.addAll(this.servicioPrestados, serviciosPrestados);
    }

    public void agregarIncidenteComunidad(Persona persona){
        persona.getMembresias().stream().map(m -> m.getComunidad()).forEach(c -> c.agregarIncidente(this));
    }

    @Override
    public String getInfo() {
        List<String> servicios = this.servicioPrestados.stream().map(s -> s.getServicio()).map(s -> s.getStringEtiquetas()).toList();
        String respuesta = "";
        for (String texto : servicios){
            respuesta = respuesta + texto + " | ";
        }
        return "\nServicios afectados: " + respuesta;
    }
}
