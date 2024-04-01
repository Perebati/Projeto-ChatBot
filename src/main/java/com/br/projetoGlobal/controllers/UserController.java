package com.br.projetoGlobal.controllers;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.br.projetoGlobal.controllers.payload.dtos.requestDTO.UsuarioNameEditDTO;
import com.br.projetoGlobal.controllers.payload.dtos.requestDTO.UsuarioPasswordEditDTO;
import com.br.projetoGlobal.controllers.payload.dtos.responseDTO.UsuarioResponseDTO;
import com.br.projetoGlobal.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/usuario")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/updatePassword")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UsuarioPasswordEditDTO body) throws Exception {
        String username = this.userService.getTokenFromUser();
        return this.userService.updatePassword(body, username);
    }

    @GetMapping("/getUserData")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public UsuarioResponseDTO getUserData() throws Exception {
        String username = this.userService.getTokenFromUser();
        return this.userService.getUserData(username);
    }

    @PostMapping("/setUserName")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public UsuarioResponseDTO setUserNome(@RequestBody UsuarioNameEditDTO usuarioNameEditDTO) throws Exception {
        String username = this.userService.getTokenFromUser();
        return this.userService.setUserNome(usuarioNameEditDTO, username);
    }
           
}
