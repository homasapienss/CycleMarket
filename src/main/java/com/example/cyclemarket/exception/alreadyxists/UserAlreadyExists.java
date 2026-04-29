package com.example.cyclemarket.exception.alreadyxists;

public class UserAlreadyExists extends AlreadyExistsException{
    public UserAlreadyExists() {
        super("User already exists");
    }
}
