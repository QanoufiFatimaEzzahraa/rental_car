package com.springboot.car_rental.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springboot.car_rental.service.CustomUserDetailsService;
import com.springboot.car_rental.util.JwtUtil;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

import java.io.IOException;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        final String email ;
        final String jwt ;

        if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

            jwt = authHeader.substring(7);
            email = jwtUtil.extractEmail(jwt);
         
        

     // Si l'email est extrait et qu'il n'y a pas encore d'authentification dans le contexte de sécurité
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
        	UserDetails userDetails = customUserDetailsService.userDetailsService().loadUserByUsername(email);
        	 if (jwtUtil.isTokenValid(jwt, userDetails)) {
                 SecurityContext context = SecurityContextHolder.createEmptyContext();
                 UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                 authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                 context.setAuthentication(authenticationToken);
                 SecurityContextHolder.setContext(context);
             }
        }


        filterChain.doFilter(request, response);
    }
}

