package com.project.securerestfulapi.ratelimit;
import com.project.securerestfulapi.exception.TooManyRequestsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LeakyBucketInterceptor implements HandlerInterceptor {
    private final LeakyBucketService leakyBucketService;

    public LeakyBucketInterceptor(LeakyBucketService leakyBucketService) {
        this.leakyBucketService = leakyBucketService;
    }
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        if (leakyBucketService.allowRequest()) {
            return true;
        }
        else {
            throw new TooManyRequestsException("Can only make 1 request every 5 seconds, please wait!");
        }
    }
}
