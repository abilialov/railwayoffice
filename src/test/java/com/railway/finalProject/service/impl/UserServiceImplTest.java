package com.railway.finalProject.service.impl;

import com.railway.finalProject.dto.UserDto;
import com.railway.finalProject.models.Users;
import com.railway.finalProject.repo.RoleRepository;
import com.railway.finalProject.repo.UsersRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UsersRepository usersRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void findAllUsers() {
        List<Users> usersFromMock = getUsers();
        Mockito.when(usersRepository.findAll()).thenReturn(usersFromMock);

        List<UserDto> users = userService.findAllUsers();

        Assert.assertNotNull(users);
        Assert.assertEquals( users.get(0).getLogin(), usersFromMock.get(0).getLogin());
    }

    public List<Users> getUsers(){
        Users user1 = new Users();
        Users user2 = new Users();

        user1.setId(1L);
        user1.setUserName("User1 lastname1");
        user1.setLogin("userlog1");
        user1.setPhoneNumber(111111);
        user1.setEmail("mail@mail.com");
        user1.setPassword(passwordEncoder.encode("111111"));

        user2.setId(2L);
        user2.setUserName("User2 lastname2");
        user2.setLogin("userlog2");
        user2.setPhoneNumber(2222222);
        user2.setEmail("mail2@mail.com");
        user2.setPassword(passwordEncoder.encode("222222"));

        return List.of(user1, user2);
    }

    @Test
    public void findUserByLogin() {
        Users userFromMock = getUsers().get(0);
        String userLogin = "userlog1";
        Mockito.when(usersRepository.findByLogin(userLogin)).thenReturn(userFromMock);

        Users resultUser = userService.findUserByLogin(userLogin);

        Assert.assertNotNull(resultUser);
        Assert.assertEquals(resultUser.getUserName(), userFromMock.getUserName());
        Assert.assertEquals(resultUser, userFromMock);

    }

    @Test
    public void findUserByPhone() {
        Users userFromMock = getUsers().get(0);
        String phone = "111111";
        Mockito.when(usersRepository.findByByPhoneNumber(phone)).thenReturn(userFromMock);

        Users resultUser = userService.findUserByPhone(phone);

        Assert.assertNotNull(resultUser);
        Assert.assertEquals(resultUser.getUserName(), userFromMock.getUserName());
        Assert.assertEquals(resultUser, userFromMock);

    }

    @Test
    public void findUserByEmail() {

        Users userFromMock = getUsers().get(0);
        String email = "mail@mail.com";
        Mockito.when(usersRepository.findByEmail("mail@mail.com")).thenReturn(userFromMock);

        Users resultUser = userService.findUserByEmail(email);

        Assert.assertNotNull(resultUser);
        Assert.assertEquals(resultUser.getUserName(), userFromMock.getUserName());
        Assert.assertEquals(resultUser, userFromMock);
    }


    @Test
    public void findAllPagebleUsers() {
        Page<Users> usersFromMock = new PageImpl<>(getUsers());
        PageRequest of = PageRequest.of(0, 2);
        Mockito.when(usersRepository.findAll(of)).thenReturn(usersFromMock);

        Page<Users> users = userService.findAllPagebleUsers(of);

        Assert.assertNotNull(users);
        Assert.assertEquals(users.getTotalPages(), usersFromMock.getTotalPages());
        Assert.assertEquals(users.getTotalElements(), usersFromMock.getTotalElements());
        Assert.assertEquals(users.get().toArray(), usersFromMock.get().toArray());
        Assert.assertEquals(users, usersFromMock);
    }

    @Test
    public void saveUser() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("UserDtoName");
        userDto.setLastName("UserDtoLastName");
        userDto.setPassword("111111");
        userDto.setEmail("mail@mail.com");
        userDto.setLogin("userdto");
        userDto.setPhoneNumber("123456");

        boolean isUserCreated = userService.saveUser(userDto);

        Assert.assertTrue(isUserCreated);
    }

}








