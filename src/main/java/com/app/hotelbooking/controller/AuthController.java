package com.app.hotelbooking.controller;

import com.app.hotelbooking.dto.LoginCredentialsDto;
import com.app.hotelbooking.model.User;
import com.app.hotelbooking.service.JwtService;
import com.app.hotelbooking.service.UserService;
import com.app.hotelbooking.validation.ObjectNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginCredentialsDto loginCredentials){

        User user = userService.findUserByEmail(loginCredentials.getEmail()).
                orElseThrow(() -> new ObjectNotFoundException("User is not found!"));

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginCredentials.getEmail(),
                loginCredentials.getPassword()
        ));

        String jwtToken = jwtService.generateToken(user);

        Map<String, String> response = new HashMap<>();
        response.put("token", jwtToken);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
