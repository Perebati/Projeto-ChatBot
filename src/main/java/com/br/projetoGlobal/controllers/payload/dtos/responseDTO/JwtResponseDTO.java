package com.br.projetoGlobal.controllers.payload.dtos.responseDTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class JwtResponseDTO {
    private String token;


    public JwtResponseDTO(String accessToken) {
        this.token = accessToken;
    }
}
