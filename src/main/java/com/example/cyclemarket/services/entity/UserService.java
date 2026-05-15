package com.example.cyclemarket.services.entity;

import com.example.cyclemarket.entities.User;
import com.example.cyclemarket.exception.notfound.UserNotFoundException;
import com.example.cyclemarket.repos.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public User getUserById(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public User getByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }
}
