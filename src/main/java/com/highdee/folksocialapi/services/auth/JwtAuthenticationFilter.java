package com.highdee.folksocialapi.services.auth;

import com.highdee.folksocialapi.models.auth.User;
import com.highdee.folksocialapi.services.JwtService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailService userDetailService;
    private final JwtService jwtService;


    public JwtAuthenticationFilter(UserDetailService userDetailService, JwtService jwtService) {
        this.userDetailService = userDetailService;
        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if(header != null && header.startsWith("Bearer ") && SecurityContextHolder.getContext().getAuthentication() == null){

            String jwt = header.substring(7);
            String email = jwtService.extractEmail(jwt);

            if(jwtService.validateToken(jwt,email )){
                UserDetails userDetails = userDetailService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
