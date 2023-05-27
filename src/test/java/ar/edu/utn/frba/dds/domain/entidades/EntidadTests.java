package ar.edu.utn.frba.dds.domain.entidades;

import ar.edu.utn.frba.dds.domain.servicios.Servicio;
import ar.edu.utn.frba.dds.domain.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.domain.utilidades.Etiqueta;
import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EntidadTests {
    private Servicio banioHombreBebe;
    private Servicio escaleraMolinete;
    private Servicio escaleraMolineteAncho;
    private Servicio banioSinGeneroAscensor;
    private Servicio banioMujerDiscapRampa;
    private Servicio escMecanicaMolinete;
    private List<Servicio> serviciosIniciales = new ArrayList<>();
    private EntidadPrestadora entidadPrestadora1;
    private EntidadPrestadora entidadPrestadora2;
    private EntidadPrestadora entidadPrestadora3;
    private OrganismoControl organismoControl1;
    private OrganismoControl organismoControl2;
    private Establecimiento sucursal1;
    private Establecimiento sucursal2;
    private Establecimiento estacion1;
    private Establecimiento estacion2;
    private Establecimiento estacion3;

    @BeforeEach
    public void init() throws IOException {
        this.iniciarServicios();
        this.iniciarEntidades();
        this.iniciarEstablecimientos();
    }

    @Test
    @DisplayName("Se pueden agregar establecimientos a una entidad")
    public void crearEntidad(){
        entidadPrestadora1.agregarEstablecimiento(sucursal1);
        entidadPrestadora1.agregarEstablecimiento(sucursal2);

        Assertions.assertTrue(entidadPrestadora1.getEstablecimientos().size() == 2);
    }

    @Test
    @DisplayName("Se pueden eliminar establecimientos de una entidad")
    public void eliminarEntidad(){
        entidadPrestadora1.agregarEstablecimiento(sucursal1);
        entidadPrestadora1.agregarEstablecimiento(sucursal2);

        entidadPrestadora1.eliminarEstablecimiento(sucursal1);

        Assertions.assertTrue(entidadPrestadora1.getEstablecimientos().size() == 1);
    }

    @Test
    @DisplayName("Se pueden agregar servicios a un establecimiento")
    public void agregarServicios(){
        sucursal1.agregarServicio(new ServicioPrestado(banioHombreBebe));
        sucursal1.agregarServicio(new ServicioPrestado(banioMujerDiscapRampa));

        Assertions.assertTrue(sucursal1.getServiciosPrestados().size() == 2);
        Assertions.assertTrue(sucursal1.getServiciosPrestados().stream().filter(servicioPrestado -> servicioPrestado.isDisponibilidad() == true).toList().size() == 2);
    }

    @Test
    @DisplayName("Se pueden eliminar servicios de un establecimiento")
    public void eliminarServicios(){
        sucursal1.agregarServicio(new ServicioPrestado(banioHombreBebe));
        sucursal1.agregarServicio(new ServicioPrestado(banioMujerDiscapRampa));
        sucursal1.agregarServicio(new ServicioPrestado(escaleraMolinete));

        sucursal1.eliminarServicio(banioMujerDiscapRampa);

        Assertions.assertTrue(sucursal1.getServiciosPrestados().size() == 2);
        Assertions.assertTrue(sucursal1.getServiciosPrestados().stream().filter(servicioPrestado -> servicioPrestado.isDisponibilidad() == true).toList().size() == 2);
    }

    @Test
    @DisplayName("Se pueden modificar los servicios prestados de un establecimiento")
    public void modificarServicios(){
        sucursal1.agregarServicio(new ServicioPrestado(banioHombreBebe));
        sucursal1.agregarServicio(new ServicioPrestado(banioMujerDiscapRampa));
        sucursal1.agregarServicio(new ServicioPrestado(escaleraMolinete));

        sucursal1.setServicio(banioMujerDiscapRampa, false);

        Assertions.assertTrue(sucursal1.getServiciosPrestados().size() == 3);
        Assertions.assertTrue(sucursal1.getServiciosPrestados().stream().filter(servicioPrestado -> servicioPrestado.isDisponibilidad() == true).toList().size() == 2);
        Assertions.assertTrue(sucursal1.getServiciosPrestados().stream().filter(servicioPrestado -> servicioPrestado.isDisponibilidad() == true).toList().size() == 1);
    }

    private void iniciarEstablecimientos() throws IOException {
        this.estacion1 = new Establecimiento(new Ubicacion((float) -30.150, (float) -30.150), new Denominacion("Estacion"));
        this.estacion2 = new Establecimiento(new Ubicacion((float) -31.150, (float) -31.150), new Denominacion("Estacion"));
        this.estacion3 = new Establecimiento(new Ubicacion((float) -32.150, (float) -32.150), new Denominacion("Estacion"));
        this.sucursal1 = new Establecimiento(new Ubicacion((float) -40.000, (float) -40.000), new Denominacion("Sucursal"));
        this.sucursal2 = new Establecimiento(new Ubicacion((float) -41.000, (float) -41.000), new Denominacion("Sucursal"));
    }

    private void iniciarEntidades(){
        this.entidadPrestadora1 = new EntidadPrestadora("Primera entidad prestadora de banco", new Denominacion("banco"));
        this.entidadPrestadora2 = new EntidadPrestadora("Segunda entidad prestadora de banco", new Denominacion("banco"));
        this.entidadPrestadora3 = new EntidadPrestadora("Primera entidad prestadora de transporte", new Denominacion("transporte"));
        this.organismoControl1 = new OrganismoControl("Primer organismo de control transporte", new Denominacion("controlTransporte"));
        this.organismoControl2 = new OrganismoControl("Primer organismo de control banco", new Denominacion("controlBanco"));
    }

    private void iniciarServicios(){
        Etiqueta eBanio = new Etiqueta("tipoServicio", "banio");
        Etiqueta eEscalera = new Etiqueta("elevacion", "escalera");
        Etiqueta eAscensor = new Etiqueta("elevacion", "ascensor");
        Etiqueta eEscaleraMecanica = new Etiqueta("elevacion", "escaleraMecanica");
        Etiqueta eRampa = new Etiqueta("elevacion", "rampa");
        Etiqueta eDiscapacitados = new Etiqueta("tipoBanio", "discapacitados");
        Etiqueta eBebe = new Etiqueta("tipoBanio", "bebe");
        Etiqueta eHombre = new Etiqueta("genero", "hombre");
        Etiqueta eMujer = new Etiqueta("genero", "mujer");
        Etiqueta eSinGenero = new Etiqueta("genero", "sinGenero");
        Etiqueta eMolinete = new Etiqueta("acceso", "molineteComun");
        Etiqueta eMolineteAncho = new Etiqueta("acceso", "molineteAncho");

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
}
