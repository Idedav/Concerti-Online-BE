package com.project.controller;

import com.project.model.AuthRequest;
import com.project.model.dto.AuthenticationResponse;
import com.project.model.response.SuccessResponse;
import com.project.service.AuthenticationService;
import com.project.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/concert-on/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<AuthenticationResponse>> login(@RequestBody AuthRequest authRequest){

        return new ResponseEntity<>(new SuccessResponse<>(authenticationService.authenticate(authRequest)), HttpStatus.OK);

    }

    @PostMapping("/refresh-token")
    public ResponseEntity<SuccessResponse<AuthenticationResponse>> refreshToken(HttpServletRequest request){



        return new ResponseEntity<>(new SuccessResponse<>(authenticationService.refreshToken(request)), HttpStatus.OK);

    }
}



