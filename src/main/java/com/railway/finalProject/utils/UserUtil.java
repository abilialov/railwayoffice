package com.railway.finalProject.utils;


import com.railway.finalProject.dto.CaptchaResponceDto;
import com.railway.finalProject.dto.UserDto;
import com.railway.finalProject.models.Users;
import com.railway.finalProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.stream.IntStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class UserUtil {

    private static final Logger LOGGER = LogManager.getLogger(UserUtil.class);

    @Autowired
    UserService userService;

    public String userList(Integer page, int limit, Model model) {

        LOGGER.info("Load user list");

        Page<Users> userList = userService.findAllPagebleUsers(PageRequest.of(page, limit));
        model.addAttribute("numbers", IntStream.range(0,userList.getTotalPages()).toArray());
        model.addAttribute("userlist",  userList);
        return "userslist";
    }

    public String registration(Model model) {
        LOGGER.info("Registration form");
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "registration";
    }

    public String registrationSave(CaptchaResponceDto responce, UserDto userDto, BindingResult result, Model model) {

        LOGGER.debug("Save new user with login = {}", userDto.getLogin());

        Users existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            LOGGER.warn("There is already an account registered with the same email = {}", userDto.getEmail());
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        Users existingUserByLogin = userService.findUserByLogin(userDto.getLogin());

        if(existingUserByLogin != null && existingUserByLogin.getLogin() != null && !existingUserByLogin.getLogin().isEmpty()){
            LOGGER.warn("There is already an account registered with the same login = {}", userDto.getLogin());
            result.rejectValue("login", null,
                    "There is already an account registered with the same login");
        }

        Users existingUserByPhone = userService.findUserByPhone(userDto.getPhoneNumber());

        if(existingUserByPhone != null){
            LOGGER.warn("There is already an account registered with the same Phone Number = {} ", userDto.getPhoneNumber());
            result.rejectValue("phoneNumber", null,
                    "There is already an account registered with the same Phone Number");
        }

        if(result.hasErrors() || !responce.isSuccess()){
            LOGGER.warn("There input fields or captcha error is validation");
            model.addAttribute("user", userDto);
            return "/registration";
        }

        userService.saveUser(userDto);
        LOGGER.debug("User with login = {} added successfully", userDto.getLogin());
        return "redirect:/registration?success";
    }
}
