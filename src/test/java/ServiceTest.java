import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UsuarioDTOInput;
import dto.UsuarioDTOOutput;
import org.junit.Test;
import service.UsuarioService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ServiceTest {

    @Test
    public void testeInsercao() {
        UsuarioService usuarioService = new UsuarioService();
        UsuarioDTOInput usuarioDTOInput = new UsuarioDTOInput();
        usuarioDTOInput.setNome("Usuario Teste");
        usuarioDTOInput.setSenha("Teste123");

        usuarioService.inserirUsuario(usuarioDTOInput);
        List<UsuarioDTOOutput> listaUsuarios = usuarioService.listarUsuarios();
        assertEquals(1, listaUsuarios.size());
    }

    @Test
    public void testeValidacao() throws Exception {

        URL urlApiPublica = new URL("https://randomuser.me/api/");
        HttpURLConnection connection = (HttpURLConnection) urlApiPublica.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        assertEquals(200, responseCode);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String jsonResponse = reader.readLine();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        String nomeUsuario = jsonNode.at("/results/0/name/first").asText();
        String senhaUsuario = "senhaAleatoria";

        UsuarioDTOInput usuarioDTOInput = new UsuarioDTOInput();
        usuarioDTOInput.setNome(nomeUsuario);
        usuarioDTOInput.setSenha(senhaUsuario);

        URL urlSuaApi = new URL("http://localhost:4567/usuarios");
        HttpURLConnection connectionSuaApi = (HttpURLConnection) urlSuaApi.openConnection();
        connectionSuaApi.setRequestMethod("POST");
        connectionSuaApi.setRequestProperty("Content-Type", "application/json");
        connectionSuaApi.setDoOutput(true);

        String jsonInputString = objectMapper.writeValueAsString(usuarioDTOInput);

        connectionSuaApi.getOutputStream().write(jsonInputString.getBytes());

        int responseCodeSuaApi = connectionSuaApi.getResponseCode();
        assertEquals(201, responseCodeSuaApi);
    }
}
