package com.example.smartwardrobe.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class AuthenticationExceptionTest {
    @Test
    void testConstructor() {
        Throwable throwable = new Throwable();
        AuthenticationException actualAuthenticationException = new AuthenticationException("An error occurred", throwable);

        Throwable cause = actualAuthenticationException.getCause();
        assertSame(throwable, cause);
        assertEquals("com.example.smartwardrobe.authentication.AuthenticationException: An error occurred",
                actualAuthenticationException.toString());
        assertEquals("An error occurred", actualAuthenticationException.getLocalizedMessage());
        Throwable[] suppressed = actualAuthenticationException.getSuppressed();
        assertEquals(0, suppressed.length);
        assertEquals("An error occurred", actualAuthenticationException.getMessage());
        assertNull(cause.getLocalizedMessage());
        assertNull(cause.getCause());
        assertEquals("java.lang.Throwable", cause.toString());
        assertNull(cause.getMessage());
        assertSame(suppressed, cause.getSuppressed());
        assertSame(cause, throwable);
    }
}

