package com.example.otpauthentication.config;

import com.example.otpauthentication.cache.CacheMemory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

public class OTPAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final CacheManager cacheManager;
    protected OTPAuthenticationFilter(CacheManager cacheManager) {
        super(new AntPathRequestMatcher("/verify", "POST"));
        this.cacheManager = cacheManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String otp = request.getParameter("otp");
        String key = request.getParameter("key");

        CacheMemory cacheMemory = cacheManager.getCache("user").get(key, CacheMemory.class);
        Authentication authentication = getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(cacheMemory.getUsername(), cacheMemory.getPassword()));

        if(authentication.isAuthenticated()) {
            if(getAuthenticationManager().authenticate(new OTPAuthentication(key, otp)) != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return authentication;
            }
        }

        return null;
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        chain.doFilter(request, response);
    }
}
