package ar.edu.utn.frba.dds.domain.incidentes;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.notificaciones.Notificable;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Incidente implements Notificable {
    @Getter
    private List<ServicioPrestado> serviciosAfectados;
    @Getter @Setter
    private String observaciones;
    @Getter @Setter
    private LocalDateTime fechaHoraApertura;
    @Getter
    private Persona autorApertura;

    public Incidente() {
        this.serviciosAfectados = new ArrayList<>();
    }

    public Incidente(Persona autor){
        this.autorApertura = autor;
        this.serviciosAfectados = new ArrayList<>();
    }

    public void agregarIncidenteComunidad() {
        this.autorApertura.getMembresias().stream().map(m -> m.getComunidad()).forEach(c -> c.agregarIncidente(this));
    }

    @Override
    public String getInfo() {
        List<String> servicios = this.serviciosAfectados.stream().map(s -> s.getServicio()).map(s -> s.getStringEtiquetas()).toList();
        String respuesta = "";
        for (String texto : servicios){
            respuesta = respuesta + texto + " | ";
        }
        return "\nServicios afectados: " + respuesta;
    }

    public Entidad obtenerEntidad(){
        return getServiciosAfectados().get(0).getEstablecimiento().getEntidad();
    }
}
