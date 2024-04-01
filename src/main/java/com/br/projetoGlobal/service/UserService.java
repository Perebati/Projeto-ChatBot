package com.br.projetoGlobal.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.projetoGlobal.controllers.payload.dtos.requestDTO.UsuarioNameEditDTO;
import com.br.projetoGlobal.controllers.payload.dtos.requestDTO.UsuarioPasswordEditDTO;
import com.br.projetoGlobal.controllers.payload.dtos.responseDTO.UsuarioResponseDTO;
import com.br.projetoGlobal.models.Usuario;
import com.br.projetoGlobal.repository.UsuarioRepository;

@Service
public class UserService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder encoder;

    public String getTokenFromUser() throws Exception {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.getPrincipal() != null) {
                return authentication.getName();
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public ResponseEntity<?> updatePassword(UsuarioPasswordEditDTO body, String username) throws Exception {
        try {
            if (Objects.isNull(username)) {
                throw new Exception("Usuário não encontrado!");
            }

            Long userId = this.usuarioRepository.findUsuarioByUsername(username);
            Usuario usuario = this.usuarioRepository.findUsuarioById(userId);

            if (encoder.matches(body.getOldPassword(), usuario.getPassword())) {
                usuario.setPassword(encoder.encode(body.getNewPassword()));

                this.usuarioRepository.save(usuario);

                return ResponseEntity.ok("Senhas trocadas.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("As senhas não coincidem.");
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public UsuarioResponseDTO getUserData(String username) throws Exception {
        try {
            if (Objects.isNull(username)) {
                throw new Exception("Usuário não encontrado!");
            }

            Long userId = this.usuarioRepository.findUsuarioByUsername(username);
            return new UsuarioResponseDTO(this.usuarioRepository.findUsuarioById(userId));

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public UsuarioResponseDTO setUserNome(UsuarioNameEditDTO usuarioNameEditDTO, String username) throws Exception {
            try {
                if (Objects.isNull(username)) {
                    throw new Exception("Usuário não encontrado!");
                }
    
                Long userId = this.usuarioRepository.findUsuarioByUsername(username);
                Usuario user = this.usuarioRepository.findUsuarioById(userId);
                user.setName(usuarioNameEditDTO.getName());

                return new UsuarioResponseDTO(this.usuarioRepository.save(user)); 
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
}
