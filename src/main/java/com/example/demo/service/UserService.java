package com.example.demo.service;


import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(String username, String password) {
        User user = User.builder().username(username)
                .password(password).build();

        userRepository.save(user);
    }
}
