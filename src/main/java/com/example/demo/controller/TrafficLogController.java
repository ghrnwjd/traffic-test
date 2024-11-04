package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class TrafficLogController {

    private static final String LOG_FILE_PATH = "logs/traffic.log";

    @GetMapping("/traffic")
    public List<String> getTrafficLogs() {
        List<String> logs = new ArrayList<>();
        File logFile = new File(LOG_FILE_PATH);

        if (logFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    logs.add(line);
                }
            } catch (IOException e) {
                logs.add("Error reading log file: " + e.getMessage());
            }
        } else {
            logs.add("Log file does not exist.");
        }

        return logs;
    }
}
