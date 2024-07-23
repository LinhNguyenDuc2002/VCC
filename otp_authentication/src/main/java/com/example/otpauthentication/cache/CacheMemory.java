package com.example.otpauthentication.cache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CacheMemory {
    private String key;

    private String username;

    private String password;

    private String otp;
}
