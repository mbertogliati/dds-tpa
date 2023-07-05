package ar.edu.utn.frba.dds.domain.utilidades;

import ar.edu.utn.frba.dds.meta_datos_geo.AdapterProveedorMetadatosGeograficos;
import ar.edu.utn.frba.dds.meta_datos_geo.Departamento;
import ar.edu.utn.frba.dds.meta_datos_geo.Localidad;
import ar.edu.utn.frba.dds.meta_datos_geo.MetadatoGeografico;
import ar.edu.utn.frba.dds.meta_datos_geo.Municipio;
import ar.edu.utn.frba.dds.meta_datos_geo.Provincia;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UbicacionTests {

    @Test
    @DisplayName("Se puede crear una ubicacion")
    public void crearUbicacion() {
        //arrange

        //act
        Ubicacion unaUbicacion = new Ubicacion(-20.5f, 1.7f);

        //assert
        Assertions.assertNotNull(unaUbicacion);
    }

    @Test
    @DisplayName("Se puede crear una ubicacion y asignar un adapter de metadatos geográficos")
    public void crearUbicacionYCargarMetadatosGeograficos() throws IOException {
        //arrange
        AdapterProveedorMetadatosGeograficos adapter = mock(AdapterProveedorMetadatosGeograficos.class);
        when(adapter.obtenerMetadatoGeografico(anyFloat(), anyFloat())).thenReturn(this.BuildFakeMetadatoGeografico());
        Ubicacion unaUbicacion = new Ubicacion(-20.5f, 1.7f);
        unaUbicacion.setAdapterProveedorMetadatosGeograficos(adapter);

        //act
        unaUbicacion.cargarMetadatosGeograficos();

        //assert
        Assertions.assertNotNull(unaUbicacion);
        Assertions.assertNotNull(unaUbicacion.getProvincia());
        Assertions.assertNotNull(unaUbicacion.getMunicipio());
        Assertions.assertNotNull(unaUbicacion.getLocalidad());
    }

    @Test
    @DisplayName("Se puede crear una ubicacion y no asignar un adapter de metadatos geográficos, los metadatos no se cargan")
    public void crearUbicacionSinAsignarAdapterYCargarMetadatosGeograficos() throws IOException {
        //arrange
        Ubicacion unaUbicacion = new Ubicacion(-20.5f, 1.7f);

        //act
        unaUbicacion.cargarMetadatosGeograficos();

        //assert
        Assertions.assertNotNull(unaUbicacion);
        Assertions.assertNull(unaUbicacion.getProvincia());
        Assertions.assertNull(unaUbicacion.getMunicipio());
        Assertions.assertNull(unaUbicacion.getLocalidad());
    }

    @Test
    @DisplayName("Se puede asignar una latitud y una longitud a una ubicación creada")
    public void setLatitudYLongitudAUbicacionCreada() {
        //arrange
        Ubicacion unaUbicacion = new Ubicacion(-20.5f, 1.7f);

        //act
        unaUbicacion.setLatitudLongitud(1.5f, 1.2f);

        //assert
        Assertions.assertNotNull(unaUbicacion);
        Assertions.assertEquals(1.5f, unaUbicacion.getLatitud());
        Assertions.assertEquals(1.2f, unaUbicacion.getLongitud());
    }


    private MetadatoGeografico BuildFakeMetadatoGeografico() {
        Provincia provincia = new Provincia(1, "Buenos Aires");
        Municipio municipio = new Municipio(2, "CABA");
        Departamento departamento = new Departamento(4, "Departamento de Pikachu");
        Localidad localidad = new Localidad("3", "Lugano");
        return new MetadatoGeografico(provincia, municipio, departamento, localidad);
    }
}
