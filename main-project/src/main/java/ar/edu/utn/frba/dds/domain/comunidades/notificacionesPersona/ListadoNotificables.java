package ar.edu.utn.frba.dds.domain.comunidades.notificacionesPersona;

import ar.edu.utn.frba.dds.notificaciones.Notificable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "listadosNotificables")
@Getter
@Setter
public class ListadoNotificables implements Notificable {
    @Id
    @GeneratedValue
    private int id;

    @ManyToMany
    private List<NotificableConFecha> notificables;

    public ListadoNotificables() {
        this.notificables = new ArrayList<NotificableConFecha>();
    }

    public void agregarNotificables(Notificable ... notificablesRecibidos) {
        for(Notificable unNotificable : notificablesRecibidos) {
            this.notificables.add(nuevoNotificable(unNotificable));
        }
    }

    private NotificableConFecha nuevoNotificable(Notificable notificable){
        NotificableConFecha resultado = new NotificableConFecha();
        resultado.setFecha(LocalDateTime.now());
        resultado.setNotificable(notificable);
        return resultado;
    }

    private List<Notificable> obtenerNotificablesDelDia() {
        List<Notificable> filtrados = new ArrayList<Notificable>();

        for(NotificableConFecha notificableConFecha : this.notificables){
            if(notificableConFecha.getFecha().plusDays(1).isAfter(LocalDateTime.now())){
                filtrados.add(notificableConFecha.getNotificable());
            }
        }

        return filtrados;
    }

    public void vaciarNotificables(){
        this.notificables.clear();
    }

    @Override
    public String getInfo() {
        String info = "";
        for(Notificable notificable : this.obtenerNotificablesDelDia()) {
            info = info + notificable.getInfo() + "\n";
        }
        this.vaciarNotificables();
        return info;
    }

    public boolean estaVacio() {
        return this.notificables.isEmpty();
    }
}
