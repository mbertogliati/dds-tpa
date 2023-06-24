package ar.edu.utn.frba.dds.meta_datos_geo;

import lombok.Getter;

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
