package com.br.projetoGlobal.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.br.projetoGlobal.controllers.payload.dtos.requestDTO.UsuarioNameEditDTO;
import com.br.projetoGlobal.controllers.payload.dtos.responseDTO.UsuarioResponseDTO;
import com.br.projetoGlobal.models.Usuario;
import com.br.projetoGlobal.repository.UsuarioRepository;
import com.br.projetoGlobal.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/usuario")
public class UsuarioController {
    @Autowired
    UserService userService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder encoder;

    /* Password edit */
    @PostMapping("/updatePassword")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updatePassword(@Valid @RequestParam String newPassword) throws Exception {
        try {
            String username = this.userService.getTokenFromUser();
            if (Objects.isNull(username)) {
                throw new Exception("Usuário não encontrado!");
            }

            Long userId = this.usuarioRepository.findUsuarioByUsername(username);
            Usuario usuario = this.usuarioRepository.findUsuarioById(userId);

            usuario.setPassword(encoder.encode(newPassword));

            this.usuarioRepository.save(usuario);

            return ResponseEntity.ok("Ok");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /* Read */
    @GetMapping("/getUserData")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public UsuarioResponseDTO getUserData() throws Exception {
        try {
            String username = this.userService.getTokenFromUser();
            if (Objects.isNull(username)) {
                throw new Exception("Usuário não encontrado!");
            }

            Long userId = this.usuarioRepository.findUsuarioByUsername(username);
            return new UsuarioResponseDTO(this.usuarioRepository.findUsuarioById(userId));

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

        /* Read */
        @PostMapping("/setUserName")
        @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
        public UsuarioResponseDTO setUserNome(@RequestBody UsuarioNameEditDTO usuarioNameEditDTO) throws Exception {
            try {
                String username = this.userService.getTokenFromUser();
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
