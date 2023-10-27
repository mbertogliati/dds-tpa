package ar.edu.utn.frba.dds.modelos.comunidades.notificacionesPersona;

import ar.edu.utn.frba.dds.modelos.notificaciones.Notificable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Getter
@Setter
@Entity
@Table(name = "listadosNotificables")
public class ListadoNotificables implements Notificable {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToMany
    @Cascade(CascadeType.ALL)
    private List<NotificableConFecha> notificables = new ArrayList<>();

    public ListadoNotificables() {}

    public void agregarNotificables(Notificable ... notificablesRecibidos) {
        for(Notificable unNotificable : notificablesRecibidos) {
            this.notificables.add(nuevoNotificable(unNotificable));
        }
    }

    private NotificableConFecha nuevoNotificable(Notificable notificable){
        NotificableConFecha resultado = new NotificableConFecha(notificable);
        resultado.setFecha(LocalDateTime.now());
        return resultado;
    }

    private List<String> obtenerNotificablesDelDia() {
        List<String> filtrados = new ArrayList<String>();

        for(NotificableConFecha notificableConFecha : this.notificables){
            if(notificableConFecha.getFecha().plusDays(1).isAfter(LocalDateTime.now())){
                filtrados.add(notificableConFecha.getMensaje());
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
        for(String mensaje : this.obtenerNotificablesDelDia()) {
            info += mensaje + "\n";
        }
        this.vaciarNotificables();
        return info;
    }

    public boolean estaVacio() {
        return this.notificables.isEmpty();
    }
}
