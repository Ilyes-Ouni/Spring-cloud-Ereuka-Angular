package com.ilyes.clients.services;

import com.ilyes.clients.Errors.CustomAuthenticationException;
import com.ilyes.clients.auth.AuthenticationRequest;
import com.ilyes.clients.auth.AuthenticationResponse;
import com.ilyes.clients.auth.RegisterRequest;
import com.ilyes.clients.config.JwtService;
import com.ilyes.clients.entities.Role;
import com.ilyes.clients.entities.Token;
import com.ilyes.clients.entities.TokenType;
import com.ilyes.clients.entities.User;
import com.ilyes.clients.repos.TokenRepository;
import com.ilyes.clients.repos.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
  @RequiredArgsConstructor
  public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
      if(repository.existsByEmail(request.getEmail())) {
        return  null;
      }

      var user = User.builder()
          .firstname(request.getFirstname())
          .lastname(request.getLastname())
          .email(request.getEmail())
          .password(passwordEncoder.encode(request.getPassword()))
          .role(Role.valueOf(request.getRole()))
          .build();
      var savedUser = repository.save(user);
      var jwtToken = jwtService.generateToken(user);
      saveUserToken(savedUser, jwtToken);

      return AuthenticationResponse.builder()
          .token(jwtToken)
          .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) throws CustomAuthenticationException {
      try {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
      } catch (Exception e) {
        // Handle invalid credentials
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return null;
      }
      var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        response.setHeader("Authorization", "Bearer " + jwtToken);

        Cookie cookie = new Cookie("token", jwtToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(3600);
        cookie.setPath("/");
        response.addCookie(cookie);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
      var token = Token.builder()
          .user(user)
          .token(jwtToken)
          .tokenType(TokenType.BEARER)
          .expired(false)
          .revoked(false)
          .build();
    }

    private void revokeAllUserTokens(User user) {
      var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
      if (validUserTokens.isEmpty())
        return;
      validUserTokens.forEach(token -> {
        token.setExpired(true);
        token.setRevoked(true);
      });
      tokenRepository.saveAll(validUserTokens);
    }
  }
