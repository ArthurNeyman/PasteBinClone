package com.paste_bin_clone.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class UtilService {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final String ALLOWED_CHARACTERS =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_";
    public String getHashCode() {
        StringBuilder result = new StringBuilder(64);
        for (int i = 0; i < 64; i++) {
            int randomIndex = SECURE_RANDOM.nextInt(ALLOWED_CHARACTERS.length());
            result.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }
        return result.toString();
    }
}
