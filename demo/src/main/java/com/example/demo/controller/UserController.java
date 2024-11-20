package com.example.demo.controller;

import com.example.demo.model.dto.UserDTO;
import com.example.demo.service.LogService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;
    private final LogService logService;
    public UserController(UserService userService, LogService logService) {
        this.userService = userService;
        this.logService = logService;
    }

    @PostMapping("/add/user")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        long startTime = System.nanoTime();  // 요청 시작 시간
        userService.addUser(userDTO.getUsername(), userDTO.getPassword());
        logService.logTrafficInfo(request.getMethod(), request.getRequestURI(),
                String.valueOf(request.getAttribute("username")), startTime);  // 로그 기록 메서드 호출/
        return new ResponseEntity<>("유저 추가 완료", HttpStatus.OK);
    }
}
