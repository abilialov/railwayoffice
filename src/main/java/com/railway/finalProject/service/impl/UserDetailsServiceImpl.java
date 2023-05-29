package com.railway.finalProject.service.impl;

import com.railway.finalProject.models.Users;
import com.railway.finalProject.repo.RoleRepository;
import com.railway.finalProject.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LogManager.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        UserDetails loadedUser;

        try {
            Users client = usersRepository.findByLogin(login);

            String roleUser = roleRepository.findUserRole(Math.toIntExact(client.getId()));

            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roleUser);
            List<GrantedAuthority> grantedAuthorities = Arrays.asList(grantedAuthority);


            loadedUser = new org.springframework.security.core.userdetails.User(
                    client.getLogin(), client.getPassword(),
                    grantedAuthorities);
            LOGGER.debug("User with login = {} login successfully", login);
        } catch (Exception repositoryProblem) {
            LOGGER.warn("Some trouble to login user with login = {}", login);
            throw new InternalAuthenticationServiceException(repositoryProblem.getMessage(),
                    repositoryProblem);
        }

        return loadedUser;
    }
}
