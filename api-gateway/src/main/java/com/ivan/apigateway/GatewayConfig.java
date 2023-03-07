package com.ivan.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GatewayConfig {

    private final AuthGatewayFilterFactory authGatewayFilterFactory;

    public GatewayConfig(@Lazy AuthGatewayFilterFactory authGatewayFilterFactory) {
        this.authGatewayFilterFactory = authGatewayFilterFactory;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product-service", r -> r.path("/api/v1/product")
                        .filters(f -> f.filter(authGatewayFilterFactory.apply(new AuthGatewayFilterFactory.Config())))
                        .uri("lb://product-service"))
                .build();
    }
}


