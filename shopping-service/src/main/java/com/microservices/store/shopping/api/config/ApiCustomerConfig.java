package com.microservices.store.shopping.api.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource(value = "${spring.system.active}/${spring.application.name}/api-customer-${spring.profiles.active}.yml")
@ConfigurationProperties("api-customer")
@Getter
@Setter
public class ApiCustomerConfig {
    private int connectTimeout;
    private int readTimeout;
    private String apiSubscriptionKey;
}
