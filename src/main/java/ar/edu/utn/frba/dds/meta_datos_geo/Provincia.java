package ar.edu.utn.frba.dds.meta_datos_geo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class Provincia {
    @Getter
    private int id;
    @Getter
    private String nombre;
    @Getter @Setter
    private List<Municipio> municipios;

    public Provincia(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }
}
