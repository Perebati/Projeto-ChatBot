package com.br.projetoGlobal.service;

import org.springframework.stereotype.Service;

@Service
public class ChatService {

    // Estou tomando um exception no microserviço do chatBot, descobri que tem algo a ver com timeout, mesmo o console do python acusando um erro do chatGPT. 
    // Por enquanto o metodo fica assim até eu arrumar.  
    public String chatMessaging(String message){
        try {
            // Aguarda por 5 segundos 
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       return "Essa seria a resposta"; 
    }
}
