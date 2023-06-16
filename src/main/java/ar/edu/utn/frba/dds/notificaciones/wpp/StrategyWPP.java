package ar.edu.utn.frba.dds.notificaciones.wpp;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.notificaciones.StrategyNotificacion;

public class StrategyWPP implements StrategyNotificacion {
    private AdapterWPP adapter;

    public StrategyWPP(){
        this.adapter = new APIWhatsapp();
    }

    @Override
    public void enviarNotificacion(String mensaje, Persona persona) {
        this.adapter.enviarWPP(mensaje, persona.getWhatsapp());
    }
}
