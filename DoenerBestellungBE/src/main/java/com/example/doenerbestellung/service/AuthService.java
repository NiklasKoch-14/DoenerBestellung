package com.example.doenerbestellung.service;

import com.example.doenerbestellung.controller.AuthBody;
import com.example.doenerbestellung.dto.UserDTO;
import com.example.doenerbestellung.entity.User;
import com.example.doenerbestellung.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private UserRepository users;

    public Map<Object, Object> login(AuthBody data) {
        try {
            String username = data.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(username, this.users.findUserByUsername(username).getRoles());
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            log.info("Session mit username {} und token {} wurde erstellt", username, token);
            return model;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email/password supplied ", e);
        }
    }

    public Map<Object, Object> register(UserDTO userDTO) {
        User userExists = userService.findUserByUsername(userDTO.getUsername());
        if (userExists != null) {
            throw new BadCredentialsException("User with username: " + userDTO.getUsername() + " already exists");
        }
        User savedUser = userService.saveUser(userDTO);
        Map<Object, Object> model = new HashMap<>();
        model.put("message", "User with username " + savedUser.getUsername() + " registered successfully");
        return model;
    }

}
