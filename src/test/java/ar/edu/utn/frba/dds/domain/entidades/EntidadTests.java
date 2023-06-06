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

public class EntidadTests {
    private Servicio banioHombreBebe;
    private Servicio escaleraMolinete;
    private Servicio escaleraMolineteAncho;
    private Servicio banioSinGeneroAscensor;
    private Servicio banioMujerDiscapRampa;
    private Servicio escMecanicaMolinete;
    private List<Servicio> serviciosIniciales = new ArrayList<>();
    private Entidad entidad1;
    private Entidad entidad2;
    private Entidad entidad3;
    private ControlEntidades organismoControl1;
    private ControlEntidades organismoControl2;
    private Establecimiento sucursal1;
    private Establecimiento sucursal2;
    private Establecimiento estacion1;
    private Establecimiento estacion2;
    private Establecimiento estacion3;

    @BeforeEach
    public void init() throws IOException {
        this.iniciarServicios();
        this.iniciarEstablecimientos();
        this.iniciarEntidades();
    }

    @Test
    @DisplayName("Se pueden agregar establecimientos a una entidad")
    public void agregarEstablecimientos(){
        entidad1.agregarEstablecimiento(sucursal1);
        entidad1.agregarEstablecimiento(sucursal2);

        Assertions.assertEquals(2, entidad1.getEstablecimientos().size());
    }

    @Test
    @DisplayName("Se pueden eliminar establecimientos de una entidad")
    public void eliminarEstablecimientos(){
        entidad1.agregarEstablecimiento(sucursal1);
        entidad1.agregarEstablecimiento(sucursal2);

        entidad1.eliminarEstablecimiento(sucursal1);

        Assertions.assertEquals(1, entidad1.getEstablecimientos().size());
    }

    @Test
    @DisplayName("Se pueden agregar servicios a un establecimiento")
    public void agregarServicios(){
        sucursal1.agregarServicio(new ServicioPrestado(banioHombreBebe));
        sucursal1.agregarServicio(new ServicioPrestado(banioMujerDiscapRampa));

        Assertions.assertEquals(2,sucursal1.getServiciosPrestados().size());
        Assertions.assertEquals(2,sucursal1.getServiciosPrestados().stream().filter(servicioPrestado -> servicioPrestado.isDisponibilidad() == true).toList().size());
    }

    @Test
    @DisplayName("Se pueden eliminar servicios de un establecimiento")
    public void eliminarServicios(){
        ServicioPrestado serv1 = new ServicioPrestado(banioHombreBebe);
        serv1.setId(0);
        ServicioPrestado serv2 = new ServicioPrestado(banioMujerDiscapRampa);
        serv2.setId(1);
        ServicioPrestado serv3 = new ServicioPrestado(escaleraMolinete);
        serv3.setId(2);

        sucursal1.agregarServicio(serv1);
        sucursal1.agregarServicio(serv2);
        sucursal1.agregarServicio(serv3);

        sucursal1.eliminarServicioPrestado(serv2);

        Assertions.assertEquals(2,sucursal1.getServiciosPrestados().size());
        Assertions.assertEquals(2,sucursal1.getServiciosPrestados().stream().filter(servicioPrestado -> servicioPrestado.isDisponibilidad() == true).toList().size());
    }

    @Test
    @DisplayName("Se pueden modificar los servicios prestados de un establecimiento")
    public void modificarServicios(){
        sucursal1.agregarServicio(new ServicioPrestado(banioHombreBebe));
        sucursal1.agregarServicio(new ServicioPrestado(banioMujerDiscapRampa));
        sucursal1.agregarServicio(new ServicioPrestado(escaleraMolinete));

        sucursal1.setServicio(banioMujerDiscapRampa, false);

        Assertions.assertEquals(3,sucursal1.getServiciosPrestados().size());
        Assertions.assertEquals(2,sucursal1.getServiciosPrestados().stream().filter(servicioPrestado -> servicioPrestado.isDisponibilidad() == true).toList().size());
        Assertions.assertEquals(1,sucursal1.getServiciosPrestados().stream().filter(servicioPrestado -> servicioPrestado.isDisponibilidad() == false).toList().size());
    }

    private void iniciarEstablecimientos() throws IOException {
        this.estacion1 = new Establecimiento(new Ubicacion((float) -30.150, (float) -30.150), new Denominacion("Estacion"));
        estacion1.setId(0);
        this.estacion2 = new Establecimiento(new Ubicacion((float) -31.150, (float) -31.150), new Denominacion("Estacion"));
        estacion2.setId(1);
        this.estacion3 = new Establecimiento(new Ubicacion((float) -32.150, (float) -32.150), new Denominacion("Estacion"));
        estacion3.setId(2);
        this.sucursal1 = new Establecimiento(new Ubicacion((float) -40.000, (float) -40.000), new Denominacion("Sucursal"));
        sucursal1.setId(3);
        this.sucursal2 = new Establecimiento(new Ubicacion((float) -41.000, (float) -41.000), new Denominacion("Sucursal"));
        sucursal2.setId(4);
    }

    private void iniciarEntidades(){
        this.entidad1 = new Entidad("Primera entidad de banco", new Denominacion("banco"));
        entidad1.setId(0);
        this.entidad2 = new Entidad("Segunda entidad de banco", new Denominacion("banco"));
        entidad2.setId(1);
        this.entidad3 = new Entidad("Primera entidad de transporte", new Denominacion("transporte"));
        entidad3.setId(2);
        this.organismoControl1 = new ControlEntidades("Primer organismo de control transporte", new Denominacion("controlTransporte"));
        organismoControl1.setId(3);
        this.organismoControl2 = new ControlEntidades("Primer organismo de control banco", new Denominacion("controlBanco"));
        organismoControl2.setId(4);
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
        banioHombreBebe.setId(0);
        escaleraMolinete = new Servicio(et_escaleraMolinete);
        escaleraMolinete.setId(1);
        escaleraMolineteAncho = new Servicio(et_escaleraMolineteAncho);
        escaleraMolineteAncho.setId(2);
        banioSinGeneroAscensor = new Servicio(et_banioSinGeneroAscensor);
        banioSinGeneroAscensor.setId(3);
        banioMujerDiscapRampa = new Servicio(et_banioMujerDiscapRampa);
        banioMujerDiscapRampa.setId(4);
        escMecanicaMolinete = new Servicio(et_escMecanicaMolinete);
        escMecanicaMolinete.setId(5);

        serviciosIniciales.add(banioHombreBebe);
        serviciosIniciales.add(escaleraMolinete);
        serviciosIniciales.add(escaleraMolineteAncho);
        serviciosIniciales.add(banioSinGeneroAscensor);
        serviciosIniciales.add(banioMujerDiscapRampa);
        serviciosIniciales.add(escMecanicaMolinete);
    }
}
