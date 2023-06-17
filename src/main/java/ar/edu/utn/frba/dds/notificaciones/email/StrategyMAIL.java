package ar.edu.utn.frba.dds.notificaciones.email;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.notificaciones.StrategyNotificacion;
import ar.edu.utn.frba.dds.notificaciones.wpp.AdapterWPP;

public class StrategyMAIL implements StrategyNotificacion {
    private AdapterMAIL adapter;

    public StrategyMAIL() {
        this.adapter = new ApacheCommonsEmail();
    }

    @Override
    public void enviarNotificacion(String mensaje, Persona persona) {
        this.adapter.enviarMAIL(mensaje, persona.getEmail());
    }
}
