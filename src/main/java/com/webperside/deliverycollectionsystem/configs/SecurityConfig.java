package com.webperside.deliverycollectionsystem.configs;

import com.webperside.deliverycollectionsystem.exception.BaseException;
import com.webperside.deliverycollectionsystem.filters.AuthorizationFilter;
import com.webperside.deliverycollectionsystem.model.enums.response.ErrorResponseMessages;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
                                                         PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthorizationFilter authorizationFilter,
                                                   AuthEntryPoint authEntryPoint,
                                                   AuthAccessDeniedHandler authAccessDeniedHandler,
                                                   AuthenticationProvider authenticationProvider) throws Exception {
        return http
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(request -> {
                    // Swagger UI
                    request.requestMatchers(
                            "/v3/api-docs/**",           // OpenAPI docs
                            "/swagger-ui/**",
                            "/swagger-ui.html"
                            // Swagger UI main
                    ).permitAll();

                    // Auth URLs
                    request.requestMatchers("/v1/auth/logout").authenticated();
                    request.requestMatchers("/v1/auth/refresh").permitAll();
                    request.requestMatchers("/v1/auth/me").permitAll();
                    request.requestMatchers("/v1/auth/**").anonymous();
                    request.anyRequest().permitAll();

                })
                .exceptionHandling(eh -> eh
                        .authenticationEntryPoint(authEntryPoint)
                        .accessDeniedHandler(authAccessDeniedHandler)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
                .build();


    }

    @Component
    @Slf4j
    public static class AuthEntryPoint implements AuthenticationEntryPoint {

        @Qualifier("handlerExceptionResolver")
        private final HandlerExceptionResolver resolver;

        public AuthEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
            this.resolver = resolver;
        }

        @Override
        public void commence(HttpServletRequest request,
                             HttpServletResponse response,
                             AuthenticationException authException) {

            resolver.resolveException(request,response, null, BaseException.of(ErrorResponseMessages.FORBIDDEN));
        }
    }

    @Component
    @Slf4j
    public static class AuthAccessDeniedHandler implements AccessDeniedHandler {

        @Qualifier("handlerExceptionResolver")
        private final HandlerExceptionResolver resolver;

        public AuthAccessDeniedHandler(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
            this.resolver = resolver;
        }

        @Override
        public void handle(HttpServletRequest request,
                           HttpServletResponse response,
                           AccessDeniedException accessDeniedException) {

            resolver.resolveException(request, response, null, BaseException.of(ErrorResponseMessages.FORBIDDEN));
        }
    }
}
