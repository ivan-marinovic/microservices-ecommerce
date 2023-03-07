package com.ivan.apigateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


public class RequestFilter implements GatewayFilter {

    private final RestTemplate restTemplate;

    public RequestFilter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authorizationHeader);

        ResponseEntity<Boolean> response = restTemplate.exchange(
                "http://localhost:8080/api/v1/auth",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Boolean.class);

        Boolean result = response.getBody();

        if(result) {
            return chain.filter(exchange);
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }
    }
}



