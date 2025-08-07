package com.Ecommerce.ApliServi.App.Usuario.Dto.Login;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor

public class JwtResponse {
    private String username;
    private String accessToken;
    private String refreshToken;
    private int userId;
    private String role;

    public JwtResponse(String username, String accessToken, String refreshToken, int userId, String role) {
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.role = role;
    }
}