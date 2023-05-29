package com.railway.finalProject.controllers;


import com.railway.finalProject.dto.CaptchaResponceDto;
import com.railway.finalProject.dto.UserDto;
import com.railway.finalProject.utils.UserUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Controller
public class UserController {

    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    /**
     * User Controller
     *
     *
     */

    @Autowired
    private UserUtil userUtil;

    @Value("${google.recaptcha.key.secret}")
    private String secret;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/userslist")
    public String userInfo(@RequestParam (value = "page", required = false, defaultValue = "0") Integer page,
                           @RequestParam(value = "limit", defaultValue = "3") int limit, Model model){
        return userUtil.userList(page, limit, model);
    }

    @GetMapping("/registration")
    public String registration(Model model){
        return userUtil.registration(model);
    }

    // handler method to handle user registration form submit request
    @PostMapping("/registration/save")
    public String registration(@RequestParam("g-recaptcha-response") String captchaResponce,
                               @Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        String url = String.format(CAPTCHA_URL, secret, captchaResponce);
        CaptchaResponceDto responce = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponceDto.class);

        if(!responce.isSuccess()){
            model.addAttribute("captchaError", "Fill captcha");
        }

        return userUtil.registrationSave(responce, userDto, result, model);
    }

}





























