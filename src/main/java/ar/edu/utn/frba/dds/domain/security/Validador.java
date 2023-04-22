package ar.edu.utn.frba.dds.domain.security;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Validador {

    private static Validador instance;
    private String[] peoresPasswords;
    private Validador() throws MalformedURLException {
      URL url = new URL("https://raw.githubusercontent.com/danielmiessler/SecLists/master/Passwords/Common-Credentials/10-million-password-list-top-10000.txt");
    }

    public static Validador obtener() throws MalformedURLException {
      return Validador.instance == null ? new Validador() : instance;
    }

    public boolean validar(String password) {
      return password.length() >= 8;
    }



    private boolean esValida(String password) {

      return password.length() >= 8;
    }
}
