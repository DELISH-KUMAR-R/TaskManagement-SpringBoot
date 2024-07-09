package com.Task_1.TaskManagement.auth;

import com.Task_1.TaskManagement.Entity.LogDb;
import com.Task_1.TaskManagement.Entity.Role;
import com.Task_1.TaskManagement.Repository.LogDbRepository;
import com.Task_1.TaskManagement.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService
{
    private final LogDbRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request)
    {
        var user = LogDb.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        repository.save(user);
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        System.out.println(passwordEncoder.encode(request.getPassword()));
        LogDb user=repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        //if(passwordEncoder.encode(request.getPassword()).equals(user.getPassword()))
        //{
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        //}
        //throw new Exception("Incorrect password");
    }

    public AuthenticationResponse refresh(String refreshToken)
    {
        String userEmail = jwtService.extractUsername(refreshToken);
        LogDb user = repository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new RuntimeException("Refresh token is invalid");
        }

        String newAccessToken = jwtService.generateToken(user);
        System.out.println("refreshed");
        return AuthenticationResponse.builder()
                .token(newAccessToken)
                .build();
    }
}
