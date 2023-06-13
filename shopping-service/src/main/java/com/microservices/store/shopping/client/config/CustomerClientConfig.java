package com.microservices.store.shopping.client.config;


import com.microservices.store.shopping.api.config.ApiCustomerConfig;
import feign.Request;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;


@Configuration
@RequiredArgsConstructor
public class CustomerClientConfig {

    private final ApiCustomerConfig customerConfig;
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String token = requestTemplate.queries().get("token").stream().findFirst().orElse(null);
            requestTemplate.header("Authorization", token);
            requestTemplate.header("traceId",  Instant.now().toString());
            requestTemplate.header("Api-Subscription-Key", customerConfig.getApiSubscriptionKey());
        };
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(customerConfig.getConnectTimeout(), customerConfig.getReadTimeout());
    }
}
