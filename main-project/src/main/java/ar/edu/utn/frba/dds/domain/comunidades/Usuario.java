package ar.edu.utn.frba.dds.domain.comunidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.mapping.Join;

@Entity
@Table(name = "usuarios")
@Getter @Setter
public class Usuario {
  @Id
  @GeneratedValue
  private int id;

  @Column(name = "usuario")
  private String username;

  @Column(name = "clave")
  private String password;

  @ManyToOne
  @JoinColumn(name = "rolPlataforma_id", referencedColumnName = "id")
  private Rol rolPlataforma;

  public Usuario(){

  }

  public Usuario(String username, String password){
    this.username = username;
    this.password = password;
  }
}
