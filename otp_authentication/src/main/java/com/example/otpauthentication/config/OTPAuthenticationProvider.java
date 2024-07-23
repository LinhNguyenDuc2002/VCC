package com.example.otpauthentication.config;

import com.example.otpauthentication.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class OTPAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private OTPService otpService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OTPAuthentication otpAuthentication = (OTPAuthentication) authentication;

        if(otpService.validateOTP(otpAuthentication.getCredentials(), otpAuthentication.getName())) {
            return authentication;
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OTPAuthentication.class.isAssignableFrom(authentication);
    }
}
