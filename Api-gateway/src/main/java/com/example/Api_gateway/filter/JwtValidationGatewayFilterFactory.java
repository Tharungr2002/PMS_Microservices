package com.example.Api_gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.Arrays;
import java.util.List;

@Component
public class JwtValidationGatewayFilterFactory extends
        AbstractGatewayFilterFactory<JwtValidationGatewayFilterFactory.Config> {

    @Value("${app.api-key}")
    private  String API_KEY;


    private WebClient webclient;

    public JwtValidationGatewayFilterFactory(WebClient.Builder webBuilder) {
        super(Config.class);
        this.webclient = webBuilder.baseUrl("http://localhost:8081").build();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if(token == null || !token.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

           return webclient.get().uri("/auth/validate").header(HttpHeaders.AUTHORIZATION,token)
                   .header("X-Api-key",API_KEY)
                    .retrieve().bodyToMono(String.class).flatMap(responsebody ->{

                       ObjectMapper objectMapper = new ObjectMapper();
                       try {
                           JsonNode json =  objectMapper.readTree(responsebody);
                           String role = json.get("role").asText();

                           List<String> roles = Arrays.asList(config.getAllowedRoles().split(","));

                           System.out.println("---------"+ roles.toString() + "-------" );
                           System.out.println("--------"+ !roles.contains(role));

                           if(!roles.contains(role)) {
                               exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                               return exchange.getResponse().setComplete();
                           }
                       } catch (JsonProcessingException e) {
                           throw new RuntimeException(e);
                       }
                       return chain.filter(exchange);
                   });

        };
    }

    public static class Config {
        private String allowedRoles;

        public String getAllowedRoles() { return allowedRoles; }
        public void setAllowedRoles(String allowedRoles) { this.allowedRoles = allowedRoles; }
    }

}
