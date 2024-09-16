package com.gorges.studycardsapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gorges.studycardsapi.filter.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {
        @Autowired
        AuthenticationProvider authenticationProvider;
        @Autowired
        JwtAuthenticationFilter jwtAuthenticationFilter;

        private final String[] WHITE_LIST = {
                        "/api/auth/**", "/", "/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**",
                        "/swagger-ui/**",
        };

        private final static String[] ONLY_ADMIN_ALLOWED_ROUTES = {
                        "/api/user/admin/**",
        };

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                                .requestMatchers(WHITE_LIST).permitAll()
                                                .requestMatchers(ONLY_ADMIN_ALLOWED_ROUTES).hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(sessionManagement -> sessionManagement
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public WebMvcConfigurer corsConfigurer() {
                return new WebMvcConfigurer() {
                        @Override
                        public void addCorsMappings(@NonNull CorsRegistry registry) {
                                registry.addMapping("/**")
                                                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                                                .allowedOrigins("*")
                                                .allowedHeaders("*");
                        }
                };
        }
}