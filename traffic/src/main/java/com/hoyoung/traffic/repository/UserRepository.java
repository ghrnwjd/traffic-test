package com.hoyoung.traffic.repository;

import com.hoyoung.traffic.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
