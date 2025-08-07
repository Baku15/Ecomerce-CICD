package com.Ecommerce.ApliServi.App.Usuario.Entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Us_people")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
// permite la intecion con las tabla Us_Persona de la base de datos
public class PersonaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_people")
    private int idPersona;

    @Column(name = "people_name", nullable = false, length = 250)
    private String nombre;

    @Column(name = "people_lastname", nullable = false, length = 500)
    private String apellido;

    @Column(name = "carnet", unique = true, nullable = false, length = 250)
    private String carnet;

    @Column(name = "phone", nullable = false, length = 250)
    private String telefono;

    @OneToMany(mappedBy = "idpersona", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserEntity> usuarios;
}
