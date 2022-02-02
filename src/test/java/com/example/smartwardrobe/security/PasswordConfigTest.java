package com.example.smartwardrobe.security;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.smartwardrobe.PasswordConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PasswordConfig.class})
@ExtendWith(SpringExtension.class)
class PasswordConfigTest {
    @Autowired
    private PasswordConfig passwordConfig;

    @Test
    void testEncoder() {
        assertTrue(this.passwordConfig.encoder() instanceof org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder);
    }
}

