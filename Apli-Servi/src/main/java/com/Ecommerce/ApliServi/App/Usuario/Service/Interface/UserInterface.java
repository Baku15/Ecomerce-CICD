package com.Ecommerce.ApliServi.App.Usuario.Service.Interface;

import java.util.List;

import com.Ecommerce.ApliServi.App.Usuario.Dto.UserDto;

public interface UserInterface {
    UserDto createUser(UserDto userDto);

    UserDto getUserById(int id);

    List<UserDto> getAllUsers();

    UserDto updateUser(int id, UserDto userDto);

    void deleteUser(int id);

}
