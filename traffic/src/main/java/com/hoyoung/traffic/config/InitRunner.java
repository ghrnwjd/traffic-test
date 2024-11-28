package com.hoyoung.traffic.config;

import com.hoyoung.traffic.data.User;
import com.hoyoung.traffic.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitRunner implements CommandLineRunner {

    private final UserRepository userRepository;

    public InitRunner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        int currentUserCount = userRepository.findAll().size();

        if(currentUserCount < 3000) {
            String username = "홍길동";
            String password;

            for(int i = 0; i < 1000; i++) {
                password = String.valueOf(Math.round(Math.random() * 100));
                User user = User.builder().username(username + password).password(password).build();
                userRepository.save(user);
            }
            currentUserCount += 1000;
        }

        log.info("current user: " + currentUserCount);

    }
}
