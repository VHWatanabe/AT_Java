import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class EndpointTest {

    @Test
    public void testeListagem() throws IOException {
        URL url = new URL("http://127.0.0.1:4657/usuarios");
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");
        ObjectMapper objectMapper = new ObjectMapper();
        int responseCode = conexao.getResponseCode();
        assertEquals(200,responseCode);
    }
}
