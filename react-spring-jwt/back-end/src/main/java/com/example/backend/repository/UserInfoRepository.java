package com.example.backend.repository;

import com.example.backend.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
    Optional<UserInfo> findByUsername(String useranme);
    Optional<UserInfo> findByEmail(String email);
}
