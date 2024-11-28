package com.hoyoung.traffic.controller;

import com.hoyoung.traffic.data.TrafficLog;
import com.hoyoung.traffic.service.BucketService;
import com.hoyoung.traffic.service.LogService;
import com.hoyoung.traffic.service.UserService;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@Slf4j
public class TrafficController {

    private final LogService logService;
    private final UserService userService;
    private final BucketService bucketService;
    public TrafficController(LogService logService, UserService userService, BucketService bucketService) {
        this.logService = logService;
        this.userService = userService;
        this.bucketService = bucketService;
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

    @GetMapping("/access")
    public ResponseEntity bucketAccess(HttpServletRequest request) {
        Bucket bucket = bucketService.resolveBucket(request);
        long startTime = System.currentTimeMillis();  // 요청 시작 시간

        TrafficLog trafficLog = TrafficLog.builder().httpMethod(request.getMethod())
                .remoteAddr(request.getRemoteAddr())
                .build();

        if (bucket.tryConsume(1)) { // 1개 사용 요청
            trafficLog.setStatusCode(HttpStatus.OK.toString());
            trafficLog.setServerResp("[정상응답] 잔여토큰 : " + bucket.getAvailableTokens());
            logService.writeLog(trafficLog, startTime);
            return ResponseEntity.ok("[정상응답] 잔여토큰 : " + bucket.getAvailableTokens());
        } else {
            trafficLog.setStatusCode(HttpStatus.TOO_MANY_REQUESTS.toString());
            trafficLog.setServerResp("[비정상응답] 트래픽 초과");
            logService.writeLog(trafficLog, startTime);
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("[비정상응답] 트래픽 초과");

        }
    }

}