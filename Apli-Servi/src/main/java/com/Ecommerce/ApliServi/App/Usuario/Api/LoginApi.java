package com.Ecommerce.ApliServi.App.Usuario.Api;

import java.util.Map;

import com.Ecommerce.ApliServi.App.Usuario.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.Ecommerce.ApliServi.App.Usuario.Dto.Login.JwtResponse;
import com.Ecommerce.ApliServi.App.Usuario.Dto.Login.LoginRequest;
import com.Ecommerce.ApliServi.App.Usuario.Dto.Respuesta.Respuesta;
import com.Ecommerce.ApliServi.Util.JwtUtils;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/auth")
public class LoginApi {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<Respuesta> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = jwtUtils.createAccessToken(authentication);
            String refreshToken = jwtUtils.createRefreshToken(authentication);

            UserEntity userDetails = (UserEntity) authentication.getPrincipal();
            String username = userDetails.getUsername();
            int userId = userDetails.getIdUsuario();
            String role = userDetails.getRoles().stream()
                    .map(roleEntity -> roleEntity.getRoles().name())
                    .findFirst()
                    .orElse("");

            JwtResponse jwtResponse = new JwtResponse(username, accessToken, refreshToken, userId, role);

            return ResponseEntity.ok(new Respuesta("SUCCESS", jwtResponse));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Respuesta("ERROR", "Credenciales inválidas"));
        }
    }

    @GetMapping("/result")
    public ResponseEntity<Respuesta> getTokenValues(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String tokenHeader) {
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String jwtToken = tokenHeader.substring(7);
            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);

            Map<String, Claim> tokenClaims = jwtUtils.returnClaimsFromToken(decodedJWT);

            return ResponseEntity.ok(new Respuesta("SUCCESS", tokenClaims));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Respuesta("ERROR", "Token de autorización no proporcionado o inválido"));
        }
    }
}