package ar.edu.utn.frba.dds.domain.incidentes;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import ar.edu.utn.frba.dds.domain.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.domain.notificaciones.Notificable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Entity
@Table(name = "incidentes")
@Getter @Setter
public class Incidente implements Notificable {
    @Id
    @GeneratedValue
    private int id;

    @ManyToMany
    private List<ServicioPrestado> serviciosAfectados;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "fecha_hora_apertura", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaHoraApertura;

    @ManyToOne
    @JoinColumn(name = "autor_apertura_id", referencedColumnName = "id")
    private Persona autorApertura;

    public Incidente() {
        this.serviciosAfectados = new ArrayList<>();
    }

    public Incidente(Persona autor){
        this.autorApertura = autor;
        this.serviciosAfectados = new ArrayList<>();
    }

    public void agregarServiciosPrestados(ServicioPrestado ... servicioPrestados){
        Collections.addAll(this.serviciosAfectados, servicioPrestados);
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
