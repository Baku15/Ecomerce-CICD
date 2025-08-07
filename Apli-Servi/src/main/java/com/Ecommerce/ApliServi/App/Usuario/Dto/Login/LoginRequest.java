package com.Ecommerce.ApliServi.App.Usuario.Dto.Login;

import lombok.*;

@Getter
@Setter
@NonNull
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginRequest {
    private String username;
    private String password;
}
