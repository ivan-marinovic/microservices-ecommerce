package com.ivan.apigateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthGatewayFilterFactory implements GatewayFilterFactory<AuthGatewayFilterFactory.Config> {

    private final RestTemplate restTemplate;

    public AuthGatewayFilterFactory(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new RequestFilter(restTemplate);
    }

    @Override
    public Config newConfig() {
        return new Config();
    }

    @Override
    public Class<Config> getConfigClass() {
        return Config.class;
    }

    public static class Config {
    }
}

