package com.railway.finalProject.service.impl;

import com.railway.finalProject.dto.UserDto;
import com.railway.finalProject.models.Role;
import com.railway.finalProject.models.Users;
import com.railway.finalProject.repo.RoleRepository;
import com.railway.finalProject.repo.UsersRepository;
import com.railway.finalProject.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private UsersRepository usersRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UsersRepository usersRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public boolean saveUser(UserDto userDto) {
        LOGGER.info("Save user with login = {}", userDto.getLogin());
        Users user = new Users();
        user.setUserName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setLogin(userDto.getLogin());
        user.setPhoneNumber(Integer.parseInt(userDto.getPhoneNumber()));

        Role role = roleRepository.findByName("ROLE_USER");
        if(role == null){
            role = checkRoleExist();
        }

        user.setRoles(Arrays.asList(role));
        usersRepository.save(user);
        return true;
    }

    private UserDto mapToUserDto(Users user){
        UserDto userDto = new UserDto();
        String[] str = user.getUserName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        userDto.setLogin(user.getLogin());
        userDto.setPhoneNumber(String.valueOf(user.getPhoneNumber()));
        return userDto;
    }

    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }

    @Override
    public Users findUserByEmail(String email) {
        LOGGER.info("Find user by Email = {}", email);
        return usersRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        LOGGER.info("Find all users");
        List<Users> users = (List<Users>) usersRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public Users findUserByLogin(String login) {
        LOGGER.info("Find user by login = {}", login);
        return usersRepository.findByLogin(login);
    }

    @Override
    public Users findUserByPhone(String phoneNumber) {
        LOGGER.info("Find user by phone = {}", phoneNumber);
        return usersRepository.findByByPhoneNumber(phoneNumber);
    }

    @Override
    public Page<Users> findAllPagebleUsers(PageRequest of) {
        LOGGER.info("Find pageable list of users");
        return usersRepository.findAll(of);
    }
}
