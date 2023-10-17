package ar.edu.utn.frba.dds.modelos.fusion_organizacion;

import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.comunidades.Membresia;
import ar.edu.utn.frba.dds.modelos.comunidades.Rol;
import ar.edu.utn.frba.dds.modelos.entidades.Establecimiento;
import ar.edu.utn.frba.dds.modelos.fusion_organizacion.servicio_fusion_g19.Organizacion;
import ar.edu.utn.frba.dds.modelos.servicios.Servicio;
import ar.edu.utn.frba.dds.modelos.servicios.ServicioPrestado;
import ar.edu.utn.frba.dds.repositorios.comunidades.RolRepositorio;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FusionadorComunidades {
  private Rol rolDefaultComunidad;
  private Rol rolAdminComunidad;

  public Comunidad obtenerComunidad(Organizacion organizacion, Comunidad comunidad1, Comunidad comunidad2){
    Comunidad comunidad = new Comunidad();

    comunidad.setNombre("Fusión de " + comunidad1.getNombre() + " y " + comunidad2.getNombre());
    comunidad.setDetalle(comunidad1.getDetalle() + " - " + comunidad2.getDetalle());
    comunidad.setGradoConfianza(organizacion.getGradoConfianza().floatValue());
    comunidad.setActivo(true);

    List<ServicioPrestado> serviciosPrestados = Stream.concat(comunidad1.getServiciosPrestados().stream(),comunidad2.getServiciosPrestados().stream()).toList();
    serviciosPrestados.forEach(sp -> {
      if(comunidad.getServiciosPrestados().stream().noneMatch(s -> s.getId() == sp.getId())){
        comunidad.agregarServicio(sp);
      }
    });


    List<Membresia> membresias = Stream.concat(comunidad1.getMembresias().stream(),comunidad2.getMembresias().stream()).toList();
    for(Long idPersona : organizacion.getMiembros()){
      List<Membresia> membresiasDePersona = membresias.stream().filter(m -> m.getPersona().getId() == idPersona).toList();
      Rol rolPersona;

      if(membresiasDePersona.stream().anyMatch(m -> m.getRolComunidad().getId() == this.rolAdminComunidad.getId())){
        rolPersona = this.rolAdminComunidad;
      }else{
        rolPersona = this.rolDefaultComunidad;
      }

      comunidad.agregarPersona(membresiasDePersona.get(0).getPersona(), rolPersona);
    }

    return comunidad;
  }
}
