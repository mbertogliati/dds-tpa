package ar.edu.utn.frba.dds.geoRef;

import lombok.Getter;
import lombok.Setter;

public class Provincia {
    @Getter
    private int id;
    @Getter
    private String nombre;

    public Provincia(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }
}
