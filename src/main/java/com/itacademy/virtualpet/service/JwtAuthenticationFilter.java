package com.itacademy.virtualpet.service;

import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements WebFilter {

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private ReactiveUserDetailsService userDetailsService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        System.out.println("Checking Authorization Header: " + authorizationHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            System.out.println("Extracted JWT: " + jwt);

            try {
                String username = jwtUtil.extractUsername(jwt);
                System.out.println("Extracted Username: " + username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    return userDetailsService.findByUsername(username)
                            .flatMap(userDetails -> {
                                if (jwtUtil.validateToken(jwt, userDetails)) {
                                    System.out.println("JWT validated successfully");
                                    UsernamePasswordAuthenticationToken authToken =
                                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                                    SecurityContextHolder.getContext().setAuthentication(authToken);
                                }
                                return chain.filter(exchange);
                            });
                }
            } catch (Exception e) {
                System.out.println("Error parsing JWT: " + e.getMessage());
            }
        } else {
            System.out.println("No Authorization header or invalid token");
        }
        return chain.filter(exchange);
    }
}
