package ar.edu.utn.frba.dds.domain.security;

public class Password {
  private String password;
  private byte[] salt;

  public Password(String pass,String usuario){
    if(Validador.esValida(pass,usuario)){
      this.cambiarPassword(pass);
    }
    else{
      throw new RuntimeException("La contraseña no es valida");
    }
  }
  public boolean esCorrecta(String pass){

    return Validador.getHash(pass,this.salt).equals(this.password);

  }
  private void cambiarPassword(String nuevaPassword){
    this.salt = Validador.genSalt();
    this.password = Validador.getHash(nuevaPassword,this.salt);
  }

  public void cambiarPassword(String viejaPassword, String nuevaPassword,String usuario){
    if (Validador.esValida(nuevaPassword,usuario) && this.esCorrecta(viejaPassword)) {
      cambiarPassword(nuevaPassword);
    }
    else{
      throw new RuntimeException("La contraseña ingresada no coincide con la actual");
    }
  }



}
