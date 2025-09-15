package com.paste_bin_clone.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UtilService {
    public String getHashCode() {
        byte[] array = new byte[64];
        new Random().nextBytes(array);
        String randomString = new String(array, StandardCharsets.UTF_8);
        StringBuffer r = new StringBuffer();
        for (int k = 0; k < randomString.length(); k++) {
            char ch = randomString.charAt(k);
            if (((ch >= 'a' && ch <= 'z')
                || (ch >= 'A' && ch <= 'Z')
                || (ch >= '0' && ch <= '9'))) {
                r.append(ch);
            }
        }
        return r.toString();
    }
}
