package ar.edu.utn.frba.dds.controladores.utilidades;

import ar.edu.utn.frba.dds.acceso_datos.repositorios.RepositorioPersona;
import ar.edu.utn.frba.dds.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.domain.comunidades.Membresia;
import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import ar.edu.utn.frba.dds.domain.comunidades.Usuario;
import ar.edu.utn.frba.dds.domain.comunidades.notificacionesPersona.EstrategiaMomentoNotificacion;
import ar.edu.utn.frba.dds.domain.entidades.Establecimiento;
import ar.edu.utn.frba.dds.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.domain.servicios.Etiqueta;
import ar.edu.utn.frba.dds.domain.servicios.Servicio;
import ar.edu.utn.frba.dds.domain.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.domain.utilidades.AdapterCalculadoraDistancia;
import ar.edu.utn.frba.dds.domain.utilidades.Coordenada;
import ar.edu.utn.frba.dds.domain.utilidades.EvaluadorSolicitudRevision;
import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
import ar.edu.utn.frba.dds.notificaciones.Notificador;
import ar.edu.utn.frba.dds.notificaciones.StrategyNotificacion;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;

import static java.lang.System.out;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ReceptorCoordenadaPersonaTests {
  private ReceptorCoordenadaPersona receptorCoordenadaPersona;
  private EvaluadorSolicitudRevision evaluadorSolicitudRevision;
  private AdapterCalculadoraDistancia adapterCalculadoraDistanciaMock;
  private RepositorioPersona repositorioPersonaMock;
  private EstrategiaMomentoNotificacion estrategiaMomentoNotificacionMock; //en el momento o despues
  private StrategyNotificacion strategyNotificacionEmailMock; //Email
  private StrategyNotificacion strategyNotificacionWhatsappMock; //Whatsapp
  private Persona persona1;
  private Persona persona2;
  private Ubicacion ubicacionEstablecimiento1;

  @BeforeEach
  public void init() {

    this.adapterCalculadoraDistanciaMock = mock(AdapterCalculadoraDistancia.class);
    this.repositorioPersonaMock = mock(RepositorioPersona.class);
    this.estrategiaMomentoNotificacionMock = mock(EstrategiaMomentoNotificacion.class);
    this.strategyNotificacionEmailMock = mock(StrategyNotificacion.class);
    this.strategyNotificacionWhatsappMock = mock(StrategyNotificacion.class);

    this.evaluadorSolicitudRevision = new EvaluadorSolicitudRevision(adapterCalculadoraDistanciaMock);
    this.receptorCoordenadaPersona = new ReceptorCoordenadaPersona(this.repositorioPersonaMock, this.evaluadorSolicitudRevision);

    Notificador.getEstrategias().put("email", this.strategyNotificacionEmailMock);
    Notificador.getEstrategias().put("whatsapp", this.strategyNotificacionWhatsappMock);

    this.inicializarDatosMock();
  }

  private void inicializarDatosMock() {

    Usuario usuario1 = new Usuario("Carlos-1", "PasswordTest");
    usuario1.setId(1);

    Usuario usuario2 = new Usuario("Andrea-2", "PasswordTest");
    usuario2.setId(2);

    this.persona1 = new Persona("Carlos", "Carlini");
    persona1.setUsuario(usuario1);
    persona1.setEstrategiaMomentoNotificacion(this.estrategiaMomentoNotificacionMock);

    this.persona2 = new Persona("Andrea", "Andreini");
    persona2.setUsuario(usuario2);
    persona2.setEstrategiaMomentoNotificacion(this.estrategiaMomentoNotificacionMock);

    Comunidad comunidad1 = new Comunidad("Comunidad Test");
    Membresia membresia1 = new Membresia(comunidad1, persona1);
    persona1.agregarMembresia(membresia1);;
    persona1.setMetodoNotificacion("email");

    Membresia membresia2 = new Membresia(comunidad1, persona2);
    persona2.agregarMembresia(membresia2);
    persona2.setMetodoNotificacion("whatsapp");

    Incidente incidente1 = new Incidente(persona2);
    comunidad1.agregarIncidente(incidente1);

    this.ubicacionEstablecimiento1 = new Ubicacion(1.0f, 1.0f);

    Establecimiento establecimiento1 = new Establecimiento("Establecimiento Test", "Test");
    establecimiento1.setUbicacion(ubicacionEstablecimiento1);

    Servicio servicio1 = new Servicio();
    servicio1.agregarEtiqueta(new Etiqueta("texto", "Escalera"));
    servicio1.agregarEtiqueta(new Etiqueta("texto", "Mecanica"));
    ServicioPrestado servicioPrestado1 = new ServicioPrestado(servicio1);
    servicioPrestado1.setEstablecimiento(establecimiento1);
    incidente1.getServiciosAfectados().add(servicioPrestado1);
  }

  @Test
  @DisplayName("Se puede recibir la coordenada de una persona , actualizarla y detectar incidentes cercanos")
  public void recibirCoordenadaPersona(){
    //arrange
    float latitud = 1.0f;
    float longitud = 2.0f;
    int id = 1;

    when(this.repositorioPersonaMock.obtenerPersonaPorIdUsuario(anyInt()))
    .thenAnswer(x -> {
      out.println(String.format("Buscando persona con id %s.", Arrays.stream(x.getArguments()).findFirst().get()));
      out.println(String.format("Persona encontrada: id = %s, nombre = %s.", persona1.getUsuario().getId(), persona1.getNombre()));
      return persona1;
    });

    doAnswer( x -> {
      out.println(String.format("Modificando persona con id %s.", Arrays.stream(x.getArguments()).findFirst().get()));
      out.println(String.format("Persona modificada: id = %s, nombre = %s.", persona1.getUsuario().getId(), persona1.getNombre()));
      Ubicacion ubicacionActual = persona1.getUbicacionActual();
      out.println(String.format("Nuevas coordenadas: latitud = %s, longitud = %s", ubicacionActual.getCoordenada().getLatitud(), ubicacionActual.getCoordenada().getLongitud()));
      return null;
    }).when(this.repositorioPersonaMock).modificarPersona(anyInt(), any(Persona.class));

    when(this.adapterCalculadoraDistanciaMock.distanciaEntre(any(Coordenada.class), any(Coordenada.class)))
        .thenReturn(1.0d);

    doAnswer((x) -> {
      out.println(String.format("Enviando notificacion: %s", x.getArgument(0).toString()));
      return null;
    }).when(this.strategyNotificacionEmailMock).enviarNotificacion(anyString(), any(Persona.class));

    //act
    this.receptorCoordenadaPersona.recibirCoordenada(latitud, longitud, id);

    //assert

    //se llama a obtener persona por id
    verify(this.repositorioPersonaMock).obtenerPersonaPorIdUsuario(id);

    //se modifica la persona por id
    verify(this.repositorioPersonaMock).modificarPersona(id, this.persona1);

    //se llama a la calculadora de distancias con las coordenadas correctas
    verify(this.adapterCalculadoraDistanciaMock).distanciaEntre(
        this.ubicacionEstablecimiento1.getCoordenada(),
        this.persona1.getUbicacionActual().getCoordenada()
    );

    ArgumentCaptor<String> captorMensaje = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Persona> captorPersona = ArgumentCaptor.forClass(Persona.class);

    //se llama al notificador correctamente
    verify(this.strategyNotificacionEmailMock).enviarNotificacion(
        captorMensaje.capture(),
        captorPersona.capture()
    );
    Assert.notNull(captorMensaje.getValue());
    Assert.notNull(captorPersona.getValue());
  }
}
