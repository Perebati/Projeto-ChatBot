package com.br.projetoGlobal.controllers;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetoGlobal.models.Usuario;
import com.br.projetoGlobal.repository.UsuarioRepository;
import com.br.projetoGlobal.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/test")
public class AdminController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() throws Exception {

        String username = this.userService.getTokenFromUser();
        return "User Content." + username;
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Você é um moderador!";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Você é um admin!";
    }

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Usuario> getUsers() throws Exception {
        try {
            String username = this.userService.getTokenFromUser();
            if (Objects.isNull(username)) {
                throw new Exception("Usuário não encontrado!");
            }

            return this.usuarioRepository.findAll();

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @SuppressWarnings("null")
    @DeleteMapping("/deleteUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@Valid @RequestParam Long userId) throws Exception {
        try {
            String username = this.userService.getTokenFromUser();
            if (Objects.isNull(username)) {
                throw new Exception("Usuário não encontrado!");
            }

            this.usuarioRepository.deleteById(userId);

            return ResponseEntity.ok("Ok");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
