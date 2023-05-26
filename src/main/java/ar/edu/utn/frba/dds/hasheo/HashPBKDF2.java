package ar.edu.utn.frba.dds.hasheo;

import java.util.ArrayList;
import java.util.List;

public class HashPBKDF2 implements EstrategiaHash {
  private List<Byte> salt;
  private int costo;

  @Override
  public String hashear(String password) {
    //TODO: IMPLEMENTAR HASHEO
    return "PASSWORDHASHEADA";
  }

  private List<Byte> genSalt(){
    //TODO: IMPLEMENTAR SALT
    return new ArrayList<Byte>();
  }

  private int genCosto(){
    //TODO: IMPLEMENTAR COSTO
    return 0;
  }
}
