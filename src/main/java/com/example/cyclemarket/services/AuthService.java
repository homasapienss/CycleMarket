package com.example.cyclemarket.services;

import com.example.cyclemarket.entities.Role;
import com.example.cyclemarket.entities.User;
import com.example.cyclemarket.exception.alreadyxists.UserAlreadyExists;
import com.example.cyclemarket.exception.notfound.RoleNotFoundException;
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
        createUserWithRole(username, password, "ROLE_CUSTOMER");
    }
    @Transactional
    public User registerEmployee(String username, String password) {
        return createUserWithRole(username, password, "ROLE_MANAGER");
    }
    private User createUserWithRole(String email, String password, String roleName) {
        if (userRepo.existsByEmail(email)) {
            throw new UserAlreadyExists();
        }

        Role role = roleRepo.findByName(roleName)
                .orElseThrow(RoleNotFoundException::new);

        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.getRoles().add(role);

        return userRepo.save(user);
    }
}
