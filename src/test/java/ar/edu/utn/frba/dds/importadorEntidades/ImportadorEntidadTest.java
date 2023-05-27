package ar.edu.utn.frba.dds.importadorEntidades;

import ar.edu.utn.frba.dds.domain.entidades.Entidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImportadorEntidadTest {
    private ImportadorEntidadAdapter importador = new ImportadorEntidadCSV();

    @BeforeEach
    public void init(){
    }

    @Test
    @DisplayName("ej1.csv")
    public void ej1() throws IOException {

        String path = "src/test/java/ar/edu/utn/frba/dds/importadorEntidades/ej1.csv";
        List<Entidad> entidades = importador.crearEntidades(path);
        assertEquals(4, entidades.size());
        assertEquals("Linea B", entidades.get(0).getNombre());
        assertEquals("Transporte", entidades.get(0).getDenominacion().getDescripcion());
        assertEquals("Linea 60", entidades.get(2).getNombre());
        assertEquals("Administracion Estatal", entidades.get(1).getDenominacion().getDescripcion());

    }

    @Test
    @DisplayName("ej2.csv")
    public void ej2() throws IOException {

        String path = "src/test/java/ar/edu/utn/frba/dds/importadorEntidades/ej2.csv";
        List<Entidad> entidades = importador.crearEntidades(path);
        assertEquals(4, entidades.size());
        assertEquals("Dia", entidades.get(1).getNombre());
        assertEquals("Supermercado", entidades.get(1).getDenominacion().getDescripcion());
        assertEquals("Linea Roca", entidades.get(3).getNombre());
        assertEquals("Transporte", entidades.get(3).getDenominacion().getDescripcion());

    }

}
