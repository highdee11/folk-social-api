package com.highdee.folksocialapi.services.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.highdee.folksocialapi.dto.response.RestResponse;
import com.highdee.folksocialapi.enums.ResponseCode;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailService userDetailService;
    private final JwtService jwtService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    public JwtAuthenticationFilter(UserDetailService userDetailService, JwtService jwtService) {
        this.userDetailService = userDetailService;
        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
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
        }catch (ExpiredJwtException ex){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(
                    new RestResponse<Object>(ResponseCode.TOKEN_EXPIRE.getCode(), ResponseCode.TOKEN_EXPIRE.getMessage(), null)
            ));
            response.getWriter().flush();
        }
    }
}
