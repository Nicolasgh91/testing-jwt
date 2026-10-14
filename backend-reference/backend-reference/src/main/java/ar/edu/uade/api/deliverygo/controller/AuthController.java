package ar.edu.uade.api.deliverygo.controller;

import ar.edu.uade.api.deliverygo.dto.LoginDto;
import ar.edu.uade.api.deliverygo.dto.TokenDto;
import ar.edu.uade.api.deliverygo.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public AuthController(
          AuthenticationManager authenticationManager,
          JwtService jwtService) {

    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(
          @RequestBody LoginDto dto) {

    Authentication authentication =
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.username(),
                            dto.password()
                    )
            );

    String username =
            authentication.getName();

    String rol =
            authentication
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("ROLE_USER")
                    .replace("ROLE_", "");

    String token =
            jwtService.generarToken(
                    username,
                    rol
            );

    return ResponseEntity.ok(
            new TokenDto(
                    token,
                    "Bearer",
                    jwtService.getExpirationSeconds()
            )
    );
  }
}