package com.sampleProject.sampleProjectRestApi.Authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.refreshExpiration}")
    private long refreshExpiration;

    public String getSecret() {
        return secret;
    }

    public long getExpiration() {
        return expiration;
    }

    public long getRefreshExpiration(){ return refreshExpiration; }
}
