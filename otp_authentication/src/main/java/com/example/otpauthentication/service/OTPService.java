package com.example.otpauthentication.service;

public interface OTPService {
    boolean validateOTP(String key, String otp);
}
