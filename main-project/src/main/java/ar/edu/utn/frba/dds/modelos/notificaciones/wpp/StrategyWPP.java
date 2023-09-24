package ar.edu.utn.frba.dds.modelos.notificaciones.wpp;

import ar.edu.utn.frba.dds.modelos.comunidades.Persona;
import ar.edu.utn.frba.dds.modelos.notificaciones.StrategyNotificacion;
import lombok.Setter;

public class StrategyWPP implements StrategyNotificacion {

    @Setter
    private AdapterWPP adapter;

    @Override
    public void enviarNotificacion(String mensaje, Persona persona) {
        this.adapter.enviarWPP(mensaje, persona.getWhatsapp());
    }
}
