package com.project.controller;


import com.project.model.dto.UserDTO;
import com.project.model.response.SuccessResponse;
import com.project.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/concert-on/user")
public class UserController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/")
    private ResponseEntity<SuccessResponse<UserDTO>> getUser(HttpServletRequest request){

        return new ResponseEntity<>(new SuccessResponse<>(customUserDetailsService.loadUser(request)), HttpStatus.OK);

    }

}
