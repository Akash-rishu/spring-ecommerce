package com.akash.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.akash.ecommerce.service.CustomUserDetailsService;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    private final JwtFilter jwtFilter;

    // =========================
    // CONSTRUCTOR
    // =========================
    public SecurityConfig(

            CustomUserDetailsService userDetailsService,

            JwtFilter jwtFilter
    ) {

        this.userDetailsService =
                userDetailsService;

        this.jwtFilter =
                jwtFilter;
    }

    // =========================
    // PASSWORD ENCODER
    // =========================
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    // =========================
    // AUTH MANAGER
    // =========================
    @Bean
    public AuthenticationManager authenticationManager(

            AuthenticationConfiguration config

    ) throws Exception {

        return config.getAuthenticationManager();
    }

    // =========================
    // AUTH PROVIDER
    // =========================
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider =

                new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(
                userDetailsService
        );

        authProvider.setPasswordEncoder(
                passwordEncoder()
        );

        return authProvider;
    }

    // =========================
    // SECURITY FILTER CHAIN
    // =========================
    @Bean
    public SecurityFilterChain securityFilterChain(

            HttpSecurity http

    ) throws Exception {

        http

            // =========================
            // CORS
            // =========================
            .cors(cors -> {})

            // =========================
            // DISABLE CSRF
            // =========================
            .csrf(csrf -> csrf.disable())

            // =========================
            // STATELESS SESSION
            // =========================
            .sessionManagement(session ->

                    session.sessionCreationPolicy(

                            SessionCreationPolicy.STATELESS
                    )
            )

            // =========================
            // ROUTE AUTHORIZATION
            // =========================
            .authorizeHttpRequests(auth -> auth

                    // =========================
                    // PUBLIC AUTH
                    // =========================
                    .requestMatchers(

                            "/api/auth/**"

                    ).permitAll()

                    // =========================
                    // PUBLIC IMAGES
                    // =========================
                    .requestMatchers(

                            "/images/**"

                    ).permitAll()

                    // =========================
                    // OPTIONS REQUESTS
                    // =========================
                    .requestMatchers(

                            HttpMethod.OPTIONS,

                            "/**"

                    ).permitAll()

                    // =========================
                    // PUBLIC PRODUCTS
                    // =========================
                    .requestMatchers(

                            HttpMethod.GET,

                            "/api/products/**"

                    ).permitAll()

                    // =========================
                    // PUBLIC CATEGORIES
                    // =========================
                    .requestMatchers(

                            HttpMethod.GET,

                            "/api/categories/**"

                    ).permitAll()

                    // =========================
                    // ADDRESS
                    // =========================
                    .requestMatchers(

                            "/api/address/**"

                    ).hasAnyRole(

                            "USER",

                            "ADMIN"
                    )

                    // =========================
                    // CHECKOUT
                    // =========================
                    .requestMatchers(

                            "/api/orders/checkout"

                    ).hasRole("USER")

                    // =========================
                    // CART
                    // =========================
                    .requestMatchers(

                            "/api/cart/**"

                    ).hasRole("USER")

                    // =========================
                    // PRODUCT ADMIN
                    // =========================
                    .requestMatchers(

                            HttpMethod.POST,

                            "/api/products/**"

                    ).hasRole("ADMIN")

                    .requestMatchers(

                            HttpMethod.PUT,

                            "/api/products/**"

                    ).hasRole("ADMIN")

                    .requestMatchers(

                            HttpMethod.DELETE,

                            "/api/products/**"

                    ).hasRole("ADMIN")

                    // =========================
                    // CATEGORY ADMIN
                    // =========================
                    .requestMatchers(

                            HttpMethod.POST,

                            "/api/categories/**"

                    ).hasRole("ADMIN")

                    .requestMatchers(

                            HttpMethod.DELETE,

                            "/api/categories/**"

                    ).hasRole("ADMIN")

                    // =========================
                    // ORDERS
                    // =========================
                    .requestMatchers(

                            HttpMethod.POST,

                            "/api/orders/**"

                    ).hasAnyRole(

                            "USER",

                            "ADMIN"
                    )

                    .requestMatchers(

                            HttpMethod.GET,

                            "/api/orders/**"

                    ).hasAnyRole(

                            "USER",

                            "ADMIN"
                    )

                    .requestMatchers(

                            HttpMethod.PUT,

                            "/api/orders/**"

                    ).hasRole("ADMIN")

                    // =========================
                    // EVERYTHING ELSE
                    // =========================
                    .anyRequest()

                    .authenticated()
            )

            // =========================
            // JWT FILTER
            // =========================
            .addFilterBefore(

                    jwtFilter,

                    UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}