package ar.edu.utn.frba.dds.validacion;

import ar.edu.utn.frba.dds.domain.comunidades.Usuario;
import java.util.ArrayList;
import java.util.List;

public class ValidadorUsuario {
  private List<EstrategiaValidacion> estrategiasValidacion = new ArrayList<EstrategiaValidacion>();

  public boolean validar(Usuario usuario, String password){
    return this.estrategiasValidacion.stream().allMatch(estrategia -> estrategia.validar(password)) && usuario.getUsername() != password;
  }

  public void agregarEstrategia(EstrategiaValidacion estrategiaValidacion){
    this.estrategiasValidacion.add(estrategiaValidacion);
  }

  public void eliminarEstrategia(EstrategiaValidacion estrategiaValidacion){
    this.estrategiasValidacion.remove(estrategiaValidacion);
  }

}
