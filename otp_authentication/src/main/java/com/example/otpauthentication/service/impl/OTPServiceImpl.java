package com.example.otpauthentication.service.impl;

import com.example.otpauthentication.cache.CacheMemory;
import com.example.otpauthentication.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class OTPServiceImpl implements OTPService {
    @Autowired
    private CacheManager cacheManager;

    // Can call third-party to validate
    @Override
    public boolean validateOTP(String key, String otp) {
        CacheMemory cache = cacheManager.getCache("user").get(key, CacheMemory.class);

        if(cache != null && cache.getOtp().equals(otp)) {
            cacheManager.getCache("user").evict(key);
            return true;
        }

        return false;
    }
}
