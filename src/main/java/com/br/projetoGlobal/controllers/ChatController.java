package com.br.projetoGlobal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetoGlobal.controllers.payload.dtos.requestDTO.ChatPromptDTO;
import com.br.projetoGlobal.service.ChatService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;
    
    @PostMapping("/prompt")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String chatCommunication(@RequestBody ChatPromptDTO chatPromptDTO) throws Exception {
        return this.chatService.chatMessaging(chatPromptDTO.getMessage());
    }
}
