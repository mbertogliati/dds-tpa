package ar.edu.utn.frba.dds.domain.comunidades;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MembresiaTests {
    private Persona persona;
    private Persona persona2;
    private Comunidad comunidad1;
    private Membresia membresia;

    @BeforeEach
    public void init() {
        Usuario usuario = new Usuario("username", "clave");
        usuario.setId(0);
        this.persona = new Persona("Nombre", "Apellido");
        persona.setUsuario(usuario);

        Usuario usuario2 = new Usuario("username2", "clave");
        usuario2.setId(1);
        this.persona2 = new Persona("Nombre2", "Apellido2");
        persona2.setUsuario(usuario2);

        this.comunidad1 = new Comunidad("Una comunidad");
        this.membresia = new Membresia(this.comunidad1, this.persona);
    }

    @Test
    @DisplayName("Se pueden generar y agregar membresias")
    public void agregarMembresia() {
        this.persona.agregarMembresia(this.membresia);

        assertTrue(this.persona.getMembresias().contains(this.membresia));
        assertTrue(this.comunidad1.getMembresias().contains(this.membresia));
    }

    @Test
    @DisplayName("Se pueden eliminar membresias desde la persona")
    public void eliminaMembresiaYComunidadRelacionada(){
        this.comunidad1.agregarMembresia(this.membresia);

        this.persona.eliminarMembresia(this.membresia);

        assertFalse(persona.getMembresias().contains(membresia));
        assertFalse(comunidad1.getMembresias().contains(membresia));
    }

    @Test
    @DisplayName("Se pueden eliminar membresias desde la comunidad")
    public void eliminaMembresiaYPersonaRelacionada() {
        this.persona.agregarMembresia(this.membresia);

        this.comunidad1.eliminarMembresia(this.membresia);

        assertFalse(persona.getMembresias().contains(membresia));
        assertFalse(comunidad1.getMembresias().contains(membresia));
    }

    @Test
    @DisplayName("Se pueden eliminar membresias según la comunidad")
    public void eliminarMembresiaPorComunidad() {
        Comunidad comunidad2 = new Comunidad("Segunda comunidad");
        Membresia membresia2 = new Membresia(comunidad2, persona);

        this.persona.agregarMembresia(membresia);
        this.persona.agregarMembresia(membresia2);

        persona.eliminarMembresiaPorComunidad(comunidad1);

        assertFalse(persona.getMembresias().contains(membresia));
        assertFalse(comunidad1.getMembresias().contains(membresia));

        assertTrue(persona.getMembresias().contains(membresia2));
        assertTrue(comunidad2.getMembresias().contains(membresia2));
    }

    @Test
    @DisplayName("Se pueden eliminar membresias según la persona")
    public void eliminarMembresiaPorPersona() {
        Membresia membresia2 = new Membresia(comunidad1, persona2);

        this.persona.agregarMembresia(membresia);
        this.persona2.agregarMembresia(membresia2);

        comunidad1.eliminarMembresiaPorPersona(persona);

        assertFalse(persona.getMembresias().contains(membresia));
        assertFalse(comunidad1.getMembresias().contains(membresia));

        assertTrue(persona2.getMembresias().contains(membresia2));
        assertTrue(comunidad1.getMembresias().contains(membresia2));
    }
}
