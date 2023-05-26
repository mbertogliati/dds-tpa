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
    byte[] saltBytes = new byte[0]; //TODO: LA salt no se implementa porque no sabemos como hacer que sea especifica por contraseña
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
