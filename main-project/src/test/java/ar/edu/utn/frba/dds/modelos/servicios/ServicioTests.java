package ar.edu.utn.frba.dds.modelos.servicios;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ServicioTests {
    private Servicio banioHombreBebe;
    private Servicio escaleraMolinete;
    private Servicio escaleraMolineteAncho;
    private Servicio banioSinGeneroAscensor;
    private Servicio banioMujerDiscapRampa;
    private Servicio escMecanicaMolinete;
    private List<Servicio> serviciosIniciales = new ArrayList<>();

    @BeforeEach
    public void init() {

        TipoEtiquetas teTipoServicio = new TipoEtiquetas("tipoServicio");
        TipoEtiquetas teElevacion = new TipoEtiquetas("elevacion");
        TipoEtiquetas teTipoBanio = new TipoEtiquetas("tipoBanio");
        TipoEtiquetas teGenero = new TipoEtiquetas("genero");
        TipoEtiquetas teAcceso = new TipoEtiquetas("acceso");

        Etiqueta eBanio = new Etiqueta(teTipoServicio, "banio");
        Etiqueta eEscalera = new Etiqueta(teElevacion, "escalera");
        Etiqueta eAscensor = new Etiqueta(teElevacion, "ascensor");
        Etiqueta eEscaleraMecanica = new Etiqueta(teElevacion, "escaleraMecanica");
        Etiqueta eRampa = new Etiqueta(teElevacion, "rampa");
        Etiqueta eDiscapacitados = new Etiqueta(teTipoBanio, "discapacitados");
        Etiqueta eBebe = new Etiqueta(teTipoBanio, "bebe");
        Etiqueta eHombre = new Etiqueta(teGenero, "hombre");
        Etiqueta eMujer = new Etiqueta(teGenero, "mujer");
        Etiqueta eSinGenero = new Etiqueta(teGenero, "sinGenero");
        Etiqueta eMolinete = new Etiqueta(teAcceso, "molineteComun");
        Etiqueta eMolineteAncho = new Etiqueta(teAcceso, "molineteAncho");

        List<Etiqueta> et_banioHombreBebe = new ArrayList<>();
        et_banioHombreBebe.add(eBanio);
        et_banioHombreBebe.add(eHombre);
        et_banioHombreBebe.add(eBebe);

        List<Etiqueta> et_escaleraMolinete = new ArrayList<>();
        et_escaleraMolinete.add(eEscalera);
        et_escaleraMolinete.add(eMolinete);

        List<Etiqueta> et_escaleraMolineteAncho = new ArrayList<>();
        et_escaleraMolineteAncho.add(eEscalera);
        et_escaleraMolineteAncho.add(eMolineteAncho);

        List<Etiqueta> et_banioSinGeneroAscensor = new ArrayList<>();
        et_banioSinGeneroAscensor.add(eBanio);
        et_banioSinGeneroAscensor.add(eSinGenero);
        et_banioSinGeneroAscensor.add(eAscensor);

        List<Etiqueta> et_banioMujerDiscapRampa = new ArrayList<>();
        et_banioMujerDiscapRampa.add(eBanio);
        et_banioMujerDiscapRampa.add(eMujer);
        et_banioMujerDiscapRampa.add(eDiscapacitados);
        et_banioMujerDiscapRampa.add(eRampa);

        List<Etiqueta> et_escMecanicaMolinete = new ArrayList<>();
        et_escMecanicaMolinete.add(eEscaleraMecanica);
        et_escMecanicaMolinete.add(eMolinete);

        banioHombreBebe = new Servicio(et_banioHombreBebe);
        escaleraMolinete = new Servicio(et_escaleraMolinete);
        escaleraMolineteAncho = new Servicio(et_escaleraMolineteAncho);
        banioSinGeneroAscensor = new Servicio(et_banioSinGeneroAscensor);
        banioMujerDiscapRampa = new Servicio(et_banioMujerDiscapRampa);
        escMecanicaMolinete = new Servicio(et_escMecanicaMolinete);

        serviciosIniciales.add(banioHombreBebe);
        serviciosIniciales.add(escaleraMolinete);
        serviciosIniciales.add(escaleraMolineteAncho);
        serviciosIniciales.add(banioSinGeneroAscensor);
        serviciosIniciales.add(banioMujerDiscapRampa);
        serviciosIniciales.add(escMecanicaMolinete);
    }

    @Test
    @DisplayName("Se pueden crear servicios y agregarle etiquetas nuevas")
    public void agregarEtiquetas() {

        TipoEtiquetas teElevacion = new TipoEtiquetas("elevacion");
        TipoEtiquetas teTipoServicio = new TipoEtiquetas("tipoServicio");

        Servicio nuevoServicio = new Servicio(new ArrayList<Etiqueta>());
        nuevoServicio.agregarEtiqueta(new Etiqueta(teElevacion, "cintaTransportadora"));
        nuevoServicio.agregarEtiqueta(new Etiqueta(teTipoServicio, "banio"));

        Assertions.assertTrue(nuevoServicio.getEtiquetas().stream().anyMatch(e -> e.getTipo() == teElevacion));
        Assertions.assertTrue(nuevoServicio.getEtiquetas().stream().anyMatch(e -> e.getTipo() == teTipoServicio));
    }

    @Test
    @DisplayName("Se pueden eliminar etiquetas de un servicio")
    public void eliminarEtiquetas() {
        TipoEtiquetas teElevacion = new TipoEtiquetas("elevación");
        escaleraMolinete.eliminarEtiqueta(new Etiqueta(teElevacion, "escalera"));

        Assertions.assertFalse(escaleraMolinete.getEtiquetas().stream().anyMatch(e -> e.getTipo() == teElevacion));
    }
}
