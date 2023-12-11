package service;

import dto.UsuarioDTOInput;
import dto.UsuarioDTOOutput;
import model.Usuario;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class UsuarioService {

    private List<Usuario> listaUsuarios;
    private ModelMapper modelMapper = new ModelMapper();

    public UsuarioService() {
        this.listaUsuarios = new ArrayList<>();
    }

    public List<UsuarioDTOOutput> listarUsuarios() {
        List<UsuarioDTOOutput> listaUsuariosDTO = new ArrayList<>();

        for (Usuario usuario : listaUsuarios) {
            UsuarioDTOOutput usuarioDTO = modelMapper.map(usuario, UsuarioDTOOutput.class);
            listaUsuariosDTO.add(usuarioDTO);
        }
        return listaUsuariosDTO;
    }

    public void inserirUsuario(UsuarioDTOInput usuarioDTOInput) {
        Usuario novoUsuario = modelMapper.map(usuarioDTOInput, Usuario.class);
        listaUsuarios.add(novoUsuario);
    }

    public void alterarUsuario(UsuarioDTOInput usuarioDTOInput) {
        Optional<Usuario> usuarioExistente = listaUsuarios.stream()
                .filter(u -> u.getId() == usuarioDTOInput.getId())
                .findFirst();

        usuarioExistente.ifPresent(usuario -> {
            modelMapper.map(usuarioDTOInput, usuario);
        });
    }

    public UsuarioDTOOutput buscarUsuario(int id) {
        Optional<Usuario> usuarioEncontrado = listaUsuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst();

        return usuarioEncontrado.map(usuario -> modelMapper.map(usuario, UsuarioDTOOutput.class))
                .orElse(null);
    }

    public void excluirUsuario(int id) {

        Iterator<Usuario> iterator = listaUsuarios.iterator();

        while(iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if(usuario.getId() == id) {
                iterator.remove();
                break;
            }
        }
    }
}
