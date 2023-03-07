package com.ivan.apigateway;

import org.springframework.cloud.gateway.filter.factory.SetPathGatewayFilterFactory;
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
                .route("order-service", r -> r.path("/api/v1/order")
                        .filters(f -> f.filter(authGatewayFilterFactory.apply(new AuthGatewayFilterFactory.Config())))
                        .uri("lb://order-service"))
                .route("inventory-service", r -> r.path("/api/v1/inventory")
                        .filters(f -> f.filter(authGatewayFilterFactory.apply(new AuthGatewayFilterFactory.Config())))
                        .uri("lb://inventory-service"))
                .route("user-service", r -> r.path("/api/v1/user")
                        .uri("lb://user-service"))
                .route("user-service", r -> r.path("/api/v1/auth/**")
                        .uri("lb://user-service"))
                .route("discovery-server", r -> r.path("/")
                        .filters(f -> f.setPath("/"))
                        .uri("http://localhost:8761"))
                .build();
    }
}


