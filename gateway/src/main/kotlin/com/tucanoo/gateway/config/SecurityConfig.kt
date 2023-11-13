package com.tucanoo.gateway.config

import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.web.reactive.function.client.WebClient


@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun defaultSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
            .csrf { obj -> obj.disable() }
            .authorizeExchange { obj ->
                obj
                    .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Allow preflight requests
                    .pathMatchers("/actuator/**").permitAll()
                    .pathMatchers("/eureka/**").permitAll()
                    .pathMatchers("/oauth2/**").permitAll()
                    .pathMatchers("/login/**").permitAll()
                    .pathMatchers("/error/**").permitAll()
                    .pathMatchers("/webjars/**").permitAll()
                    .pathMatchers("/config/**").permitAll()
                    .anyExchange().authenticated()
            }
            .oauth2ResourceServer { obj -> obj.jwt(Customizer.withDefaults()) }

        val chain = http.build()
        return chain
    }

    @Bean
    @LoadBalanced
    fun loadBalancedWebClientBuilder(): WebClient.Builder {
        return WebClient.builder()
    }
}