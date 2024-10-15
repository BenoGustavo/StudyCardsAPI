package com.gorges.studycardsapi.services;

import java.time.Duration;

import org.springframework.stereotype.Service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@Service
public class RateLimiterService {
    private final Bucket bucket;

    public RateLimiterService() {
        Bandwidth limit = Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1)));

        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    public boolean isRateLimited() {
        boolean allowed = bucket.tryConsume(1);
        System.out.println("Allowed: " + allowed + ", Remaining Tokens: " + bucket.getAvailableTokens());
        return !allowed;
    }
}