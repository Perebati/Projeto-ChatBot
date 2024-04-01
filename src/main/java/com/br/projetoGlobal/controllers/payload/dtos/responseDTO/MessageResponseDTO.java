package com.br.projetoGlobal.controllers.payload.dtos.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageResponseDTO {
    private String message;
}
