package com.Ecommerce.ApliServi.App.Usuario.Service.Service;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Ecommerce.ApliServi.App.Usuario.Entity.AutorizacionEntity;
import com.Ecommerce.ApliServi.App.Usuario.Entity.RolesEntity;
import com.Ecommerce.ApliServi.App.Usuario.Entity.Emun.EAutorizacion;
import com.Ecommerce.ApliServi.App.Usuario.Entity.Emun.ERole;
import com.Ecommerce.ApliServi.App.Usuario.Repository.AutorizacionRepository;
import com.Ecommerce.ApliServi.App.Usuario.Repository.RolesRepository;

import java.util.List;

@Component
public class AuthRoleService {
    private final AutorizacionRepository autorizacionRepository;
    private final RolesRepository rolesRepository;

    @Autowired
    public AuthRoleService(AutorizacionRepository autorizacionRepository, RolesRepository rolesRepository) {
        this.autorizacionRepository = autorizacionRepository;
        this.rolesRepository = rolesRepository;
    }

    @PostConstruct
    public void init() {
        if (autorizacionRepository.count() == 0 && rolesRepository.count() == 0) {
            try {
                run();
            } catch (Exception e) {
                // Manejar la excepción según sea necesario
            }
        }
    }

    public void run() throws Exception {
        initAutorizaciones();
        initRoles();
    }

    private void initAutorizaciones() {
        // Crea permisos si no existen en la base de datos
        for (EAutorizacion permiso : EAutorizacion.values()) {
            AutorizacionEntity autorizacionEntity = autorizacionRepository.findByPermiso(permiso);
            if (autorizacionEntity == null) {
                autorizacionEntity = new AutorizacionEntity();
                autorizacionEntity.setPermiso(permiso);
                autorizacionRepository.save(autorizacionEntity);
            }
        }
    }

    private void initRoles() {
        // Obtener todos los roles disponibles en la base de datos
        List<RolesEntity> rolesInDatabase = rolesRepository.findAll();

        // Iterar sobre todos los roles definidos en el enum
        for (ERole role : ERole.values()) {
            // Verificar si el rol actual del enum no existe en la base de datos
            boolean roleExistsInDatabase = rolesInDatabase.stream()
                    .anyMatch(roleEntity -> roleEntity.getRoles() == role);
            if (!roleExistsInDatabase) {
                // Crear una nueva entidad de roles
                RolesEntity rolesEntity = new RolesEntity();
                rolesEntity.setRoles(role);

                // Obtener todas las autorizaciones disponibles
                List<AutorizacionEntity> autorizacionesEntities = autorizacionRepository.findAll();

                // Asignar todas las autorizaciones disponibles al rol actual
                rolesEntity.setAutorizaciones(autorizacionesEntities);

                // Guardar la entidad
                rolesRepository.save(rolesEntity);
            }
        }
    }
}