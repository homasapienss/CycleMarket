package com.example.cyclemarket.services;

import com.example.cyclemarket.entities.Role;
import com.example.cyclemarket.entities.User;
import com.example.cyclemarket.exception.notfound.RoleNotFoundException;
import com.example.cyclemarket.exception.notfound.UserNotFoundException;
import com.example.cyclemarket.repos.RoleRepo;
import com.example.cyclemarket.repos.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(String username, String password) {
        if (userRepo.existsByEmail(username)) {
            throw new UserNotFoundException();
        }
        User user = new User();
        user.setEmail(username);
        user.setPasswordHash(passwordEncoder.encode(password));
        Role role = roleRepo.findByName("ROLE_USER").orElseThrow(RoleNotFoundException::new);
        user.getRoles().add(role);
        userRepo.save(user);
    }
}
