package com.example.doenerbestellung.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.doenerbestellung.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.doenerbestellung.entity.User;
import com.example.doenerbestellung.entity.Role;
import com.example.doenerbestellung.repositories.RoleRepository;
import com.example.doenerbestellung.repositories.UserRepository;

import static java.lang.Thread.sleep;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    public List<User> findAllByDefaultOrderIsNotNull() {
        return this.userRepository.findAllByDefaultOrderIsNotNull();
    }

    public User saveUser(UserDTO userDTO) {
        User userToSave = new User();
        userToSave.setUsername(userDTO.getUsername());
        userToSave.setEmail(userDTO.getEmail());
        userToSave.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        userToSave.setEnabled(false);
        Role userRole = roleRepository.findByRole("ADMIN");
        userToSave.setRoles(new HashSet<>(List.of(userRole)));
        return userRepository.save(userToSave);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = findUserByUsername(username);
        if (user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        });

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}