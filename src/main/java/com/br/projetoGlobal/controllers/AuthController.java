package com.br.projetoGlobal.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.br.projetoGlobal.config.security.jwt.JwtUtils;
import com.br.projetoGlobal.controllers.payload.requestDTO.LoginRequestDTO;
import com.br.projetoGlobal.controllers.payload.requestDTO.SignupRequestDTO;
import com.br.projetoGlobal.controllers.payload.responseDTO.JwtResponseDTO;
import com.br.projetoGlobal.controllers.payload.responseDTO.MessageResponseDTO;
import com.br.projetoGlobal.models.Role;
import com.br.projetoGlobal.models.Usuario;
import com.br.projetoGlobal.models.Enums.RoleEnum;
import com.br.projetoGlobal.repository.RoleRepository;
import com.br.projetoGlobal.repository.UsuarioRepository;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    /* Login */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            return ResponseEntity.ok(new JwtResponseDTO(jwt));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /* Create usuario */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDTO signUpRequestDTO) throws Exception {
        if (usuarioRepository.existsByEmail(signUpRequestDTO.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponseDTO("Não é possível cadastrar esse usuário."));
        }

        // Create new user's account
        Usuario usuario = new Usuario(signUpRequestDTO.getEmail(),
                signUpRequestDTO.getEmail(),
                encoder.encode(signUpRequestDTO.getPassword()));


        Set<String> strRoles = signUpRequestDTO.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Erro: Role não encontrada"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Erro: Role não encontrada"));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(RoleEnum.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Erro: Role não encontrada"));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Erro: Role não encontrada"));
                        roles.add(userRole);
                }
            });
        }

        usuario.setRoles(roles);
        usuarioRepository.save(usuario);

        return authenticateUser(new LoginRequestDTO(signUpRequestDTO.getEmail(), signUpRequestDTO.getPassword()));
    }
}
