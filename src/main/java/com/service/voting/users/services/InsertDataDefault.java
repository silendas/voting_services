package com.service.voting.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.service.voting.users.model.Role;
import com.service.voting.users.model.User;
import com.service.voting.users.repository.RoleRepository;
import com.service.voting.users.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class InsertDataDefault {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void insertDataDefault() {
        if (roleRepository.count() == 0) {
            String[] roleNames = { "ADMINISTRATOR", "USER" };
 
            for (String roleName : roleNames) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
                System.out.println("Inserting roles...");
            }
        }

        if (userRepository.count() == 0) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("2024$SAnks1JU4ng#"));
            user.setName("Administrator");
            user.setRole(getRole(1L));
            user.setEmail("admin@gmail.com");
            user.setActive(true);
            userRepository.save(user);
            System.out.println("Inserting Administrator...");
        }
    }

    public Role getRole(Long id) {
        return roleRepository.findById(id).orElse(null);
    }
}