package ar.edu.utn.frba.dds.domain.entidades;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.utilidades.InformacionAdapter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class EntidadPrestadora {
    @Getter @Setter
    private List<Entidad> entidades;
    @Getter @Setter
    private String nombre;
    @Getter @Setter
    private Persona informado;
    @Getter @Setter
    private InformacionAdapter generadorInformacion;

    public void enviarInformacion(){}
}
