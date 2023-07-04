package com.basel.FinalProject.controller;

import com.basel.FinalProject.payload.JWTAuthResponse;
import com.basel.FinalProject.payload.LoginDto;
import com.basel.FinalProject.payload.SignUpDto;
import com.basel.FinalProject.service.AuthenticationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
   private  final AuthenticationService sevice;
    @ApiOperation(value = "Create User REST API")

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody SignUpDto dto
            ){
        return  sevice.register(dto);

    }

    @ApiOperation(value = "authenticate User REST API")

    @PostMapping("/authenticate")
    public ResponseEntity<JWTAuthResponse> authenticate(
            @RequestBody LoginDto dto
    ){
        return sevice.authenticate(dto);


    }
}
