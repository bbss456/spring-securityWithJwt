package com.pwang.projoect.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        String refreshToken = jwtTokenProvider.resolveRefreshToken((HttpServletRequest) request);

        if (token != null && jwtTokenProvider.validateToken(request, token)) {
            try {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (RuntimeException error) {
                request.setAttribute("exception", "MalformedJwtException");
            }
        }

//        if (refreshToken != null && jwtTokenProvider.validateRefreshToken(request, refreshToken)) {
//            try {
//                //Authentication authentication = jwtTokenProvider.getAuthenticationRefreshToken(refreshToken);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } catch (RuntimeException error) {
//                request.setAttribute("exception", "MalformedJwtException");
//            }
//        }
        chain.doFilter(request, response);
    }
}