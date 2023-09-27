package ar.edu.utn.frba.dds.modelos.validacion;

import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidadorUsuarioConcreto implements ValidadorUsuario {
  private List<EstrategiaValidacion> estrategiasValidacion = new ArrayList<EstrategiaValidacion>();

  @Override
  public boolean validar(Usuario usuario, String password){
    return this.estrategiasValidacion.stream().allMatch(estrategia -> estrategia.validar(password)) && usuario.getUsername() != password;
  }

  public void agregarEstrategia(EstrategiaValidacion estrategiaValidacion){
    this.estrategiasValidacion.add(estrategiaValidacion);
  }

  public void agregarEstrategias(EstrategiaValidacion ... estrategiasValidacion){
    this.estrategiasValidacion.addAll(Arrays.stream(estrategiasValidacion).toList());
  }

}
