package ar.edu.utn.frba.dds.notificaciones.wpp;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.notificaciones.StrategyNotificacion;
import lombok.Setter;

public class StrategyWPP implements StrategyNotificacion {

    @Setter
    private AdapterWPP adapter;

    @Override
    public void enviarNotificacion(String mensaje, Persona persona) {
        this.adapter.enviarWPP(mensaje, persona.getWhatsapp());
    }
}
