package ar.edu.utn.frba.dds.importadorEntidades;

import ar.edu.utn.frba.dds.domain.entidades.ControlEntidades;
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
    @DisplayName("ej3.csv")
    public void ej3() throws IOException {

        String path = "src/test/java/ar/edu/utn/frba/dds/importadorEntidades/ej3.csv";
        List<ControlEntidades> controlEntidadesList = importador.importar(path);
        assertEquals(3, controlEntidadesList.size());

        var controlEntidadCNRT = controlEntidadesList.get(0);
        var prestadorTrenesArgentinos = controlEntidadesList.get(1);
        var prestadorSubterraneos = controlEntidadesList.get(2);

        assertEquals("CNRT", controlEntidadCNRT.getNombre());
        assertEquals("Trenes Argentinos", prestadorTrenesArgentinos.getNombre());
        assertEquals("Subterráneos de Buenos Aires", prestadorSubterraneos.getNombre());

        assertEquals(3, controlEntidadCNRT.getEntidades().size());
        assertEquals(2, prestadorSubterraneos.getEntidades().size());
        assertEquals(1, prestadorTrenesArgentinos.getEntidades().size());
    }
}
