package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UsuarioDTOInput;
import dto.UsuarioDTOOutput;
import service.UsuarioService;

import java.util.List;

import static spark.Spark.*;

public class UsuarioController {
    
    private UsuarioService usuarioService = new UsuarioService();
    private ObjectMapper objMapper = new ObjectMapper();

    public UsuarioController() {
        this.usuarioService = new UsuarioService();
        this.objMapper = new ObjectMapper();

        respostasRequisicoes();
    }

    public UsuarioController(UsuarioService usuarioService, ObjectMapper objMapper) {
        this.usuarioService = usuarioService;
        this.objMapper = objMapper;
    }

    public void respostasRequisicoes() {

        get("/usuarios", (request, response) -> {
            List<UsuarioDTOOutput> listaUsuarios = usuarioService.listarUsuarios();
            response.type("application/json");
            response.status(200);
            String json = objMapper.writeValueAsString(listaUsuarios);

            return json;
        });

        get("/usuarios/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            UsuarioDTOOutput usuarioDTOOutput = usuarioService.buscarUsuario(id);
            response.type("application/json");
            response.status(200);
            String json = objMapper.writeValueAsString(usuarioDTOOutput);

            return json;
        });

        delete("/usuarios/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            usuarioService.excluirUsuario(id);
            response.status(204);

            return "";
        });

        post("/usuarios", (request, response) -> {
            UsuarioDTOInput usuarioDTOInput = objMapper.readValue(request.body(), UsuarioDTOInput.class);
            usuarioService.inserirUsuario(usuarioDTOInput);
            response.type("application/json");
            response.status(201);

            return "Usuário criado com sucesso";
        });

        put("/usuarios/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            UsuarioDTOInput usuarioDTOInput = objMapper.readValue(request.body(), UsuarioDTOInput.class);
            usuarioDTOInput.setId(id);
            usuarioService.alterarUsuario(usuarioDTOInput);
            response.status(200);

            return "Usuário atualizado com sucesso";
        });
    }
}
