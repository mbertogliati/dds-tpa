package ar.edu.utn.frba.dds.modelos.base;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
public abstract class ModelBase {
  @Setter
  @GeneratedValue
  @Id
  private int id;

  @Column(name = "activo")
  private Boolean activo = true;

  @Column(name = "fechaAlta")
  private LocalDateTime fechaAlta = LocalDateTime.now();

  @Column(name = "fechaBaja")
  private LocalDateTime fechaBaja;

  public ModelBase(){
  }

  public void setActivo(Boolean valor){
    this.activo = valor;
    if(this.activo){
      this.fechaAlta = LocalDateTime.now();
    }
    else{
      this.fechaBaja = LocalDateTime.now();
    }
  }
}
