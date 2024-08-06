package com.example.otpauthentication.config;

import com.example.otpauthentication.cache.CacheMemory;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            return authentication;
        }

        String otp = request.getParameter("otp");
        String key = request.getParameter("key");
        CacheMemory cacheMemory = cacheManager.getCache("user").get(key, CacheMemory.class);

        if(getAuthenticationManager().authenticate(new OTPAuthentication(key, otp)) != null) {
            authentication = getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(cacheMemory.getUsername(), cacheMemory.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        }

        return null;
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        chain.doFilter(request, response);
    }
}
