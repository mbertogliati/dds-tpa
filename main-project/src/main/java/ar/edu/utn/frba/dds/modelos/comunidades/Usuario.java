package ar.edu.utn.frba.dds.modelos.comunidades;

import ar.edu.utn.frba.dds.modelos.base.ModelBase;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "usuarios")
@Getter @Setter

public class Usuario extends ModelBase {
  @Column(name = "usuario")
  private String username;

  @Column(name = "clave")
  private String password;

  @OneToOne
  @JoinColumn(name = "persona_id", referencedColumnName = "id")
  @Cascade(CascadeType.ALL)
  private Persona personaAsociada;

  @ManyToOne
  @JoinColumn(name = "rolPlataforma_id", referencedColumnName = "id")
  private Rol rolPlataforma;

  public Usuario(){}

  public Usuario(String username, String password){
    this.username = username;
    this.password = password;
  }

  @Override
  public void setActivo(Boolean valor){
    super.setActivo(valor);
    this.getPersonaAsociada().setActivo(valor);
  }
}
