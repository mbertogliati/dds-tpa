package ar.edu.utn.frba.dds.controllers.generales.incidentes;

import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class ObtenedorListadoIncidentes {
    public static String ip = "localhost";
    public static String puerto = "8080";
    public static String resource = "incidentes";

    public static String obtenerHTMLListadoIncidentes(Map<String,Object> model) throws URISyntaxException, IOException, InterruptedException {
        MensajeVista mensajeVista = (MensajeVista) model.get("msg");
        Usuario usuario = (Usuario) model.get("userActual");
        var values = new HashMap<String, String>() {{
            put("tipo", mensajeVista.getTipo());
            put("msg", mensajeVista.getTexto());
            put("idUsuario",String.valueOf(usuario.getId()));
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://" +ip+":"+puerto+"/"+resource))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
