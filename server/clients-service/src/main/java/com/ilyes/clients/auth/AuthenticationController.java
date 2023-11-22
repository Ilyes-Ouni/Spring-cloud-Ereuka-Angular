package com.ilyes.clients.auth;

import com.ilyes.clients.Errors.CustomAuthenticationException;
import com.ilyes.clients.services.AuthenticationService;
import com.ilyes.clients.config.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private UserDetailsService userDetailsService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    if(service.register(request) == null) {
      String emailException = "User already exists with email: ";
      return ResponseEntity.badRequest().body(Collections.singletonMap("message", emailException + request.getEmail()));
    }
    return ResponseEntity.ok(Collections.singletonMap("message", "User created successfully"));
  }


  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse response) throws CustomAuthenticationException {
    System.out.println("authenticate:  " + request.getEmail() + " " + request.getPassword());
    var authResponse = service.authenticate(request, response);
    if (authResponse == null || authResponse.getToken() == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
              .body(Map.of("error", "Invalid email or password"));

    }
    return ResponseEntity.ok(Collections.singletonMap("token", authResponse.getToken()));
  }
  @PostMapping("/validateToken")
  public ResponseEntity<?> validerToken(HttpServletRequest request) {
    try {
      String token = request.getHeader("Authorization").substring(7);

      final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtService.extractUsername(token));
      boolean estValide = jwtService.isTokenValid(token, userDetails);

      if (estValide) {
        return ResponseEntity.ok(Collections.singletonMap("state", true));
      } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    } catch (Exception err) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
