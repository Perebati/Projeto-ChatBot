package com.br.projetoGlobal.controllers.payload.dtos.responseDTO;

import com.br.projetoGlobal.models.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class UsuarioResponseDTO {
    private String email;
    private String name;

    public UsuarioResponseDTO(Usuario user){
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
