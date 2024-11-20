package com.example.demo.controller;

import com.example.demo.service.LogService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/traffic")
public class TrafficController {

    private final LogService logService;

    public TrafficController(LogService logService) {
        this.logService = logService;
    }


    @GetMapping("/get")
    public ResponseEntity<String> handleTrafficGetRequest(HttpServletRequest request) {

        long startTime = System.nanoTime();  // 요청 시작 시간

        logService.logTrafficInfo(request.getMethod(), request.getRequestURI(), String.valueOf(request.getAttribute("username")), startTime);  // 로그 기록 메서드 호출/
        return new ResponseEntity<>("확인 완료", HttpStatusCode.valueOf(200));
    }
    @GetMapping("/post")
    public ResponseEntity<String> handleTrafficPostRequest(HttpServletRequest request) {

        long startTime = System.nanoTime();  // 요청 시작 시간

        logService.logTrafficInfo(request.getMethod(), request.getRequestURI(), String.valueOf(request.getAttribute("username")), startTime);  // 로그 기록 메서드 호출/
        return new ResponseEntity<>("확인 완료", HttpStatusCode.valueOf(200));
    }





}