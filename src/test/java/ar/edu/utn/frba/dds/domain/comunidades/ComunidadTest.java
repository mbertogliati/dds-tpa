package ar.edu.utn.frba.dds.domain.comunidades;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ComunidadTest {
  private Persona persona1;
  private Persona persona2;
  private Persona persona3;
  private Comunidad comunidad1;
  private Comunidad comunidad2;

  @BeforeEach
  public void init(){
    Usuario usuario1 = new Usuario("username1", "password1");
    usuario1.setId(1);
    this.persona1 = new Persona("Matias", "Cotens", "matias@mail.com", usuario1);

    Usuario usuario2 = new Usuario("username2", "password2");
    usuario2.setId(2);
    this.persona2 = new Persona("Joaquin", "Mennazi", "joaquin@mail.com", usuario2);

    Usuario usuario3 = new Usuario("username3", "password3");
    usuario3.setId(3);
    this.persona3 = new Persona("Mateo", "Bertogliati", "mateo@mail.com", usuario3);

    this.comunidad1 = new Comunidad("Primer comunidad");
    this.comunidad2 = new Comunidad("Segunda comunidad");
  }

  @Test
  @DisplayName("Se pueden agregar personas a una comunidad")
  public void agregarPersonasAUnaComunidad(){
    this.comunidad1.agregarMembresia(new Membresia(this.comunidad1, this.persona1));
    this.comunidad1.agregarMembresia(new Membresia(this.comunidad1, this.persona2));

    Assertions.assertTrue(this.comunidad1.getMembresias().stream().anyMatch(mem -> mem.getPersona() == this.persona1));
    Assertions.assertTrue(this.comunidad1.getMembresias().stream().anyMatch(mem -> mem.getPersona() == this.persona2));
    Assertions.assertFalse(this.comunidad1.getMembresias().stream().anyMatch(mem -> mem.getPersona() == this.persona3));
  }

  @Test
  @DisplayName("Se pueden eliminar personas de una comunidad")
  public void eliminarPersonasDeUnaComunidad(){
    this.comunidad1.agregarMembresia(new Membresia(this.comunidad1, this.persona1));
    this.comunidad1.agregarMembresia(new Membresia(this.comunidad1, this.persona2));

    this.comunidad1.eliminarMembresiaPorPersona(this.persona1);

    Assertions.assertFalse(this.comunidad1.getMembresias().stream().anyMatch(mem -> mem.getPersona() == this.persona1));
    Assertions.assertTrue(this.comunidad1.getMembresias().stream().anyMatch(mem -> mem.getPersona() == this.persona2));
  }
}
