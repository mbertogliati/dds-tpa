package ar.edu.utn.frba.dds.modelos.entidades;

import ar.edu.utn.frba.dds.modelos.servicios.Etiqueta;
import ar.edu.utn.frba.dds.modelos.servicios.Servicio;
import ar.edu.utn.frba.dds.modelos.servicios.TipoEtiquetas;
import ar.edu.utn.frba.dds.modelos.utilidades.Ubicacion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    //private ControlEntidades organismoControl1;
    //private ControlEntidades organismoControl2;
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
    @DisplayName("Se pueden generar establecimientos asociados a una entidad")
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
        sucursal1.agregarServicio(banioHombreBebe);
        sucursal1.agregarServicio(banioMujerDiscapRampa);

        Assertions.assertEquals(2,sucursal1.getServiciosPrestados().size());
    }

    @Test
    @DisplayName("Se pueden eliminar servicios de un establecimiento")
    public void eliminarServicios(){
        sucursal1.agregarServicio(banioHombreBebe);
        sucursal1.agregarServicio(banioMujerDiscapRampa);
        sucursal1.agregarServicio(escaleraMolinete);

        sucursal1.eliminarServicio(banioMujerDiscapRampa);

        Assertions.assertEquals(2,sucursal1.getServiciosPrestados().size());
    }


    @Test
    @DisplayName("Se puede asignar una ubicacion a los establecimientos de una entidad y consultar sus ubicaciones")
    public void asignarUbicacionAEntidad() {
        //arrange
        Entidad entidad = new Entidad("entidad prestadora", new Denominacion("entidad"));

        Establecimiento establecimiento1 = new Establecimiento("estación 1", new Denominacion("estación"));
        establecimiento1.setUbicacion(new Ubicacion((float)-34.77995323941093, (float)-58.39850705828568));
        entidad.agregarEstablecimiento(establecimiento1);

        Establecimiento establecimiento2 = new Establecimiento("estación 2", new Denominacion("estación"));
        establecimiento2.setUbicacion(new Ubicacion((float)-34.60364737571995, (float)-58.38158957545822));
        entidad.agregarEstablecimiento(establecimiento2);

        Establecimiento establecimiento3 = new Establecimiento("estación 3", new Denominacion("estación"));
        establecimiento3.setUbicacion(new Ubicacion((float)-34.568478432086074, (float)-58.47965135917718));
        entidad.agregarEstablecimiento(establecimiento3);

        //act
        List<Ubicacion> ubicacionesDeEntidad = entidad.getUbicaciones();

        //assert
        Assertions.assertEquals(3, ubicacionesDeEntidad.size());
    }

    private void iniciarEstablecimientos() {
        this.estacion1 = new Establecimiento("estación 1", new Denominacion("Estacion"));
        estacion1.setId(0);
        this.estacion1.setUbicacion(new Ubicacion((float) -30.150, (float) -30.150));

        this.estacion2 = new Establecimiento("estación 2", new Denominacion("Estacion"));
        estacion2.setId(1);
        this.estacion2.setUbicacion(new Ubicacion((float) -31.150, (float) -31.150));

        this.estacion3 = new Establecimiento("estación 3", new Denominacion("Estacion"));
        estacion3.setId(2);
        estacion3.setUbicacion(new Ubicacion((float) -32.150, (float) -32.150));

        this.sucursal1 = new Establecimiento("sucursal1", new Denominacion("Sucursal"));
        sucursal1.setId(3);
        sucursal1.setUbicacion(new Ubicacion((float) -40.000, (float) -40.000));

        this.sucursal2 = new Establecimiento("sucursal2", new Denominacion("Sucursal"));
        sucursal2.setId(4);
        sucursal2.setUbicacion(new Ubicacion((float) -41.000, (float) -41.000));
    }

    private void iniciarEntidades(){
        this.entidad1 = new Entidad("Primera entidad de banco", new Denominacion("banco"));
        entidad1.setId(0);
        this.entidad2 = new Entidad("Segunda entidad de banco", new Denominacion("banco"));
        entidad2.setId(1);
        this.entidad3 = new Entidad("Primera entidad de transporte", new Denominacion("transporte"));
        entidad3.setId(2);
        //this.organismoControl1 = new ControlEntidades("Primer organismo de control transporte", new Denominacion("controlTransporte"));
        //organismoControl1.setId(3);
        //this.organismoControl2 = new ControlEntidades("Primer organismo de control banco", new Denominacion("controlBanco"));
        //organismoControl2.setId(4);
    }

    private void iniciarServicios(){

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
