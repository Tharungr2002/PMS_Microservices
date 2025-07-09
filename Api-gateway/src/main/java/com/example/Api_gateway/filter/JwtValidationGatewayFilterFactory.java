package com.example.Api_gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

@Component
public class JwtValidationGatewayFilterFactory extends
        AbstractGatewayFilterFactory<Object> {

    private WebClient webclient;

    public JwtValidationGatewayFilterFactory(WebClient.Builder webBuilder) {
        this.webclient = webBuilder.baseUrl("http://localhost:8081").build();
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if(token == null || !token.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

           return webclient.get().uri("/validate").header(HttpHeaders.AUTHORIZATION,token)
                    .retrieve().toBodilessEntity().flatMap(response -> chain.filter(exchange))
                   .onErrorResume(WebClientException.class, ex -> {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                   });

        };
    }
}
