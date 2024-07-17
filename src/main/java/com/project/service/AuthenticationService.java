package com.project.service;

import com.project.exception.AuthException;
import com.project.model.AuthRequest;
import com.project.model.User;
import com.project.model.dto.AuthenticationResponse;
import com.project.model.enums.ErrorCode;
import com.project.model.response.ErrorResponse;
import com.project.repository.RoleRepository;
import com.project.repository.UserRepository;
import com.project.utils.JwtUtils;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    public AuthenticationResponse authenticate(AuthRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthException(new ErrorResponse(ErrorCode.EUN, "user not found")));

        return AuthenticationResponse.builder()
                .accessToken(jwtUtils.generateToken(user))
                .refreshToken(jwtUtils.generateRefreshToken(user))
                .build();
    }

    public AuthenticationResponse refreshToken(HttpServletRequest request){

        User user = customUserDetailsService.getUserFromToken(request);

        return AuthenticationResponse.builder()
                .accessToken(jwtUtils.generateToken(user))
                .refreshToken(jwtUtils.generateRefreshToken(user))
                .build();
    }

}
