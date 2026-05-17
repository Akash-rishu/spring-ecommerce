package com.akash.ecommerce.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.akash.ecommerce.service.CustomUserDetailsService;
import com.akash.ecommerce.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain chain

    ) throws ServletException, IOException {

        // =========================
        // SKIP AUTH APIs
        // =========================
        String path =
                request.getServletPath();

        if (

                path.startsWith("/api/auth")

                ||

                path.startsWith("/images")

        ) {

            chain.doFilter(
                    request,
                    response
            );

            return;
        }

        // =========================
        // GET AUTH HEADER
        // =========================
        final String authHeader =
                request.getHeader(
                        "Authorization"
                );

        String token = null;

        String email = null;

        // =========================
        // EXTRACT TOKEN
        // =========================
        if (

                authHeader != null

                &&

                authHeader.startsWith(
                        "Bearer "
                )
        ) {

            token =
                    authHeader.substring(7);

            try {

                email =
                        jwtUtil.extractEmail(
                                token
                        );

            } catch (Exception e) {

                System.out.println(
                        "❌ Invalid or expired JWT token"
                );
            }
        }

        // =========================
        // AUTHENTICATE USER
        // =========================
        if (

                email != null

                &&

                SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null

        ) {

            UserDetails userDetails =

                    userDetailsService
                            .loadUserByUsername(
                                    email
                            );

            // VALID TOKEN
            if (

                    jwtUtil.validateToken(
                            token,
                            email
                    )

            ) {

                System.out.println(
                        "✅ Valid JWT for: "
                        + email
                );

                UsernamePasswordAuthenticationToken authToken =

                        new UsernamePasswordAuthenticationToken(

                                userDetails,

                                null,

                                userDetails.getAuthorities()
                        );

                authToken.setDetails(

                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
            }
        }

        // =========================
        // CONTINUE REQUEST
        // =========================
        chain.doFilter(
                request,
                response
        );
    }
}