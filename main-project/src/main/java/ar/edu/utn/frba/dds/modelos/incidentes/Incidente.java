package ar.edu.utn.frba.dds.modelos.incidentes;

import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.entidades.Entidad;
import ar.edu.utn.frba.dds.modelos.notificaciones.Notificable;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "incidentes")
@Getter @Setter
public class Incidente implements Notificable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "estaCerrado")
    private Boolean estaCerrado = false;

    public void actualizarEstado(List<IncidentePorComunidad> incPorComunidad){
        if(incPorComunidad.stream().allMatch(i -> i.isEstaCerrado())){
            this.estaCerrado = true;
        }
    }

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
        this.autorApertura.getMembresias().stream().map(m -> m.getComunidad()).filter(c -> c.getActivo()).forEach(c -> c.agregarIncidente(this));
    }

    @Override
    public String getInfo() {
        List<String> servicios = this.serviciosAfectados.stream().map(s -> s.getServicio()).map(s -> s.getStringEtiquetas()).toList();
        String respuesta = "";
        for (String texto : servicios){
            respuesta = respuesta + texto + "\n";
        }
        return "\nEstablecimiento: "+ this.serviciosAfectados.get(0).getEstablecimiento().getNombre() +"\nServicios afectados: " + respuesta;
    }

    public Entidad obtenerEntidad(){
        return getServiciosAfectados().get(0).getEstablecimiento().getEntidad();
    }
}
