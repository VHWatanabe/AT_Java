package dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTOOutput {

    private int id;
    private String nome;

    @JsonIgnore
    private String senha;
}
