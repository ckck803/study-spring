package com.example.sampleoauth2.repository;

import com.example.sampleoauth2.domain.OAuth2Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuth2Repository extends JpaRepository<OAuth2Entity, Long> {
    Optional<OAuth2Entity> findOAuth2EntityByUsername (String username);
}
