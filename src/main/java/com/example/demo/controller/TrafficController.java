package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executor;


@RestController
@RequestMapping("/api/traffic")
public class TrafficController {

    private static final Logger logger = LoggerFactory.getLogger(TrafficController.class);
    private static final String LOG_FILE_PATH = "logs/traffic.log";

    private final Executor executor;

    public TrafficController(@Qualifier("taskExecutor") Executor executor) {
        this.executor = executor;
    }

    @GetMapping("/get")
    public ResponseEntity<String> handleTrafficGetRequest(HttpServletRequest request) {

        long startTime = System.nanoTime();  // 요청 시작 시간

        logTrafficInfo(request.getMethod(), request.getRequestURI(), startTime);  // 로그 기록 메서드 호출/
        return new ResponseEntity<>("확인 완료", HttpStatusCode.valueOf(200));
    }
    @GetMapping("/post")
    public ResponseEntity<String> handleTrafficPostRequest(HttpServletRequest request) {

        long startTime = System.nanoTime();  // 요청 시작 시간

        logTrafficInfo(request.getMethod(), request.getRequestURI(), startTime);  // 로그 기록 메서드 호출/
        return new ResponseEntity<>("확인 완료", HttpStatusCode.valueOf(200));
    }

    private void logTrafficInfo(String statusCode, String message, long startTime) {
        // 로그 파일 객체 생성
        File logFile = new File(getTodayLogDate());

        long endTime = System.nanoTime();
        long latency = endTime - startTime;  // 지연 시간 계산 (밀리초)

        try (FileWriter writer = new FileWriter(logFile, true)) {  // 이어쓰기 모드로 설정
            // 현재 시간과 응답 코드, 메시지, 지연 시간을 로그에 기록
            String logEntry = String.format("쓰레드 이름 : %s ,[%s] HTTP 메소드: %s, 요청 URI: %s, Latency: %d ns%n",
                    Thread.currentThread().getName(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일 HH시 mm분 ss초"))
                    ,statusCode, message, latency);

            writer.write(logEntry);  // 파일에 로그 작성
            writer.flush();  // 강제로 버퍼 플러시

            logger.info("Logged traffic info: " + logEntry.trim());  // 콘솔 로그

        } catch (IOException e) {
            logger.error("Failed to write traffic log: " + e.getMessage());
        }
    }

    private String getTodayLogDate() {
        return "logs/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy년_MM월_dd일")) + "_traffic.log";
    }

}