package com.example.dockerspringboot.repository;

import com.example.dockerspringboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}