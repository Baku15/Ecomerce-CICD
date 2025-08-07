package com.Ecommerce.ApliServi.App.Usuario.Service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Ecommerce.ApliServi.App.Usuario.Dto.Busquedas.BusPersonaDto;
import com.Ecommerce.ApliServi.App.Usuario.Dto.Busquedas.CreateDto;
import com.Ecommerce.ApliServi.App.Usuario.Dto.Busquedas.UserAuxDto;
import com.Ecommerce.ApliServi.App.Usuario.Dto.Respuesta.PageableQuery;
import com.Ecommerce.ApliServi.App.Usuario.Entity.PersonaEntity;
import com.Ecommerce.ApliServi.App.Usuario.Entity.RolesEntity;
import com.Ecommerce.ApliServi.App.Usuario.Entity.UserEntity;
import com.Ecommerce.ApliServi.App.Usuario.Entity.Emun.ERole;
import com.Ecommerce.ApliServi.App.Usuario.Repository.PersonaRepository;
import com.Ecommerce.ApliServi.App.Usuario.Repository.RolesRepository;
import com.Ecommerce.ApliServi.App.Usuario.Repository.UserRepository;
import com.Ecommerce.ApliServi.App.Usuario.Service.Interface.BusquedaPersonaInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusquedaUserP implements BusquedaPersonaInterface {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    private PasswordEncoder passwordConverter;

    public List<BusPersonaDto> getPersonasS(PageableQuery pageableQuery) {
        Sort sort = Sort.by(Sort.Direction.fromString(pageableQuery.EnOrden()),
                pageableQuery.OrdenadoPor());
        Pageable pageable = PageRequest.of(pageableQuery.Pagina(),
                pageableQuery.ElementosPorPagina(), sort);

        Page<PersonaEntity> personaPage = personaRepository.findAll(pageable);

        return personaPage.getContent().stream()
                .map(this::obtener)
                .collect(Collectors.toList());
    }

    public List<BusPersonaDto> getPersonasS() {
        return personaRepository.findAll()
                .stream()
                .map(this::obtener)
                .collect(Collectors.toList());
    }

    public List<BusPersonaDto> buscarPorNombre(String nombre) {
        return personaRepository.findByNombre(nombre)
                .stream()
                .map(this::obtener)
                .collect(Collectors.toList());
    }

    public BusPersonaDto buscarPorCarnet(String carnet) {
        PersonaEntity personaEntity = personaRepository.findByCarnet(carnet);
        return personaEntity != null ? obtener(personaEntity) : null;
    }

    private BusPersonaDto obtener(PersonaEntity personaEntity) {
        BusPersonaDto busPersonaDto = new BusPersonaDto();
        busPersonaDto.setIdPersona(personaEntity.getIdPersona());
        busPersonaDto.setNombre(personaEntity.getNombre());
        busPersonaDto.setApellido(personaEntity.getApellido());
        busPersonaDto.setCarnet(personaEntity.getCarnet());
        busPersonaDto.setTelefono(personaEntity.getTelefono());

        // Obtener todos los usuarios asociados a la persona consultando el repositorio
        // de usuarios
        List<UserEntity> userEntities = userRepository.findByIdpersona_IdPersona(personaEntity.getIdPersona());

        // Verificar si hay algún usuario asociado
        if (!userEntities.isEmpty()) {
            List<UserAuxDto> userAuxDtoList = new ArrayList<>();

            // Iterar sobre la lista de entidades de usuario
            for (UserEntity userEntity : userEntities) {
                // Mapear la entidad de usuario a un DTO y agregarlo a la lista de DTOs
                UserAuxDto userAuxDto = mapToDtoU(userEntity);
                userAuxDtoList.add(userAuxDto);
            }

            // Establecer la lista de DTOs en busPersonaDto
            busPersonaDto.setUserAuxDtoList(userAuxDtoList);
        }

        return busPersonaDto;
    }

    private UserAuxDto mapToDtoU(UserEntity userEntity) {
        UserAuxDto userAuxDto = new UserAuxDto();
        userAuxDto.setIdUsuario(userEntity.getIdUsuario());
        userAuxDto.setEmail(userEntity.getEmail());
        userAuxDto.setPassword(userEntity.getPassword());
        userAuxDto.setUsername(userEntity.getUsername());
        userAuxDto.setRoles(
                userEntity.getRoles().stream().map(RolesEntity::getRoles).map(Enum::name).collect(Collectors.toList()));
        return userAuxDto;
    }

    public CreateDto createPersonaUser(CreateDto user) {
        // Mapea el DTO de usuario a la entidad de persona
        PersonaEntity personaEntity = mapToEntityP(user);
        // Guarda la entidad de persona y obtén la entidad guardada con el ID
        personaEntity = personaRepository.save(personaEntity);
        BusPersonaDto bus = buscarPorCarnet(user.getCarnet());
        user.setIdPersona(bus.getIdPersona());
        // Mapea el DTO de usuario a la entidad de usuario, pasando la entidad de
        // persona
        UserEntity userEntity = mapToEntityU(user, bus.getIdPersona());
        // Guarda la entidad de usuario
        userRepository.save(userEntity);
        return user;
    }

    // Método para mapear un DTO PersonaDto a una entidad PersonaEntity
    private PersonaEntity mapToEntityP(CreateDto personaDto) {
        PersonaEntity personaEntity = new PersonaEntity();
        personaEntity.setNombre(personaDto.getNombre());
        personaEntity.setApellido(personaDto.getApellido());
        personaEntity.setCarnet(personaDto.getCarnet());
        personaEntity.setTelefono(personaDto.getTelefono());
        return personaEntity;
    }

    public UserEntity mapToEntityU(CreateDto userDto, int p) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(passwordConverter.encode(userDto.getPassword()));
        userEntity.setUsername(userDto.getUsername());

        if (userDto.getIdPersona() != 0) {
            PersonaEntity personaEntity = personaRepository.findById(p)
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

    public void deletePersonaUser(int userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));

        // Eliminar la persona asociada al usuario
        personaRepository.deleteById(userEntity.getIdpersona().getIdPersona());

        // Eliminar el usuario
        userRepository.deleteById(userId);
    }

    public CreateDto updatePersonaUser(int userId, CreateDto user) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));

        // Actualizar la entidad de persona
        PersonaEntity personaEntity = personaRepository.findById(userEntity.getIdpersona().getIdPersona())
                .orElseThrow(() -> new RuntimeException(
                        "Persona no encontrada con ID: " + userEntity.getIdpersona().getIdPersona()));
        personaEntity.setNombre(user.getNombre());
        personaEntity.setApellido(user.getApellido());
        personaEntity.setCarnet(user.getCarnet());
        personaEntity.setTelefono(user.getTelefono());
        personaRepository.save(personaEntity);

        // Actualizar la entidad de usuario
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(passwordConverter.encode(user.getPassword()));
        userEntity.setUsername(user.getUsername());
        userRepository.save(userEntity);

        return user;
    }
}
