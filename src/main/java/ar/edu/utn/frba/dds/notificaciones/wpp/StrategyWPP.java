package ar.edu.utn.frba.dds.notificaciones.wpp;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.notificaciones.StrategyNotificacion;

public class StrategyWPP implements StrategyNotificacion {

    @Setter
    private AdapterWPP adapter;

    /*public StrategyWPP(AdapterWPP adapter){//TODO: Debe recibir la estrategia por parametro ya sea en el constructor o en el setter
        this.adapter = new WhatsappTwilio();
    }*/

    public void setAdapter(AdapterWPP adapter){
        this.adapter = adapter;
    }

    @Override
    public void enviarNotificacion(String mensaje, Persona persona) {
        this.adapter.enviarWPP(mensaje, persona.getWhatsapp());
    }
}
