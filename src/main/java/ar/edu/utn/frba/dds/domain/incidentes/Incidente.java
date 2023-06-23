package ar.edu.utn.frba.dds.domain.incidentes;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.notificaciones.Notificable;
import lombok.Getter;
import lombok.Setter;

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

    public Incidente(Persona autor){
        this.fechaApertura = new Date();
        this.autorApertura = autor;
    }

    @Override
    public String getInfo() {
        String servicios = this.servicioPrestados.stream().map(s -> s.getServicio().getEtiquetas().stream().map(e -> e.getValor() + " - ")).toString();
        return "Servicios afectados: " + servicios;
    }
}
