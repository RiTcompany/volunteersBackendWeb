package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entities.BotUser;
import org.example.pojo.JwtAuthenticationResponse;
import org.example.pojo.SignInRequest;
import org.example.pojo.SignUpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements org.example.services.AuthenticationService {
    private final UserServiceImpl userService;
    private final JwtServiceImpl jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        BotUser user = BotUser.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request
                        .getPassword()))
                .roleList(null)
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }


    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        try {
            var user = userService
                    .userDetailsService()
                    .loadUserByUsername(request.getUsername());

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new BadCredentialsException("Wrong password");
            }

            var jwt = jwtService.generateToken(user);
            return new JwtAuthenticationResponse(jwt);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Bad credentials");
        }
    }
}