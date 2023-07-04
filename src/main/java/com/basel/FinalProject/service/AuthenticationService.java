package com.basel.FinalProject.service;

import com.basel.FinalProject.enitity.Role;
import com.basel.FinalProject.enitity.User;
import com.basel.FinalProject.payload.JWTAuthResponse;
import com.basel.FinalProject.payload.LoginDto;
import com.basel.FinalProject.payload.SignUpDto;
import com.basel.FinalProject.repository.RoleRepository;
import com.basel.FinalProject.repository.UserRepository;
import com.basel.FinalProject.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userrepository;
    private final PasswordEncoder encoder;
    private  final RoleRepository roleRepository;

    private final JwtTokenProvider tokenProvider;


    public ResponseEntity<?> register(SignUpDto dto){
        if(userrepository.existsByUsername(dto.getUsername())){
            return new ResponseEntity<>("Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userrepository.existsByEmail(dto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }





       Role roles = roleRepository.findByName("Admin_Role").get();
        var user = User.builder().name(dto.getName())
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .username(dto.getUsername())
                .roles(Collections.singleton(roles))
                .build();
        userrepository.save(user);
        return new ResponseEntity<>("User registered successfully",
                HttpStatus.OK);

    }
    public ResponseEntity<JWTAuthResponse> authenticate(LoginDto dto){
        authenticationManager
                .authenticate( new UsernamePasswordAuthenticationToken(dto.getUsernameOrEmail()
                ,dto.getPassword()));
        var user=userrepository.findByUsernameOrEmail(dto.getUsernameOrEmail(),dto.getUsernameOrEmail()).orElseThrow();
        var jwtToken = tokenProvider.genratetoken(user);
        return  ResponseEntity.ok(JWTAuthResponse.builder()
                .accessToken(jwtToken).build());

    }




}
