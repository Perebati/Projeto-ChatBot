package com.br.projetoGlobal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.projetoGlobal.models.Usuario;
import com.br.projetoGlobal.repository.UsuarioRepository;

@Service
public class ChatService {
    @Autowired
    UsuarioRepository usuarioRepository;

    // Estou tomando um exception no microserviço do chatBot, descobri que tem algo a ver com timeout, mesmo o console do python acusando um erro do chatGPT. 
    // Por enquanto o metodo fica assim até eu arrumar.  
    public String chatMessaging(String message, String username){
        Optional<Usuario> user = this.usuarioRepository.findByUsername(username);

        try {
            // Aguarda por 5 segundos 
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       return "Essa seria a resposta"; 
    }
}
