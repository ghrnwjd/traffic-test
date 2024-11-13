package com.example.demo.controller;

import com.example.demo.model.dto.UserDTO;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
        userService.addUser(userDTO.getUsername(), userDTO.getPassword());
        return new ResponseEntity<>("유저 추가 완료", HttpStatus.OK);
    }
}
