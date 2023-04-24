package ar.edu.utn.frba.dds.domain.security;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.text.Normalizer;
import java.util.HexFormat;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


public class Validador {

  private static final SecureRandom generadorRandom = new SecureRandom();
  private static final int largoSalt = 20;
  private static final int largoHash = 512;

  private static final int cantMinimaCaracteres = 8;

    private static final ArrayList<String> peoresPasswords = obtenerPeoresPasswords();
    private Validador() {
    }

   public static ArrayList<String> getPeoresPasswords(){
    return peoresPasswords;
   }

    public static boolean esValida(String password,String usuario) {
      return cantidadCorrectaDeCaracteres(password) && contieneMinuscula(password) && contieneMayuscula(password) && contieneNumero(password) && contieneSimbolo(password) && noEstaEnPeoresPasswords(password) && noEsIgualAUsuario(password,usuario);

    }

  /**
   * Obtiene la lista de las 10000 peores contraseñas desde un URL
   */
  private static ArrayList<String> obtenerPeoresPasswords(){
    ArrayList<String> peores10000Passwords = new ArrayList<>();
    try{
      /*Necesito traerme las contraseñas desde esta URL*/
      URL url = new URL("https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-10000.txt");
      HttpURLConnection conexionHTTP= (HttpURLConnection) url.openConnection();

      if(conexionHTTP.getResponseCode() == HttpURLConnection.HTTP_OK){

        InputStream inputStream = conexionHTTP.getInputStream(); //El archivo que recibe lo mapea al inputStream
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); //El Reader lee desde el input stream

        String linea = bufferedReader.readLine();//Lee de a una linea a la vez

        while(linea != null){
          peores10000Passwords.add(linea);
          linea = bufferedReader.readLine();
        }
      }
    }
    catch(IOException e){
      throw new RuntimeException( e );
    }
    return peores10000Passwords;
  }

  public static String getHash(String password,byte[] salt,int costo) {
    String password_normalizada = Normalizer.normalize(password, Normalizer.Form.NFKC);
    try {
      SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
      PBEKeySpec spec = new PBEKeySpec( password_normalizada.toCharArray(), salt, costo, largoHash );
      SecretKey key = skf.generateSecret( spec );
      return  HexFormat.of().formatHex(key.getEncoded( ));
    } catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
      throw new RuntimeException( e );
    }
  }

  public static byte[] genSalt(){

    byte[] salt = new byte[largoSalt];
    generadorRandom.nextBytes(salt);
    return salt;
  }

  private static boolean cantidadCorrectaDeCaracteres(String password){
    return password.length() >= cantMinimaCaracteres;
  }
  private static boolean contieneMinuscula(String password){
    return password.matches(".*[a-z].*");
  }

  private static boolean contieneMayuscula(String password){
    return password.matches(".*[A-Z].*");
  }

  private static boolean contieneSimbolo(String password){
    return password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");
  }

  private static boolean noEstaEnPeoresPasswords(String password){
    return !peoresPasswords.contains(password);
  }

  private static boolean noEsIgualAUsuario(String password, String usuario){
    return !password.equals(usuario);
  }
   private static boolean contieneNumero(String password) {
     return password.matches(".*[0-9].*");
   }
}
