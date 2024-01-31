package com.project.securerestfulapi.ratelimit;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class RateLimitingConfig implements WebMvcConfigurer {
    private final LeakyBucketInterceptor leakyBucketInterceptor;
    private final TokenBucketInterceptor tokenBucketInterceptor;

    public RateLimitingConfig(LeakyBucketInterceptor leakyBucketInterceptor, TokenBucketInterceptor tokenBucketInterceptor) {
        this.leakyBucketInterceptor = leakyBucketInterceptor;
        this.tokenBucketInterceptor = tokenBucketInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenBucketInterceptor)
                .addPathPatterns("/hello");
        registry.addInterceptor(leakyBucketInterceptor)
                .addPathPatterns("/hi");
    }
}
