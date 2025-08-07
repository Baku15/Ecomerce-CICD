package com.Ecommerce.ApliServi.App.Usuario.Api.Basico;

import com.Ecommerce.ApliServi.App.Usuario.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.Ecommerce.ApliServi.App.Usuario.Dto.UserDto;
import com.Ecommerce.ApliServi.App.Usuario.Dto.Login.JwtResponse;
import com.Ecommerce.ApliServi.App.Usuario.Dto.Login.LoginRequest;
import com.Ecommerce.ApliServi.App.Usuario.Dto.Respuesta.Respuesta;
import com.Ecommerce.ApliServi.App.Usuario.Service.Service.UserService;
import com.Ecommerce.ApliServi.Util.JwtUtils;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserApi {
    private final UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Respuesta> createUser(@RequestBody UserDto userDto) {
        try {
            UserDto createdUser = userService.createUser(userDto);
            return ResponseEntity.ok(new Respuesta("SUCCESS", createdUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Respuesta> getUserById(@PathVariable int id) {
        try {
            UserDto userDto = userService.getUserById(id);
            return ResponseEntity.ok(new Respuesta("SUCCESS", userDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Respuesta("ERROR", "Usuario no encontrado por id: " + id));
        }
    }

    @GetMapping("/micro/find/{id}")
    public ResponseEntity<Respuesta> getMicroUserById(@PathVariable int id) {
        try {
            UserDto userDto = userService.getUserById(id);
            return ResponseEntity.ok(new Respuesta("SUCCESS", userDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Respuesta("ERROR", "Usuario no encontrado por id: " + id));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Respuesta> getAllUsers() {
        try {
            List<UserDto> users = userService.getAllUsers();
            return ResponseEntity.ok(new Respuesta("SUCCESS", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Respuesta> updateUser(@PathVariable int id, @RequestBody UserDto userDto) {
        try {
            UserDto updatedUser = userService.updateUser(id, userDto);
            return ResponseEntity.ok(new Respuesta("SUCCESS", updatedUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Respuesta> deleteUser(@PathVariable int id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new Respuesta("SUCCESS", "Usuario eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Respuesta("ERROR", e.getMessage()));
        }
    }

    @PostMapping("/auth/login")
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
}
