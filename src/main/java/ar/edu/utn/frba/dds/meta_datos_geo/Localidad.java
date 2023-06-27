package ar.edu.utn.frba.dds.meta_datos_geo;

import lombok.Getter;

public class Localidad {
    @Getter
    private String id;
    @Getter
    private String nombre;

    public Localidad(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
