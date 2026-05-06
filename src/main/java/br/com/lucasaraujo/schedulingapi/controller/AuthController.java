package br.com.lucasaraujo.schedulingapi.controller;

import br.com.lucasaraujo.schedulingapi.dto.AuthResponse;
import br.com.lucasaraujo.schedulingapi.dto.LoginRequest;
import br.com.lucasaraujo.schedulingapi.dto.RegisterRequest;
import br.com.lucasaraujo.schedulingapi.model.User;
import br.com.lucasaraujo.schedulingapi.repository.UserRepository;
import br.com.lucasaraujo.schedulingapi.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = jwtService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        User newUser = new User();
        newUser.setName(request.name());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole(request.role());

        userRepository.save(newUser);

        var token = jwtService.generateToken(newUser);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
