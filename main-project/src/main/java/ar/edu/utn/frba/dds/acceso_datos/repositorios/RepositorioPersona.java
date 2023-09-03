package ar.edu.utn.frba.dds.acceso_datos.repositorios;

import ar.edu.utn.frba.dds.domain.comunidades.Persona;
import java.util.List;

public interface RepositorioPersona {
  Persona obtenerPersonaPorIdUsuario(int idUsuario);
  void agregarPersona(Persona unaPersona);
  void modificarPersona(int idUsuario, Persona unaPersonaModificada);
  void eliminarPersona(int idUsuario);
}
