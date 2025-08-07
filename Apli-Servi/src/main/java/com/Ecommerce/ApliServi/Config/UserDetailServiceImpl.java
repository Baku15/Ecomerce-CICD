package com.Ecommerce.ApliServi.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Ecommerce.ApliServi.App.Usuario.Entity.UserEntity;
import com.Ecommerce.ApliServi.App.Usuario.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
        @Autowired
        private UserRepository usuarioRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserEntity userEntity = usuarioRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(
                                "El usuario " + username + " no existe"));
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                userEntity.getRoles()
                        .forEach(role -> authorities.add(
                                new SimpleGrantedAuthority("ROLE_".concat(role.getRoles().name()))));
                userEntity.getRoles().stream().flatMap(r -> r.getAutorizaciones().stream())
                        .forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getPermiso().name())));

                return userEntity;
        }
}
