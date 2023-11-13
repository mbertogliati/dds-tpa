package ar.edu.utn.frba.dds.controllers.generales.incidentes;

import ar.edu.utn.frba.dds.controllers.utils.MensajeVista;
import ar.edu.utn.frba.dds.modelos.comunidades.Comunidad;
import ar.edu.utn.frba.dds.modelos.comunidades.Usuario;
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
    public static String url = "https://dds-g19-laravel.onrender.com/incidentes";

    public static URI obtenerURI() {
        String envUrl = System.getenv("LISTADO_INCIDENTES_URL");
        if(envUrl != null && !envUrl.isEmpty()) url = envUrl;
        return URI.create(url);
    }

    public static String obtenerHTMLListadoIncidentes(Map<String,Object> model, String estado) throws URISyntaxException, IOException, InterruptedException {
        MensajeVista mensajeVista = (MensajeVista) model.get("msg");
        Usuario usuario = (Usuario) model.get("userActual");
        Comunidad comunidad = (Comunidad) model.get("comunidad");
        var values = new HashMap<String, String>() {{
            if(mensajeVista != null) {
                put("tipo", mensajeVista.getTipo());
                put("msg", mensajeVista.getTexto());
            }
            if(estado != null){
                put("estado", estado);
            }
            put("idComunidad", String.valueOf(comunidad.getId()));
            put("idUsuario",String.valueOf(usuario.getId()));
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(obtenerURI())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
