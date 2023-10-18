package ar.edu.utn.frba.dds.controllers.utils;

import ar.edu.utn.frba.dds.modelos.entidades.EntidadPrestadora;
import ar.edu.utn.frba.dds.modelos.entidades.OrganismoControl;
import java.util.ArrayList;
import java.util.List;

public class FiltradorEntidades {
  public static List<OrganismoControl> organismosDeControl(List<OrganismoControl> organismosControl){
    List<OrganismoControl> nuevoListado = new ArrayList<>();
    for (OrganismoControl organismoControl : organismosControl){
      OrganismoControl organismoNuevo = new OrganismoControl(organismoControl.getId(), organismoControl.getNombre(), organismoControl.getEntidadesPrestadoras().stream().filter(e -> e.getActivo()).toList());
      nuevoListado.add(organismoNuevo);
    }
    return nuevoListado;
  }

  public static List<EntidadPrestadora> entidadesPrestadoras(List<EntidadPrestadora> entidadesPrestadoras){
    List<EntidadPrestadora> nuevoListado = new ArrayList<>();
    for (EntidadPrestadora entidadPrestadora : entidadesPrestadoras){
      EntidadPrestadora entidadNueva = new EntidadPrestadora(entidadPrestadora.getId(), entidadPrestadora.getNombre(), entidadPrestadora.getEntidades().stream().filter(e -> e.getActivo()).toList());
      nuevoListado.add(entidadNueva);
    }
    return nuevoListado;
  }
}
