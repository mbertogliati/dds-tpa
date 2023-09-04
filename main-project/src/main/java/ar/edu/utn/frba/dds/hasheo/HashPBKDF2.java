package ar.edu.utn.frba.dds.hasheo;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


import java.util.ArrayList;
import java.util.List;

public class HashPBKDF2 implements EstrategiaHash {
  private int costo = 6000;

  public String hashear(String password) {
    byte[] saltBytes = new byte[1]; //LA salt no se implementa porque no sabemos como hacer que sea especifica por contraseña
    KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, costo, 256);
    SecretKeyFactory factory;
    byte[] hash = null;
    try {
      factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
      hash = factory.generateSecret(spec).getEncoded();
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      e.printStackTrace();
    }

    return Base64.getEncoder().encodeToString(hash);
  }
}

/*
package ar.edu.utn.frba.dds.hasheo;


public class HashPBKDF2 implements EstrategiaHash {


  private List<Byte> genSalt() {
    private List<Byte> genSalt() {
  byte[] salt = new byte[0]; // Longitud del salt
  new SecureRandom().nextBytes(salt); // Generar valores aleatorios para el salt

  List<Byte> saltList = new ArrayList<>();
  for (byte b : salt) {
    saltList.add(b);
  }
  return saltList;
}
  }

  private int genCosto() {
  return 10; // Costo arbitrario, ajusta según tus necesidades
}

  private byte[] convertSaltListToByteArray(List<Byte> saltList) {
    byte[] saltArray = new byte[saltList.size()];
    for (int i = 0; i < saltList.size(); i++) {
        saltArray[i] = saltList.get(i);
    }
    return saltArray;
  }
}

* */