package ar.edu.utn.frba.dds.importadorEntidades;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.frba.dds.modelos.entidades.OrganismoControl;
import ar.edu.utn.frba.dds.modelos.importadorEntidades.ImportadorEntidadAdapter;
import ar.edu.utn.frba.dds.modelos.importadorEntidades.ImportadorEntidadCSV;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ImportadorEntidadTest {
    private ImportadorEntidadAdapter importador = new ImportadorEntidadCSV();

    @BeforeEach
    public void init(){
    }

    @Test
    @DisplayName("ej4.csv")
    public void ej4() throws IOException {

        String path = "src/test/java/ar/edu/utn/frba/dds/importadorEntidades/ej4.csv";
        List<OrganismoControl> organismosDeControl = importador.importar(path);
        assertEquals(1, organismosDeControl.size());

        var controlEntidadCNRT = organismosDeControl.get(0);
        var prestadorTrenesArgentinos = controlEntidadCNRT.getEntidadesPrestadoras().get(0);
        var prestadorSubterraneos = controlEntidadCNRT.getEntidadesPrestadoras().get(1);

        assertEquals("CNRT", controlEntidadCNRT.getNombre());
        assertEquals("Trenes Argentinos", prestadorTrenesArgentinos.getNombre());
        assertEquals("Subterráneos de Buenos Aires", prestadorSubterraneos.getNombre());

        assertEquals(3, controlEntidadCNRT.getEntidades().size());
        assertEquals(2, prestadorSubterraneos.getEntidades().size());
        assertEquals(1, prestadorTrenesArgentinos.getEntidades().size());
    }
}
