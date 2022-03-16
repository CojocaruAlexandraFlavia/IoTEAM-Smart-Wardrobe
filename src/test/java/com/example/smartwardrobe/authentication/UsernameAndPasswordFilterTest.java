package com.example.smartwardrobe.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import ch.qos.logback.core.util.COWArrayList;

import java.util.ArrayList;
import javax.servlet.FilterChain;

import org.apache.catalina.connector.Response;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.TestingAuthenticationToken;

class UsernameAndPasswordFilterTest {
    @Test
    void testConstructor() {
        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
        UsernameAndPasswordFilter actualUsernameAndPasswordFilter = new UsernameAndPasswordFilter(
                new ProviderManager(authenticationProviderList));
        assertEquals("username", actualUsernameAndPasswordFilter.getUsernameParameter());
        assertTrue(actualUsernameAndPasswordFilter
                .getRememberMeServices() instanceof org.springframework.security.web.authentication.NullRememberMeServices);
        assertEquals("password", actualUsernameAndPasswordFilter.getPasswordParameter());
    }

    @Test
    void testAttemptAuthentication() throws RuntimeException {
        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
        UsernameAndPasswordFilter usernameAndPasswordFilter = new UsernameAndPasswordFilter(
                new ProviderManager(authenticationProviderList));
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertThrows(AuthenticationException.class,
                () -> usernameAndPasswordFilter.attemptAuthentication(request, new Response()));
    }

    @Test
    void testAttemptAuthentication2() throws RuntimeException {
        UsernameAndPasswordFilter usernameAndPasswordFilter = new UsernameAndPasswordFilter(null);
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertThrows(AuthenticationException.class,
                () -> usernameAndPasswordFilter.attemptAuthentication(request, new Response()));
    }

    @Test
    void testSuccessfulAuthentication() {
        // TODO: This test is incomplete.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by successfulAuthentication(HttpServletRequest, HttpServletResponse, FilterChain, Authentication)
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
        UsernameAndPasswordFilter usernameAndPasswordFilter = new UsernameAndPasswordFilter(
                new ProviderManager(authenticationProviderList));
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);
        usernameAndPasswordFilter.successfulAuthentication(request, response, chain,
                new TestingAuthenticationToken("Principal", "Credentials"));
    }

    @Test
    void testSuccessfulAuthentication2() {
        // TODO: This test is incomplete.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by successfulAuthentication(HttpServletRequest, HttpServletResponse, FilterChain, Authentication)
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
        UsernameAndPasswordFilter usernameAndPasswordFilter = new UsernameAndPasswordFilter(
                new ProviderManager(authenticationProviderList));
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);
        usernameAndPasswordFilter.successfulAuthentication(request, response, chain,
                new TestingAuthenticationToken((COWArrayList<Object>) mock(COWArrayList.class), "Credentials"));
    }
}

