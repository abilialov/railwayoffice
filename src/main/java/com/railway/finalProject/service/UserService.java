package com.railway.finalProject.service;

import com.railway.finalProject.dto.UserDto;
import com.railway.finalProject.models.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface UserService {

    boolean saveUser(UserDto userDto);

    Users findUserByEmail(String email);

    List<UserDto> findAllUsers();

    Users findUserByLogin(String login);

    Users findUserByPhone(String phoneNumber);

    Page<Users> findAllPagebleUsers(PageRequest of);

}
