package com.example.cyclemarket.exception.notfound;

public class UserNotFoundException extends NotFoundException  {
    public UserNotFoundException() {
        super("User not found exception");
    }
}
