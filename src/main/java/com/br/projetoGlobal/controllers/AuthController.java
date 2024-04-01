package com.br.projetoGlobal.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.br.projetoGlobal.controllers.payload.dtos.requestDTO.LoginRequestDTO;
import com.br.projetoGlobal.controllers.payload.dtos.requestDTO.SignupRequestDTO;

import com.br.projetoGlobal.service.AuthService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) throws Exception {
        return this.authService.authenticateUser(loginRequestDTO);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDTO signUpRequestDTO) throws Exception {
        return this.authService.registerUser(signUpRequestDTO);
    }
}
