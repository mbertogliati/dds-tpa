package ar.edu.utn.frba.dds.domain.notificaciones;

public class EjemploNotificable implements Notificable{

    @Override
    public String getInfo() {
        return "Información de ejemplo para notificables";
    }
}
