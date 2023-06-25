package ar.edu.utn.frba.dds.meta_datos_geo.geo_ref.api_models;

public class MunicipioGeoref {
    public String id;
    public String nombre;

    public MunicipioGeoref() {
        this.id = "";
        this.nombre = "";
    }

    public MunicipioGeoref(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
