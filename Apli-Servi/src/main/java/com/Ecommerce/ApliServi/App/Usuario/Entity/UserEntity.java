package com.Ecommerce.ApliServi.App.Usuario.Entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "Us_user")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int idUsuario;

    @Column(name = "email", unique = false, nullable = false, length = 250)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "username", unique = false, nullable = false, length = 50)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private PersonaEntity idpersona;

    @Column(name = "account_non_expired")
    private boolean accountNonExpired;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;

    @Column(name = "roles")
    private boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "User_Roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<RolesEntity> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .flatMap(role -> role.getAutorizaciones().stream())
                .map(autorizacion -> new SimpleGrantedAuthority(autorizacion.getPermiso().name()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
}