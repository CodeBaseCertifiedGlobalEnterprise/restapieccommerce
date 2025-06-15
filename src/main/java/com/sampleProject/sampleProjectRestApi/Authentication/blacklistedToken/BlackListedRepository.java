package com.sampleProject.sampleProjectRestApi.Authentication.blacklistedToken;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlackListedRepository extends JpaRepository<BlacklistedToken, Long> {
    Optional<BlacklistedToken> findByToken(String token);
}
