package com.blog.insight_lane;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class EncodePasswordTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void encodePassword() {
        String password = "12345678";
        System.out.println("Encoded Password: " + this.passwordEncoder.encode(password));
    }
}
