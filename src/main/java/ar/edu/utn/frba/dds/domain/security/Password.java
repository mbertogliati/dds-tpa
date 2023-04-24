package ar.edu.utn.frba.dds.domain.security;

public class Password {
  private String password;
  private byte[] salt;
  private int costo = 1;

  public Password(String pass,String usuario){
    if(Validador.esValida(pass,usuario)){
      this.cambiarPassword(pass);
    }
    else{
      throw new RuntimeException("La contraseña no es valida");
    }
  }

  public boolean esValida(String pass){
    if( Validador.getHash(pass,this.salt,this.costo).equals(this.password)){
      costo = 1;
      return true;
    }
    else{
      costo *= 2;
      return false;
    }
  }
  private void cambiarPassword(String nuevaPassword){
    this.salt = Validador.genSalt();
    this.password = Validador.getHash(nuevaPassword,this.salt,this.costo);
  }

  public void cambiarPassword(String viejaPassword, String nuevaPassword,String usuario){
    if (Validador.esValida(nuevaPassword,usuario) && this.esValida(viejaPassword)) {
      cambiarPassword(nuevaPassword);
    }
    else{
      throw new RuntimeException("La contraseña ingresada no coincide con la actual");
    }
  }



}
