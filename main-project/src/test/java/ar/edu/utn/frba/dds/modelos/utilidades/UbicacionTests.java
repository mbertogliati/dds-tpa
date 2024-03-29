package ar.edu.utn.frba.dds.modelos.utilidades;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.modelos.meta_datos_geo.AdapterProveedorMetadatosGeograficos;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Departamento;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Localidad;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.MetadatoGeografico;
import ar.edu.utn.frba.dds.modelos.meta_datos_geo.Provincia;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        when(adapter.obtenerMetadatoGeografico(isA(Coordenada.class))).thenReturn(this.BuildFakeMetadatoGeografico());
        Ubicacion unaUbicacion = new Ubicacion(-20.5f, 1.7f);
        unaUbicacion.setAdapterProveedorMetadatosGeograficos(adapter);

        //act
        unaUbicacion.cargarMetadatosGeograficos();

        //assert
        Assertions.assertNotNull(unaUbicacion);
        Assertions.assertNotNull(unaUbicacion.getMetadato().getProvincia());
        Assertions.assertNotNull(unaUbicacion.getMetadato().getLocalidad());
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
        Assertions.assertNull(unaUbicacion.getMetadato());
    }

    @Test
    @DisplayName("Se puede asignar una latitud y una longitud a una ubicación creada")
    public void setLatitudYLongitudAUbicacionCreada() {
        //arrange
        Ubicacion unaUbicacion = new Ubicacion(-20.5f, 1.7f);

        //act
        unaUbicacion.getCoordenada().setLatitudLongitud(1.5f, 1.2f);

        //assert
        Assertions.assertNotNull(unaUbicacion);
        Assertions.assertEquals(1.5f, unaUbicacion.getCoordenada().getLatitud());
        Assertions.assertEquals(1.2f, unaUbicacion.getCoordenada().getLongitud());
    }


    private MetadatoGeografico BuildFakeMetadatoGeografico() {
        Provincia provincia = new Provincia("1", "Buenos Aires");
        Departamento departamento = new Departamento("4", "Departamento de Pikachu");
        Localidad localidad = new Localidad("3", "Lugano");
        return new MetadatoGeografico(provincia, departamento, localidad);
    }
}
