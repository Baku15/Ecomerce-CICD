package com.Ecommerce.ApliServi.App.Usuario.Service.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ecommerce.ApliServi.App.Usuario.Dto.UserDto;
import com.Ecommerce.ApliServi.App.Usuario.Entity.PersonaEntity;
import com.Ecommerce.ApliServi.App.Usuario.Entity.RolesEntity;
import com.Ecommerce.ApliServi.App.Usuario.Entity.UserEntity;
import com.Ecommerce.ApliServi.App.Usuario.Entity.Emun.ERole;
import com.Ecommerce.ApliServi.App.Usuario.Repository.PersonaRepository;
import com.Ecommerce.ApliServi.App.Usuario.Repository.RolesRepository;
import com.Ecommerce.ApliServi.App.Usuario.Repository.UserRepository;
import com.Ecommerce.ApliServi.App.Usuario.Service.Interface.UserInterface;

@Service
public class UserService implements UserInterface {
    @Autowired
    private PasswordEncoder passwordConverter;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    RolesRepository rolesRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = mapToEntity(userDto);
        return mapToDto(userRepository.save(userEntity));
    }

    @Override
    public UserDto getUserById(int id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return mapToDto(userEntity);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public UserDto updateUser(int id, UserDto userDto) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        UserEntity updatedUser = mapToEntity(userDto);
        updatedUser.setIdUsuario(existingUser.getIdUsuario());
        return mapToDto(userRepository.save(updatedUser));
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    private UserDto mapToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setIdUsuario(userEntity.getIdUsuario());
        userDto.setEmail(userEntity.getEmail());
        userDto.setPassword(userEntity.getPassword());
        userDto.setUsername(userEntity.getUsername());
        userDto.setIdPersona(userEntity.getIdpersona().getIdPersona()); // Asumiendo que la clase PersonaEntity tiene un
                                                                        // campo idPersona
        userDto.setRoles(
                userEntity.getRoles().stream().map(RolesEntity::getRoles).map(Enum::name).collect(Collectors.toList()));
        return userDto;
    }

    public UserEntity mapToEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(passwordConverter.encode(userDto.getPassword()));
        userEntity.setUsername(userDto.getUsername());

        if (userDto.getIdPersona() != 0) {
            PersonaEntity personaEntity = personaRepository.findById(userDto.getIdPersona())
                    .orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + userDto.getIdPersona()));

            userEntity.setIdpersona(personaEntity);
        }

        List<RolesEntity> roles = userDto.getRoles().stream()
                .map(roleName -> rolesRepository.findByRoles(ERole.valueOf(roleName))
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleName)))
                .collect(Collectors.toList());

        userEntity.setRoles(roles);

        userEntity.setAccountNonExpired(true); // Puedes establecer estos valores según tus requisitos
        userEntity.setAccountNonLocked(true);
        userEntity.setCredentialsNonExpired(true);
        userEntity.setEnabled(true);

        return userEntity;
    }

}
