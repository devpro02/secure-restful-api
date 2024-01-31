package com.project.securerestfulapi.ratelimit;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Service;
@Service
public class LeakyBucketService {
    private final RateLimiter rateLimiter = RateLimiter.create(0.2);
    public boolean allowRequest() {
        return rateLimiter.tryAcquire();
    }
}
