package com.Ecommerce.ApliServi.App.Usuario.Entity;

import java.util.List;

import com.Ecommerce.ApliServi.App.Usuario.Entity.Emun.ERole;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Us_Roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RolesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int idRoles;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false)
    private ERole roles;

    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> users;

    @ManyToMany
    @JoinTable(name = "roles_permissions", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "authorization_id"))
    private List<AutorizacionEntity> autorizaciones;
}