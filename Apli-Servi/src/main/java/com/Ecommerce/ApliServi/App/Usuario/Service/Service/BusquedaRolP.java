package com.Ecommerce.ApliServi.App.Usuario.Service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ecommerce.ApliServi.App.Usuario.Dto.Busquedas.BusRolDto;
import com.Ecommerce.ApliServi.App.Usuario.Dto.Busquedas.PersonaAuxDto;
import com.Ecommerce.ApliServi.App.Usuario.Dto.Busquedas.RolAuxDto;
import com.Ecommerce.ApliServi.App.Usuario.Entity.PersonaEntity;
import com.Ecommerce.ApliServi.App.Usuario.Entity.RolesEntity;
import com.Ecommerce.ApliServi.App.Usuario.Entity.UserEntity;
import com.Ecommerce.ApliServi.App.Usuario.Entity.Emun.ERole;
import com.Ecommerce.ApliServi.App.Usuario.Repository.PersonaRepository;
import com.Ecommerce.ApliServi.App.Usuario.Repository.RolesRepository;
import com.Ecommerce.ApliServi.App.Usuario.Repository.UserRepository;
import com.Ecommerce.ApliServi.App.Usuario.Service.Interface.BusquedaRolInterface;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;

@Service
public class BusquedaRolP implements BusquedaRolInterface {

        @Autowired
        private UserRepository userRepository;
        @Autowired
        private PersonaRepository personaRepository;
        @Autowired
        RolesRepository rolesRepository;

        public List<BusRolDto> asignarRolAUsuarios(String nombreRol) {
                // Obtener todos los usuarios
                List<UserEntity> usuarios = userRepository.findAll();

                // Asignar el rol especificado a cada usuario y generar los DTOs
                return usuarios.stream()
                                .map(user -> asignarRolAUsuario(user, nombreRol))
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList());
        }

        private BusRolDto asignarRolAUsuario(UserEntity userEntity, String nombreRol) {
                String nombreRolUsuario = userEntity.getRoles().stream()
                                .map(RolesEntity::getRoles)
                                .filter(role -> role.name().equals(nombreRol))
                                .findFirst()
                                .map(Enum::name)
                                .orElse(null);

                // Si el usuario no tiene el rol especificado, retornar null
                if (nombreRolUsuario == null) {
                        return null;
                }

                PersonaEntity per = personaRepository.findById(userEntity.getIdpersona().getIdPersona())
                                .orElseThrow(() -> new RuntimeException(
                                                "Persona no encontrada con ID: " + userEntity.getIdpersona()));
                PersonaAuxDto personaAuxDto = mapToPersonaDto(per, userEntity);

                return new BusRolDto(
                                userEntity.getIdUsuario(),
                                nombreRolUsuario,
                                personaAuxDto);
        }

        private PersonaAuxDto mapToPersonaDto(PersonaEntity personaEntity, UserEntity userEntity) {
                PersonaAuxDto personaAuxDto = new PersonaAuxDto();
                personaAuxDto.setIdPersona(personaEntity.getIdPersona());
                personaAuxDto.setNombre(personaEntity.getNombre());
                personaAuxDto.setApellido(personaEntity.getApellido());
                personaAuxDto.setCarnet(personaEntity.getCarnet());
                personaAuxDto.setTelefono(personaEntity.getTelefono());
                personaAuxDto.setEmail(userEntity.getEmail());
                personaAuxDto.setUsername(userEntity.getUsername());
                personaAuxDto.setPassword(userEntity.getPassword());
                return personaAuxDto;
        }

        public RolAuxDto updateUserRoles(int userId, List<String> newRoles) {
                RolAuxDto tre = new RolAuxDto();
                // Buscar el usuario por su ID
                UserEntity userEntity = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));

                // Obtener la lista de roles actual del usuario
                List<RolesEntity> currentRoles = userEntity.getRoles();

                // Limpiar los roles actuales del usuario
                currentRoles.clear();

                // Obtener y agregar los nuevos roles al usuario
                newRoles.forEach(roleName -> {
                        RolesEntity role = rolesRepository.findByRoles(ERole.valueOf(roleName))
                                        .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleName));
                        currentRoles.add(role);
                });

                // Guardar los cambios en el repositorio
                userRepository.save(userEntity);
                tre.setIdUsuario(userEntity.getIdUsuario());
                tre.setRoles(userEntity.getRoles().stream().map(RolesEntity::getRoles).map(Enum::name)
                                .collect(Collectors.toList()));

                // Mapear el usuario modificado a un DTO y devolverlo
                return tre;
        }
}
