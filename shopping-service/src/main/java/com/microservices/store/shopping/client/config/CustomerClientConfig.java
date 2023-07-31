package com.microservices.store.shopping.client.config;


import com.microservices.store.shopping.api.config.ApiCustomerConfig;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;


@Configuration
@RequiredArgsConstructor
public class CustomerClientConfig implements RequestInterceptor {

    private final ApiCustomerConfig customerConfig;

    @Bean
    public Request.Options options() {
        return new Request.Options(customerConfig.getConnectTimeout(), customerConfig.getReadTimeout());
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("Authorization");

        requestTemplate.header("Authorization", token);
        requestTemplate.header("traceId", Instant.now().toString());
        requestTemplate.header("Api-Subscription-Key", customerConfig.getApiSubscriptionKey());

    }
}
