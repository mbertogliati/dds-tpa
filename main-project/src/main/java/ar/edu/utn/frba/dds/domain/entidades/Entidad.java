package ar.edu.utn.frba.dds.domain.entidades;

import ar.edu.utn.frba.dds.domain.utilidades.Ubicacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "entidades")
@Setter
@Getter
public class Entidad {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @OneToMany(mappedBy = "entidad", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
  private List<Establecimiento> establecimientos = new ArrayList<Establecimiento>();

  @Column(name = "nombre")
  private String nombre;

  @ManyToOne
  @JoinColumn(name = "denominacion_id", referencedColumnName = "id")
  private Denominacion denominacion;

  @Embedded
  private Ubicacion ubicacion;

  public Entidad(){}

  public Entidad(String nombre, String denominacion) {
    this.nombre = nombre;
    this.denominacion = new Denominacion(denominacion);
  }

  public Entidad(String nombre, Denominacion denominacion) {
    this.nombre = nombre;
    this.denominacion = denominacion;
  }

  public List<Ubicacion> getUbicaciones(){
    return this.establecimientos.stream().map(e -> e.getUbicacion()).toList();
  }

  public void agregarEstablecimiento(Establecimiento establecimiento){
    establecimiento.setEntidad(this);
    this.establecimientos.add(establecimiento);
  }

  public void eliminarEstablecimiento(Establecimiento establecimiento){
    this.eliminarEstablecimientoPorID(establecimiento.getId());
  }

  private void eliminarEstablecimientoPorID(int id){
    this.establecimientos.stream().filter(establecimiento -> establecimiento.getId() == id).toList().forEach(est -> establecimientos.remove(est));
  }

  public void agregarEstablecimiento(Establecimiento establecimiento, int posicion){
    this.establecimientos.add(posicion, establecimiento);
  }

  public Establecimiento getPrimero(){
    return establecimientos.get(0);
  }

  public Establecimiento getUltimo(){
    return establecimientos.get(establecimientos.size() - 1);
  }

  private List<Establecimiento> getEstablecimientosEnLocacion(Ubicacion ubicacion) {
    List<Establecimiento> listaRetornar = new ArrayList<>();

    if(ubicacion.getMetadato().getProvincia() != null){
      listaRetornar.addAll(this.establecimientos.stream().filter(est -> est.getUbicacion().getMetadato().getProvincia().getId() == ubicacion.getMetadato().getProvincia().getId()).toList());
    }

    if(ubicacion.getMetadato().getLocalidad() != null){
      listaRetornar.addAll(this.establecimientos.stream().filter(est -> est.getUbicacion().getMetadato().getLocalidad().getId() == ubicacion.getMetadato().getLocalidad().getId()).toList());
    }

    return listaRetornar;
  }
}
