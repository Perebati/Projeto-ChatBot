package com.br.projetoGlobal.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
                //to do
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
    public Usuario getProjects() throws Exception {
        try {
            String username = this.userService.getTokenFromUser();
            if (Objects.isNull(username)) {
                //to do
            }

            Long userId = this.usuarioRepository.findUsuarioByUsername(username);
            return this.usuarioRepository.findUsuarioById(userId);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
