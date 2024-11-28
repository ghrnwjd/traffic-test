package com.hoyoung.traffic.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executor;

import com.hoyoung.traffic.data.TrafficLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    private static final Logger logger = LoggerFactory.getLogger(LogService.class);
    private static final String LOG_FILE_PATH = "logs/traffic.log";

    private final Executor executor;

    public LogService(@Qualifier("taskExecutor") Executor executor) {
        this.executor = executor;
    }

    public void logTrafficInfo(String statusCode, String message, String username, long startTime) {
        // 로그 파일 객체 생성
        File logFile = new File(getTodayLogDate());

        long endTime = System.nanoTime();
        long latency = endTime - startTime;  // 지연 시간 계산 (밀리초)

        try (FileWriter writer = new FileWriter(logFile, true)) {  // 이어쓰기 모드로 설정
            // 현재 시간과 응답 코드, 메시지, 지연 시간을 로그에 기록
            String logEntry = String.format("쓰레드 이름 : %s ,[%s] HTTP 메소드: %s, 요청 URI: %s, 가입 유저 이름: %s Latency: %d ns%n",
                    Thread.currentThread().getName(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일 HH시 mm분 ss초"))
                    ,statusCode, message, username, latency);

            writer.write(logEntry);  // 파일에 로그 작성
            writer.flush();  // 강제로 버퍼 플러시

            logger.info("Logged traffic info: " + logEntry.trim());  // 콘솔 로그

        } catch (IOException e) {
            logger.error("Failed to write traffic log: " + e.getMessage());
        }
    }

    public void writeLog(TrafficLog trafficLog, long startTime) {
        File logFile = new File(getTodayLogDate());

        long endTime = System.currentTimeMillis();
        long latency = endTime - startTime;  // 지연 시간 계산 (밀리초)

        try (FileWriter writer = new FileWriter(logFile, true)) {  // 이어쓰기 모드로 설정
            // 현재 시간과 응답 코드, 메시지, 지연 시간을 로그에 기록
            String log = "[" +LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss")) + "] " +
                    trafficLog.print() + " latency : " + latency + "ms\n";
            writer.write(log);  // 파일에 로그 작성
            writer.flush();  // 강제로 버퍼 플러시

            logger.info("Logged traffic info: " + log.trim());  // 콘솔 로그

        } catch (IOException e) {
            logger.error("Failed to write traffic log: " + e.getMessage());
        }
    }

    private String getTodayLogDate() {
        return "logs/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy년_MM월_dd일")) + "_traffic.log";
    }
}
