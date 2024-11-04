package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/traffic")
public class TrafficController {

    private static final Logger logger = LoggerFactory.getLogger(TrafficController.class);
    private static final String LOG_FILE_PATH = "logs/traffic.log";

    @GetMapping("/request")
    public ResponseEntity<String> handleTrafficRequest() {
        long startTime = System.nanoTime();  // 요청 시작 시간
        HttpStatus status = HttpStatus.OK;  // 여기서는 항상 200 OK로 응답한다고 가정
        String responseMessage = "Request processed successfully";

        logTrafficInfo(status.value(), responseMessage, startTime);  // 로그 기록 메서드 호출

        return new ResponseEntity<>(responseMessage, status);
    }

    private void logTrafficInfo(int statusCode, String message, long startTime) {
        // 로그 파일 객체 생성
        File logFile = new File(LOG_FILE_PATH);

        long endTime = System.nanoTime();
        long latency = endTime - startTime;  // 지연 시간 계산 (밀리초)

        try (FileWriter writer = new FileWriter(logFile, true)) {  // 이어쓰기 모드로 설정
            // 현재 시간과 응답 코드, 메시지, 지연 시간을 로그에 기록
            String logEntry = String.format("[%s] HTTP Status: %d, Message: %s, Latency: %d ns%n",
                    LocalDateTime.now(), statusCode, message, latency);

            writer.write(logEntry);  // 파일에 로그 작성
            writer.flush();  // 강제로 버퍼 플러시

            logger.info("Logged traffic info: " + logEntry.trim());  // 콘솔 로그

        } catch (IOException e) {
            logger.error("Failed to write traffic log: " + e.getMessage());
        }
    }

}